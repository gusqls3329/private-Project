package com.example.beenproject.common.security.oauth2;


import com.example.beenproject.common.SecurityProperties;
import com.example.beenproject.common.security.JwtTokenProvider;
import com.example.beenproject.common.security.SecurityUserDetails;
import com.example.beenproject.common.security.model.SecurityPrincipal;
import com.example.beenproject.common.utils.CookieUtils;
import com.example.beenproject.user.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.example.beenproject.common.security.oauth2.OAuth2AuthenticationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuth2AuthenticationRequestBasedOnCookieRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityProperties appProperties;
    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("targetUrl:{}", targetUrl);
        if (response.isCommitted()) {
            log.error("Response has already been committed. Unable to redirect to {}", targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = cookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !hasAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry!, Unauthorized Redirect URI");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());


        SecurityUserDetails myUserDetails = (SecurityUserDetails) authentication.getPrincipal();

        SecurityPrincipal myPrincipal = myUserDetails.getSecurityPrincipal();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);

        int rtCookieMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(response, "rt");
        cookieUtils.setCookie(response, "rt", rt, rtCookieMaxAge);

        UserModel userModel = myUserDetails.getUserModel();

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", at)
                .queryParam("iuser", userModel.getIuser())
                .queryParam("nm", userModel.getNick()).encode()
                .queryParam("pic", userModel.getPic())
                .queryParam("firebase_token", userModel.getFirebaseToken())
                .build()
                .toUriString();


    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        repository.removeAuthorizationRequestCookies(response);
    }

    private boolean hasAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        log.info("clientRedirectUri.getHost(): {}", clientRedirectUri.getHost());
        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream().anyMatch(redirectUri -> {
                    URI authorizedURI = URI.create(uri);
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}