package com.getir.readingisgood.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("user-token")
public class UserToken implements Serializable {

    private static final long serialVersionUID = -2396304138425129719L;

    @Id
    private String username;
    @Indexed
    private String token;
    private String ip;
    private boolean block;
    private boolean remember;
    private LocalDateTime createTime;

    public UserToken(String token, String username, String ip) {
        this.token = token;
        this.username = username;
        this.ip = ip;
        block = false;
        remember = false;
        createTime = LocalDateTime.now();
    }
}
