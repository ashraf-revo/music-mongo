package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.revo.Util.ViewDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static org.revo.Domain.Role.*;

/**
 * Created by ashraf on 13/01/17.
 */
public abstract class BaseUser implements UserDetails {
    @JsonProperty(access = READ_ONLY)
    @JsonView(ViewDetails.user.class)
    private boolean accountNonLocked = true;
    @JsonProperty(access = READ_ONLY)
    @JsonView(ViewDetails.user.class)
    private boolean enabled = false;
    @JsonIgnore
    private byte[] type = new byte[]{0, 0, 0};

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public byte[] getType() {
        return type;
    }

    public void setType(byte[] type) {
        this.type = type;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        if (type[0] == 1) {
            roles.add(USER.getBuildRole());
        }
        if (type[1] == 1) {
            roles.add(SONG.getBuildRole());
        }
        if (type[2] == 1) {
            roles.add(ADMIN.getBuildRole());
        }
        return AuthorityUtils.createAuthorityList(roles.stream().toArray(String[]::new));
    }
}
