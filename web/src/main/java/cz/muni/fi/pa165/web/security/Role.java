package cz.muni.fi.pa165.web.security;

/**
 * @author Michal Pavuk
 */
public enum Role {
    USER("USER"), ADMIN("ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
