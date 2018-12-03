package cz.muni.fi.pa165.dto;

public class AffinityDto extends BaseDto {

    private String name;
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof  AffinityDto)) return false;

        AffinityDto affinity = (AffinityDto) o;

        if (getLevel() != affinity.getLevel()) return false;
        return getName().equals(affinity.getName());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getLevel();
        return result;
    }
}
