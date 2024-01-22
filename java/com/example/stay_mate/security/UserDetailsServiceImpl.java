package com.example.stay_mate.security;

import com.example.stay_mate.service.partner.PartnerService;
import com.example.stay_mate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PartnerService partnerService;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return partnerService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e){
            return userService.loadUserByUsername(username);
        }
    }
}
