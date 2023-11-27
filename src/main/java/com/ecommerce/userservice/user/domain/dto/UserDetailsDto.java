//package com.ecommerce.userservice.user.domain.dto;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.experimental.Delegate;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Getter
//@AllArgsConstructor
//public class UserDetailsDto implements UserDetails {
//
//    @Delegate
//    private UserDto userDto;
//    private Collection<? extends GrantedAuthority> authorities;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return userDto.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return userDto.getName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
