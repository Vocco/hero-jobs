package cz.muni.fi.pa165.heroes.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Hero class represents hero and his attributes.
 *
 * Hero has actor's attributes: id, name, hitpoints and damage as well as gold,
 * isAlive, might, agility, magic, quest and skills.
 *
 * @author Metodej Klang
 */
@Entity
public class Hero extends Actor {

    // ATTRIBUTES

    private int gold;
    private boolean isAlive = true;
    private int might;
    private int agility;
    private int magic;

    @ManyToOne(targetEntity = Quest.class, fetch = FetchType.LAZY)
    private Quest quest;

    @OneToMany(targetEntity = Skill.class)
    private List<Skill> skills = new ArrayList<>();

    // CONSTRUCTORS
    public Hero(){ super();}

    public Hero(String name, int hitpoints, int damage, int gold, int might, int agility, int magic, List<Skill> skills) {
        setName(name);
        setHitpoints(hitpoints);
        setDamage(damage);
        setGold(gold);
        setMight(might);
        setAgility(agility);
        setMagic(magic);
        setSkills(skills);
    }

    // PUBLIC METHODS

    /**
     * Adds new skill to skills.
     *
     * @param skill to add
     */
    public void learnSkill(Skill skill) {
		for (Skill learntSkill : skills) {
			if (learntSkill.getName().equals(skill.getName())) {
				skills.remove(learntSkill);
			}
		}
		skills.add(skill);
    }

    /**
     * Removes skill from skills.
     *
     * @param skill to remove
     */
    public void unlearnSkill(Skill skill) {
        skills.remove(skill);
    }

    // GETTERS AND SETTERS

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
        if (might < 0) throw new IllegalArgumentException("Might of a hero can't be negative.");
        this.might = might;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        if (agility < 0) throw new IllegalArgumentException("Agility of a hero can't be negative.");
        this.agility = agility;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        if (magic < 0) throw new IllegalArgumentException("Agility of a hero can't be negative.");
        this.magic = magic;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
	    if (quest.getQuestState() != QuestState.NEW) {
		    throw new IllegalStateException("Hero can only be assigned to NEW quests.");
	    }

    	if (getQuest() == null) {
	    	this.quest = quest;
	    	quest.addHero(this);
	    	return;
	    }

	    if (getQuest().getQuestState() == QuestState.ONGOING) {
	    	throw new IllegalStateException("Hero is already on a quest.");
	    }

	    if (getQuest().getQuestState() == QuestState.NEW) {
		    getQuest().removeHero(this);
	    }
	    
	    this.quest = quest;
	    quest.addHero(this);
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public void setSkills(List<Skill> skills) {
	    if (skills == null) throw new IllegalArgumentException("Skills of a hero can't be null.");
        this.skills = skills;
    }

    // EQUALS, HASH, TOSTRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
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
    	StringBuilder sb = new StringBuilder("Class name: Hero");
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
		    for (Iterator<Skill> iterator = skills.iterator(); iterator.hasNext(); ) {
		    	Skill skill = iterator.next();

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
