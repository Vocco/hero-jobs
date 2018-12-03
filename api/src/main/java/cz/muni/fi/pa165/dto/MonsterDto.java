package cz.muni.fi.pa165.dto;

import java.util.List;
import java.util.Objects;

public class MonsterDto extends BaseDto {
    private int hitpoints;
    private int damage;
    private String name;
    private String size;
    private List<AffinityDto> strengths;
    private List<AffinityDto> weaknesses;
    private List<QuestMonsterDto> assignedToQuests;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<AffinityDto> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<AffinityDto> strengths) {
        this.strengths = strengths;
    }

    public List<AffinityDto> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<AffinityDto> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof MonsterDto)) return false;

        MonsterDto monster = (MonsterDto) o;
        if ( !getName().equals(monster.getName()) ||
                getHitpoints() != monster.getHitpoints() ||
                getDamage() != monster.getDamage())
            return false;

        return Objects.equals(size, monster.size);
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 17 * result + getName().hashCode();
        result = 17 * result + getHitpoints();
        result = 17 * result + getDamage();
        result = 17 * result + getSize().hashCode();
        return result;
    }

}
