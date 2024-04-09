package com.example.beenproject.user;

import com.example.beenproject.common.*;
import com.example.beenproject.common.exception.base.BadInformationException;
import com.example.beenproject.common.exception.checked.FileNotContainsDotException;
import com.example.beenproject.common.security.AuthenticationFacade;
import com.example.beenproject.common.security.JwtTokenProvider;
import com.example.beenproject.common.security.SecurityUserDetails;
import com.example.beenproject.common.security.model.SecurityPrincipal;
import com.example.beenproject.common.utils.CookieUtils;
import com.example.beenproject.common.utils.MyFileUtils;
import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.enums.ProvideType;
import com.example.beenproject.eneities.enums.UserStatus;
import com.example.beenproject.user.model.SignUpDto;
import com.example.beenproject.user.model.SinginDto;
import com.example.beenproject.user.model.SinginVo;
import com.example.beenproject.user.model.UserFirebaseTokenPatchDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.beenproject.common.exception.ErrorMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MyFileUtils myFileUtils;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityProperties securityProperties;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;

    public long postSignup(SignUpDto dto) {

        String password = passwordEncoder.encode(dto.getUpw());
        dto.setUpw(password);

        String path = "user" + "/" + dto.getIuser();
        myFileUtils.delFolderTrigger(path);
        if (dto.getPic() != null) {
            try {
                String savedPicFileNm = String.valueOf(
                        myFileUtils.savePic(dto.getPic(), "user",
                                String.valueOf(dto.getIuser())));
                dto.setChPic(savedPicFileNm);
            } catch (FileNotContainsDotException e) {
                throw new BadInformationException(BAD_PIC_EX_MESSAGE);
            }
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNick(dto.getNick());
        user.setPic(dto.getChPic());
        user.setStatus(UserStatus.ACTIVE);
        user.setUid(dto.getUid());
        user.setUpw(dto.getUpw());
        user.setProvideType(ProvideType.LOCAL);
        repository.save(user);
        return Const.SUCCESS;
    }

    public SinginVo postSignin(HttpServletResponse http, SinginDto dto) {
        User user = repository.findByUid(dto.getUid());
        String pass = passwordEncoder.encode(dto.getUpw());

        if (user == null) {
            throw new ClientException(ErrorMessage.ILLEGAL_UID_MESSAGE);
        }
        if (!pass.equals(user.getUpw())) {
            throw new ClientException(ErrorMessage.ILLEGAL_UPW_MESSAGE);
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ClientException(ErrorMessage.NO_SUCH_USER_EX_MESSAGE);


        }
        SecurityPrincipal principal = SecurityPrincipal.builder()
                .iuser(user.getIuser())
                .build();

        String at = jwtTokenProvider.generateAccessToken(principal);
        String rt = jwtTokenProvider.generateRefreshToken(principal);
        if (http != null) {
            int rtCookieMaxAge = (int) (securityProperties.getJwt().getRefreshTokenExpiry() / 1000);
            cookieUtils.deleteCookie(http, "rt");
            cookieUtils.setCookie(http, "rt", rt, rtCookieMaxAge);
        }

        return SinginVo.builder()
                .result(Const.SUCCESS)
                .iuser(user.getIuser())
                .accesstoken(at).build();
    }

    public Long checkEmail(String email) {
        User user = repository.findByEmail(email);
        if (user == null || user.getStatus() == UserStatus.DELETED) {
            return Const.SUCCESS;
        }

        throw new ClientException(ErrorMessage.ILLEGAL_EX_MESSAGE);

    }

    public long patchToken(UserFirebaseTokenPatchDto dto) {
        User user = repository.findByIuser(authenticationFacade.getLoginUserPk());
        user.setFirebaseToken(dto.getFirebaseToken());
        repository.save(user);
        return Const.SUCCESS;
    }

    public int getSignOut(HttpServletResponse res) {
        try {
            cookieUtils.deleteCookie(res, "rt");
        } catch (NullPointerException e) {
            throw new BadInformationException(AUTHENTICATION_FAIL_EX_MESSAGE);
        }
        return 1;
    }

    public SinginVo getRefrechToken(HttpServletRequest req) {
        Optional<Cookie> cookie = cookieUtils.getCookie(req, "rt");
        String token = cookie.get().getValue();
        if (!jwtTokenProvider.isValidatedToken(token)) {
            return SinginVo.builder()
                    .result(Const.FAIL)
                    .accesstoken(null)
                    .build();
        }
        SecurityUserDetails UserDetails = (SecurityUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        SecurityPrincipal Principal = UserDetails.getSecurityPrincipal();
        String at = jwtTokenProvider.generateAccessToken(Principal);

        return SinginVo.builder()
                .result(Const.SUCCESS)
                .accesstoken(at).build();
    }

    public long patchUserFirebaseToken(UserFirebaseTokenPatchDto dto) { //FirebaseToken을 발급 : Firebase방식 : 메시지를 보낼때 ip대신 고유값(Firebase)을 가지고 있는사람에게 메시지 전달
        User user = repository.findByIuser(authenticationFacade.getLoginUserPk());
        user.setFirebaseToken(dto.getFirebaseToken());
        repository.save(user);
        return Const.SUCCESS;
    }
}
