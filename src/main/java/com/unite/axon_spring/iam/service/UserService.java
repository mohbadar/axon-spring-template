package com.unite.axon_spring.iam.service;

import java.util.Collection;
import com.unite.axon_spring.iam.model.User;
import com.unite.axon_spring.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;


    public User getLoggedInUser() {
        return getLoggedInUser(false);
    }

    public User getLoggedInUser(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        return userRepository.findByUsername(username);
    }

    public String getSecurityContextHolderUsername(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        return username;
    }

    public boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> auths = ((UserDetails) principal).getAuthorities();
        if (auths.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return true;
        }
        return false;
    }

    public String getCurrentEnv() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        return userDetails.getCurrentEnv();

    }

    public long countByEnvSlug(String envSlug) {
        return userRepository.countByEnvSlug(envSlug);
    }

    public long countByEnvSlugAndActive(String envSlug, Boolean active) {
        return userRepository.countByEnvSlugAndActive(envSlug, active);
    }
}

