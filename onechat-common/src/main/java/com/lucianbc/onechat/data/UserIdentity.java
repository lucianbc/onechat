package com.lucianbc.onechat.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentity implements Serializable {
    private String id;
    private String username;

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof UserIdentity)) return false;
        return this.id.equals(((UserIdentity)other).id);
    }
}

