package cz.muni.fi.pa165.dto;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class HeroDto extends BaseDto {

    private String name;
    private int hitpoints;
    private int damage;
    private int gold;
    private boolean isAlive;
    private int might;
    private int agility;
    private int magic;
    private QuestDto quest;
    private List<SkillDto> skills;

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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getMight() {
        return might;
    }

    public void setMight(int might) {
        this.might = might;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public QuestDto getQuest() {
        return quest;
    }

    public void setQuest(QuestDto quest) {
        this.quest = quest;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    // EQUALS, HASH, TOSTRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroDto)) return false;
        HeroDto hero = (HeroDto) o;
        return getGold() == hero.getGold() &&
                isAlive() == hero.isAlive() &&
                getMight() == hero.getMight() &&
                getAgility() == hero.getAgility() &&
                getMagic() == hero.getMagic() &&
                Objects.equals(getQuest(), hero.getQuest()) &&
                Objects.equals(getSkills(), hero.getSkills());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getGold(), isAlive(), getMight(), getAgility(), getMagic(), getQuest(), getSkills());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Class name: HeroDto");
        sb.append("\nid: ");
        sb.append(getId());
        sb.append("\nname: ");
        sb.append(getName());
        sb.append("\nhitpoints: ");
        sb.append(getHitpoints());
        sb.append("\ndamage: ");
        sb.append(getDamage());
        sb.append("\ngold: ");
        sb.append(getGold());
        sb.append("\nisAlive: ");
        sb.append(isAlive());
        sb.append("\nmight: ");
        sb.append(getMight());
        sb.append("\nagility: ");
        sb.append(getAgility());
        sb.append("\nmagic: ");
        sb.append(getMagic());

        sb.append("\nquest: ");
        if (quest != null) {
            sb.append(getQuest());
        }
        else {
            sb.append("N/A");
        }

        sb.append("\nskills: ");
        if (skills.size() != 0) {
            for (Iterator<SkillDto> iterator = skills.iterator(); iterator.hasNext(); ) {
                SkillDto skill = iterator.next();

                sb.append(skill.getName());

                if (iterator.hasNext()) {
                    sb.append(", ");
                }
            }

        } else {
            sb.append("none");
        }

        return sb.toString();
    }
}
