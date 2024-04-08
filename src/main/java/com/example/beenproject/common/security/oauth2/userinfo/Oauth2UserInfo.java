package com.example.beenproject.common.security.oauth2.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public abstract class Oauth2UserInfo {
    protected Map<String, Object> attributes;

    public  abstract String getId();
    public  abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
