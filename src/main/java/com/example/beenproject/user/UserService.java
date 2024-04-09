package com.example.beenproject.user;

import com.example.beenproject.common.*;
import com.example.beenproject.common.exception.base.BadInformationException;
import com.example.beenproject.common.exception.checked.FileNotContainsDotException;
import com.example.beenproject.common.security.JwtTokenProvider;
import com.example.beenproject.common.security.model.SecurityPrincipal;
import com.example.beenproject.common.utils.CookieUtils;
import com.example.beenproject.common.utils.MyFileUtils;
import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.enums.ProvideType;
import com.example.beenproject.eneities.enums.UserStatus;
import com.example.beenproject.user.model.SignUpDto;
import com.example.beenproject.user.model.SinginDto;
import com.example.beenproject.user.model.SinginVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.beenproject.common.exception.ErrorMessage.BAD_PIC_EX_MESSAGE;
import static com.example.beenproject.common.exception.ErrorMessage.ILLEGAL_EX_MESSAGE;

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

}
