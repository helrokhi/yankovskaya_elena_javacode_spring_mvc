package ru.pro.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.io.IOException;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_POST;

@Configuration
@RequiredArgsConstructor
public class OAuth2ClientConfig {
    private final ResourceLoader resourceLoader;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper
                .readTree(resourceLoader.getResource("classpath:google-oauth.json").getInputStream())
                .path("web");

        ClientRegistration registration = ClientRegistration.withRegistrationId("google")
                .clientId(json.get("client_id").asText())
                .clientSecret(json.get("client_secret").asText())
                .clientAuthenticationMethod(CLIENT_SECRET_POST)
                .authorizationGrantType(AUTHORIZATION_CODE)
                .redirectUri(json.get("redirect_uris").get(0).asText())
                .scope("openid", "profile", "email")
                .authorizationUri("auth_uri")
                .tokenUri("token_uri")
                .userInfoUri("https://openidconnect.googleapis.com/v1/userinfo")
                .userNameAttributeName("email")
                .clientName("Google")
                .build();

        return new InMemoryClientRegistrationRepository(registration);
    }
}
