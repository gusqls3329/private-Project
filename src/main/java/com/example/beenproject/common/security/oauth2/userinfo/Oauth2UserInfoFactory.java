package com.example.beenproject.common.security.oauth2.userinfo;

import com.example.beenproject.common.security.oauth2.SocialProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class Oauth2UserInfoFactory {
    public Oauth2UserInfo getOauth2UserInfo(SocialProviderType socialProviderType,
                                            Map<String, Object> attrs){

        return switch(socialProviderType){
            case KAKAO -> new KakaoOAuth2UserInfo(attrs);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };

    }

}
