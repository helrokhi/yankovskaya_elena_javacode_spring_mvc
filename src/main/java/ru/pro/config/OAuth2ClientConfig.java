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
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class OAuth2ClientConfig {
    private final ResourceLoader resourceLoader;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(resourceLoader.getResource("classpath:google-oauth1.json").getInputStream())
                .path("web");

        ClientRegistration registration = ClientRegistration.withRegistrationId("google")
                .clientId(json.get("client_id").asText())
                .clientSecret(json.get("client_secret").asText())
                .clientName("Google")
                .scope("openid", "profile", "email")
                .authorizationUri(json.get("auth_uri").asText())
                .tokenUri(json.get("token_uri").asText())
                .userInfoUri("https://openidconnect.googleapis.com/v1/userinfo")
                .userNameAttributeName("email")
                .redirectUri(json.get("redirect_uris").get(0).asText())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();

        return new InMemoryClientRegistrationRepository(registration);
    }
}
