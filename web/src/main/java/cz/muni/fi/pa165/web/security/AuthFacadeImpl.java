package cz.muni.fi.pa165.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthFacadeImpl implements AuthFacade {
    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    @Override
    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public boolean hasRole(Role role) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return false;
        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role.getRole()));
    }
}