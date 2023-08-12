package com.example.demo.token;


import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;

//    in case we want to revoke the token
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


//    private Integer userid;






}
