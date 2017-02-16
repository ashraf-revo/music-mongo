package org.revo.Domain;

import static org.revo.Domain.Role.Paths.*;

/**
 * Created by ashraf on 14/12/16.
 */
public enum Role {
    USER("USER", USER_PATH), SONG("SONG", SONG_PATH), ADMIN("ADMIN", ADMIN_PATH);
    String role;
    String path;

    Role(String role, String path) {
        this.role = role;
        this.path = path;
    }

    public String getRole() {
        return role;
    }

    public String getPath() {
        return path;
    }

    public String getBuildRole() {
        return "ROLE_" + role;
    }

    public static class Paths {
        public static final String USER_PATH = "/api/user";
        public static final String SONG_PATH = "/api/song";
        public static final String ADMIN_PATH = "/api/admin";
    }
}