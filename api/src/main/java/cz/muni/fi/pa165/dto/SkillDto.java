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
}
