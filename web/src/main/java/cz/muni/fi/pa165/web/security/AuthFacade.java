package cz.muni.fi.pa165.web.security;

import org.springframework.stereotype.Service;

@Service
public interface AuthFacade {

    boolean isAuthenticated();

    String getUsername();

    boolean hasRole(Role role);
}
