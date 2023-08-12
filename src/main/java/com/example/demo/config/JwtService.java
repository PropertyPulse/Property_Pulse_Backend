package com.example.demo.config;

import com.example.demo.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //third we extract the username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    second we create this function to extract one claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        //we extract all claims and then apply the function on it
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


//    generate token with user details

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }





//generate refresh token
    public String generateRefreshToken(

            UserDetails userDetails
    ){
        return buildToken(
                new HashMap<>(),
                userDetails,
                refreshExpiration
        );
    }


//generate token(firstly we make this)

//    claims (authorities,any information to be passed to the token
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails

    ){
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        extraClaims.put("role",user.role.toString());


        return buildToken(
                extraClaims,
                userDetails,
                jwtExpiration
        );
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration


    ){


        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) //username or useremail
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +expiration )) /*token will valid 24hrs*/
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)//signing with the key
                .compact(); /*compact() will generate the token*/
    }


//    validity check
    public boolean isTokenValid(String token, UserDetails userDetails) {
//we check whether the token is belonged to the user or not
        final String username = extractUsername(token);//username kiwwata methana gannne email eka eka unique api giye mehema thami mula idnma(email)
        // we check whether the username is equal to the username in the token and the token is not expired
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

//expiration check
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);

    }

    //firstly we extract all the claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())//we need signing key in order to decode token
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
// signing is a secret that is used to digitally sign the jwt ,it is used create the signature part in the jwt
    //in signature part it describes that sender of the jwt is who claims to be. an ensure the integrity of the message
    //in order to do this we have to generate a signing key from the internet
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
