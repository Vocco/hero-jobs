package cz.muni.fi.pa165.dto;

import java.util.List;

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
}
