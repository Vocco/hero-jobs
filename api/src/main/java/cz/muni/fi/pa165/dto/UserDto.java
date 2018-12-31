package cz.muni.fi.pa165.dto;

public class UserDto extends BaseDto {
    private String username;
    private String passwordHash;
    private String email;
    private HeroDto managedHero;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HeroDto getManagedHero() {
        return managedHero;
    }

    public void setManagedHero(HeroDto managedHero) {
        this.managedHero = managedHero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof UserDto)) return false;

        UserDto userDto = (UserDto) o;

        if (getUsername() != null ? !getUsername().equals(userDto.getUsername()) : userDto.getUsername() != null)
            return false;
        if (getPasswordHash() != null ? !getPasswordHash().equals(userDto.getPasswordHash()) : userDto.getPasswordHash() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(userDto.getEmail()) : userDto.getEmail() != null) return false;
        return getManagedHero() != null ? getManagedHero().equals(userDto.getManagedHero()) : userDto.getManagedHero() == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getPasswordHash() != null ? getPasswordHash().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getManagedHero() != null ? getManagedHero().hashCode() : 0);
        return result;
    }
}
