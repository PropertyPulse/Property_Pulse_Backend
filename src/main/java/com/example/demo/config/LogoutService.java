package com.example.demo.config;

import com.example.demo.token.TokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        // here we firstly, doing extracting the request (header,jwt,useremail)
        final String authHeader = request.getHeader("Authorization"); //extract the header
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            return;
        }
        jwt = authHeader.substring(7); //extract the jwt

        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);

        }

        creatCookie(response, "", 0);

    }

    private void creatCookie(HttpServletResponse response, String refreshToken, Integer MaxAge) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);  // Make the cookie accessible only through HTTP
        refreshTokenCookie.setMaxAge(MaxAge);  // Set the cookie's expiration time in seconds
        refreshTokenCookie.setPath("/");  // Set the cookie's path to the root
        response.addCookie(refreshTokenCookie);
    }
}
