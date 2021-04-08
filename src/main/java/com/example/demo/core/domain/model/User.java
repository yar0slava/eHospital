package com.example.demo.core.domain.model;

import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class User implements UserDetails {

    private long id;
    private Gender gender;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private Hospital hospital;
    private List<BankAccount> bankAccount;
    private String password;
    private Set<Authority> authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
