package com.ict.edu2.config;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2userService extends DefaultOAuth2UserService{
    // SNS에게 사용자 정보 요청을 처리하고, 사용자 정보를 수신한다. OAuth2User 객체 생성 
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모클래스의 loaduser 메서드를 호출하여 기본 사용자 정보를 가져온다.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 사용자 속성 가져오기
        Map<String,Object> attributes = oAuth2User.getAttributes();

        // 어떤 제공자인지 알수 있다. (naver, kakao, google)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("naver")){
            Map<String, Object> response = (Map<String,Object>) attributes.get("response");
            if(response == null){
                throw new OAuth2AuthenticationException("Naver error");
            }
            String name = (String)response.get("name");
            String email = (String)response.get("email");
            String phone = (String)response.get("mobile");
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
               "email" , email,
               "name" , name,
               "id", response.get("id"),
                  "phone",phone
            ), "email");
        }else if(provider.equals("kakao")){
            Map<String, Object> kakaoAccount = (Map<String,Object>) attributes.get("kakao_account");
            if(kakaoAccount == null){
                throw new OAuth2AuthenticationException("Kakao error");
            }
            String email = (String)kakaoAccount.get("email");
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            if(properties == null){
                throw new OAuth2AuthenticationException("Kakao error2");
            }

            String name = (String) properties.get("nickname");
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                "email", email,
                "name", name,
                "id", attributes.get("id")
                ),"email");
        }else if(provider.equals("google")){
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                "email", email,
                "name", name,
                "id", attributes.get("sub")
            ), "email");
        }

        return oAuth2User;
    }

}
