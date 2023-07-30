package com.example.gateway.security.oauth2;

import com.example.common.proxy.user.payload.response.UserProfileResponse;
import com.example.gateway.service.UserService;
import com.example.gateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class SuccessHandleOAuth2 extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.cors.clientOrigins}")
    private  String HOST ;
    private JwtUtils jwtUtils;

    private UserService userService;

    public SuccessHandleOAuth2(JwtUtils jwtUtils, UserService userService) {
       this.jwtUtils = jwtUtils;
       this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = this.generateTargetUrl(request, response, authentication);
        getRedirectStrategy().sendRedirect(request,response,HOST+"/login/token?"+token);
    }

    public String generateTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
         try {
             CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
             UserProfileResponse user = userService.processOAuth2Google(oAuth2User);
             return jwtUtils.generateToken(user.getUsername());
         } catch (Exception e) {
            log.error(e.getMessage());
             throw  new RuntimeException();
         }
    }

}
