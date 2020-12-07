package com.msa.jrg.userservice.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserProfile {

    private Long id;
    private String username;
    private byte[] avatarData;

    public UserProfile(Long id, String username, byte[] avatarData) {
        this.id = id;
        this.username = username;
        this.avatarData = avatarData;
    }
}
