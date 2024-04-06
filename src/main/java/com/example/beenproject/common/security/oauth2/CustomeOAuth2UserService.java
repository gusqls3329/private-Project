package com.example.beenproject.common.security.oauth2;



import com.example.beenproject.common.security.SecurityUserDetails;
import com.example.beenproject.common.security.model.SecurityPrincipal;
import com.example.beenproject.common.security.oauth2.userinfo.Oauth2UserInfo;
import com.example.beenproject.common.security.oauth2.userinfo.Oauth2UserInfoFactory;
import com.example.beenproject.eneities.User;
import com.example.beenproject.user.jpa.UserRepository;
import com.example.beenproject.user.model.UserModel;
import com.example.beenproject.user.model.UserSelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomeOAuth2UserService extends DefaultOAuth2UserService {

    private final Oauth2UserInfoFactory factory;
    private final UserRepository userRepository;

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
        User userss = userRepository.findByUid(dto.getUid()).orElse(null);
        UserModel userModel = null;

        if (userss != null) {
            userModel = UserModel.builder()
                    .iuser(userss.getIuser())
                    .email(userss.getEmail())
                    .nick(userss.getNick())
                    .provideType(userss.getProvideType().name())
                    .storedPic(userss.getPic())
                    .status(userss.getStatus())
                    .build();

            SecurityPrincipal myPrincipal = SecurityPrincipal.builder()
                    .iuser(userModel.getIuser())
                    .build();

            return SecurityUserDetails.builder()
                    .userModel(userModel)
                    .securityPrincipal(myPrincipal)
                    .attributes(user.getAttributes()).build();
        }

        userModel = signupUser(oauth2UserInfo, socialProviderType);
        return SecurityUserDetails.builder()
                .userModel(userModel)
                .attributes(user.getAttributes()).build();

    }

    private UserModel signupUser(Oauth2UserInfo oauth2UserInfo, SocialProviderType socialProviderType) {

        return UserModel.builder()
                .uid(oauth2UserInfo.getId())
                .upw("social")
                .provideType(SocialProviderType.KAKAO.name())
                .build();
    }

}