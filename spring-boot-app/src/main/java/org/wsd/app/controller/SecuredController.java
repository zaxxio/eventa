package org.wsd.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/secured")
public class SecuredController {


    @GetMapping
    public ResponseEntity<String> getName() {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = token.getPrincipal();
        return ResponseEntity.ok(oAuth2User.getName());
    }

    @GetMapping("/inspect")
    public ResponseEntity<Map<String, String>> inspectToken(OAuth2AuthenticationToken token, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
        OidcUser principal = (OidcUser) token.getPrincipal();

        Map<String, String> map = new HashMap<>();
        map.put("access_token", client.getAccessToken().getTokenValue());
        map.put("refresh_token", Objects.requireNonNull(client.getRefreshToken()).getTokenValue());
        map.put("id_token", principal.getIdToken().getTokenValue());

        // Optionally, handle OIDC user attributes if needed
        // String oidcUserAttributes = principal.getAttributes().toString();
        // map.put("oidc_user_attributes", oidcUserAttributes);

        System.out.println(map); // Debug print

        return ResponseEntity.ok(map);
    }


}
