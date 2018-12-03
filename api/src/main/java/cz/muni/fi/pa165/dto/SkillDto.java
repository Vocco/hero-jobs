package cz.muni.fi.pa165.dto;

import java.util.List;

public class SkillDto extends BaseDto {
    private String name;
    private List<AffinityDto> affinities;
    private int baseDamage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AffinityDto> getAffinities() {
        return affinities;
    }

    public void setAffinities(List<AffinityDto> affinities) {
        this.affinities = affinities;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof SkillDto)) return false;

        SkillDto skill = (SkillDto) o;

        if (getBaseDamage() != skill.getBaseDamage()) return false;
        if (!getName().equals(skill.getName())) return false;
        return getAffinities() != null ? getAffinities().equals(skill.getAffinities()) : skill.getAffinities() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getAffinities() != null ? getAffinities().hashCode() : 0);
        result = 31 * result + getBaseDamage();
        return result;
    }
}
