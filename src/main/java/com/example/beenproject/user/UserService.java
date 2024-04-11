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
import com.example.beenproject.user.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.regex.Pattern;

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
        user.setIuser(dto.getIuser());
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
        if (!passwordEncoder.matches(dto.getUpw(), user.getUpw())) {
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

    public FindUidVo getFindUid(String email) {
        User user = repository.findByEmail(email);
        FindUidVo vo = new FindUidVo();
        vo.setUid(user.getUid());
        return vo;
    }

    public long getFindUpw(FindUpwDto dto) {
        User user = repository.findByUidAndEmail(dto.getUid(), dto.getEmail());
        if (user == null) {
            throw new ClientException(ErrorMessage.NO_SUCH_USER_EX_MESSAGE);
        }
        String pass = passwordEncoder.encode(dto.getUpw());
        user.setUpw(pass);
        repository.save(user);
        return Const.SUCCESS;
    }

    public long putUser(PutUserDto dto) {
        if (dto.getEmail() == null && dto.getPic() == null) {
            throw new ClientException(ErrorMessage.ILLEGAL_EX_MESSAGE);
        }
        if (dto.getEmail() != null) {
            if (dto.getIsEmail() != 1) {
                throw new ClientException(ErrorMessage.ILLEGAL_EX_MESSAGE);
            }

            User user = repository.findByIuser(authenticationFacade.getLoginUserPk());
            user.setEmail(dto.getEmail());

            if (dto.getPic() ==null || !dto.getPic().isEmpty()) {
                repository.save(user);
                return Const.SUCCESS;
            }
            if(dto.getPic() !=null && dto.getPic().isEmpty()) {
                String path = "user" + "/" + authenticationFacade.getLoginUser();
                myFileUtils.delFolderTrigger(path);
                String savedPicFileNm = null;
                try {
                    savedPicFileNm = String.valueOf(
                            myFileUtils.savePic(dto.getPic(), "user",
                                    String.valueOf(authenticationFacade.getLoginUser())));
                    dto.setChPic(savedPicFileNm);
                } catch (FileNotContainsDotException e) {
                    throw new RuntimeException(e);
                }
                user.setPic(dto.getChPic());
                repository.save(user);
                return Const.SUCCESS;
            }
        }

            User user = repository.findByIuser(authenticationFacade.getLoginUserPk());
            String path = "user" + "/" + authenticationFacade.getLoginUser();
            myFileUtils.delFolderTrigger(path);
            String savedPicFileNm = null;
            try {
                savedPicFileNm = String.valueOf(
                        myFileUtils.savePic(dto.getPic(), "user",
                                String.valueOf(authenticationFacade.getLoginUser())));
                dto.setChPic(savedPicFileNm);
            } catch (FileNotContainsDotException e) {
                throw new RuntimeException(e);
            }
            user.setPic(dto.getChPic());
            repository.save(user);
            return Const.SUCCESS;

    }
    public long patchUser(DelUserDto dto){
        User user = repository.findByUid(dto.getUid());
        String pass = passwordEncoder.encode(dto.getUpw());
        dto.setUpw(pass);
        if(user.getEmail().equals(dto.getEmail()) || user.getUpw().equals(dto.getUpw())){
            throw new ClientException(ErrorCode.ILLEGAL_EX_MESSAGE);
        }
        user.setStatus(UserStatus.DELETED);
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
