package com.example.beenproject.common.security.oauth2;



import com.example.beenproject.common.exception.base.BadInformationException;
import com.example.beenproject.common.exception.checked.FileNotContainsDotException;
import com.example.beenproject.common.security.SecurityUserDetails;
import com.example.beenproject.common.security.model.SecurityPrincipal;
import com.example.beenproject.common.security.oauth2.userinfo.Oauth2UserInfo;
import com.example.beenproject.common.security.oauth2.userinfo.Oauth2UserInfoFactory;
import com.example.beenproject.common.utils.MyFileUtils;
import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.enums.ProvideType;
import com.example.beenproject.eneities.enums.UserStatus;
import com.example.beenproject.user.jpa.UserRepository;
import com.example.beenproject.user.model.SignUpDto;
import com.example.beenproject.user.model.UserModel;
import com.example.beenproject.user.model.UserSelDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.MultiformatMessage;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.beenproject.common.exception.ErrorMessage.BAD_PIC_EX_MESSAGE;


@Service
@RequiredArgsConstructor
public class CustomeOAuth2UserService extends DefaultOAuth2UserService {

    private final Oauth2UserInfoFactory factory;
    private final UserRepository userRepository;
    private final MyFileUtils myFileUtils;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialProviderType socialProviderType = SocialProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        Oauth2UserInfo oauth2UserInfo = factory.getOauth2UserInfo(socialProviderType, user.getAttributes());
        UserSelDto dto = UserSelDto.builder()
                .providerType(socialProviderType.name())
                .uid(oauth2UserInfo.getId()).build();

        Optional<User> savedUser = userRepository.findByUidAndProvideType(dto.getUid(), ProvideType.valueOf(dto.getProviderType()));
        if(!savedUser.isPresent()){
            UserModel userModel = signupUser(oauth2UserInfo, socialProviderType);
        }

        SecurityPrincipal myPrincipal = SecurityPrincipal.builder()
                .iuser(savedUser.get().getIuser()).build();
        UserModel userModel = UserModel.builder()
                .createdAt(String.valueOf(savedUser.get().getCreatedAt()))
                .pic(savedUser.get().getPic())
                .uid(savedUser.get().getUid())
                .upw(savedUser.get().getUpw())
                .nick(savedUser.get().getNick())
                .provideType(savedUser.get().getProvideType().name())
                .status(savedUser.get().getStatus())
                .firebaseToken(savedUser.get().getFirebaseToken())
                .iuser(savedUser.get().getIuser())
                .updatedAt(savedUser.get().getUpdatedAt().toString())
                .email(savedUser.get().getEmail()).build();



        return SecurityUserDetails.builder()
                .userModel(userModel)
                .securityPrincipal(myPrincipal)
                .attributes(user.getAttributes()).build();
    }

    private UserModel signupUser(Oauth2UserInfo oauth2UserInfo, SocialProviderType socialProviderType) {

        SignUpDto dto = new SignUpDto();
        dto.setProviderType(socialProviderType.name());
        dto.setUid(oauth2UserInfo.getId());
        dto.setUpw("social");
        dto.setNick(oauth2UserInfo.getName());
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

        LocalDateTime now = LocalDateTime.now();

        UserModel entity = UserModel.builder()
                .createdAt(String.valueOf(now))
                .pic(dto.getChPic())
                .uid(dto.getUid())
                .upw("LOCAL")
                .nick(dto.getNick())
                .provideType(socialProviderType.name())
                .status(UserStatus.ACTIVE)
                .iuser((long) dto.getIuser())
                .email(dto.getEmail()).build();
        return entity;
    }

}