package cz.muni.fi.pa165.web.security;

import cz.muni.fi.pa165.dto.UserDto;
import cz.muni.fi.pa165.facade.UserFacade;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static cz.muni.fi.pa165.web.security.Role.ADMIN;
import static cz.muni.fi.pa165.web.security.Role.USER;
import static java.util.Collections.singletonList;

/**
 * @author Michal Pavuk
 */
@Component
public class AuthProvider implements AuthenticationProvider {

    @Inject
    private UserFacade userFacade;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Role role;

        UserDto user = userFacade.authenticate(username, getDigest(password));
        if (user == null) {
            throw new BadCredentialsException("Invalid credentials for user: " + username);
        }

        if (user.getManagedHero() != null) {
            role = USER;
        } else {
            role = ADMIN;
        }

        return new UsernamePasswordAuthenticationToken(
                username, password, singletonList(new SimpleGrantedAuthority(role.getRole()))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String getDigest(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        return String.format("%064x", new BigInteger(1, digest));
    }
}
