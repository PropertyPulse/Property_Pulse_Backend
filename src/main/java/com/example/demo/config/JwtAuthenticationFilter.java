package com.example.demo.config;

import com.example.demo.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//authentication filter class

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

//    meka methana daala applicationconfig kiyl class ekak hadagannawa
    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain  //this is a design pattern
    ) throws ServletException, IOException {

        // here we firstly, doing extracting the request (header,jwt,useremail)
        final String authHeader = request.getHeader("Authorization"); //extract the header
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response); //this is what the filter chain design pattern does it is move like a chain just like saying do this
            return;
        }
        jwt = authHeader.substring(7); //extract the jwt

        //userEmail is in the jwt token , so we need to extract it from the jwt token, in order to do that we need a class called jwtService to manipulate the jwt token
        userEmail = jwtService.extractUsername(jwt);

//        now we have extracted the useremail from the jwt token, we need to check if the user is authenticated or not


        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null/*methan null kiynne user authenticated na*/) {

//            we have to make a userdetailservice to load the userdetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map( t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

           if (jwtService.isTokenValid(jwt,userDetails) && isTokenValid){
//               if the jwt token is valid then we need to update the security context
//               in order to do that we create a new UsernamePasswordAuthenticationToken type token object(authToken) that will update the context
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                       userDetails,
                       null,
                       userDetails.getAuthorities()
               );
//             set the details of the authentication token from the request
               authToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request)
               );
//               set the authentication token to the security context
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }

        }
        filterChain.doFilter(request,response);//

    }
}
