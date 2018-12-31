package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity represents a user of the information system. This entity is created to implement authentication functionality.
 * If User does not have a managedHero assigned, an administrator role is assumed.
 *
 * @author Michal Pavúk
 */
@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @OneToOne
    private Hero managedHero;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Hero getManagedHero() {
        return managedHero;
    }

    public void setManagedHero(Hero managedHero) {
        this.managedHero = managedHero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getUsername().equals(user.getUsername())) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (!getPasswordHash().equals(user.getPasswordHash())) return false;
        return getManagedHero() != null ? getManagedHero().equals(user.getManagedHero()) : user.getManagedHero() == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + getPasswordHash().hashCode();
        result = 31 * result + (getManagedHero() != null ? getManagedHero().hashCode() : 0);
        return result;
    }
}
