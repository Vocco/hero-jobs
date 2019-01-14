package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entity to represent monster/monster type so except for own attributes consists of
 * "properties of entities capable of dying and dealing damage" specified in Actor.
 *
 * Monster have id, name, hitpoints and damage inherited from Actor
 * and size, strengths, weaknesses, assignedToQuests of its own.
 *
 * Monster (as a type of monster) can be assigned to many quest through list of QuestMonsters
 *
 * @author Jakub Strmen
 */
@Entity
public class Monster extends Actor {

    // ATTRIBUTES

    @Column(nullable = false)
    private String size;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name="monster_strengthaffinities",
        joinColumns = @JoinColumn(name = "monster_id"),
        inverseJoinColumns = @JoinColumn(name = "affinity_id")
    )
    private List<Affinity> strengths = new ArrayList<>();

        @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
        @JoinTable(
            name="monster_weaknessaffinities",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "affinity_id")
        )
    private List<Affinity> weaknesses = new ArrayList<>();

    /** refers to which quests is current monster type assigned */
    @OneToMany(mappedBy = "monster")
    private List<QuestMonster> assignedToQuests = new ArrayList<>();

    // CONSTRUCTORS

    private Monster() { super(); }

    public Monster(String name, int hitpoints, int damage,
                   String size, List<Affinity> strengths, List<Affinity> weaknesses) {
        setName(name);
        setHitpoints(hitpoints);
        setDamage(damage);
        setSize(size);
        setStrengths(strengths);
        setWeaknesses(weaknesses);
    }

    public Monster(String name, int hitpoints, int damage, String size) {
        setName(name);
        setHitpoints(hitpoints);
        setDamage(damage);
        setSize(size);
    }


    // PUBLIC METHODS

    /**
     * if not already in list adds new weakness to the weaknesses
     * @param nWeakness
     */
    public void addWeakness(Affinity nWeakness){
        if (!weaknesses.contains(nWeakness)){
            weaknesses.add(nWeakness);
            nWeakness.setMonster(this);
        }
    }


    public void removeWeakness(Affinity oldWeakness){
        weaknesses.remove(oldWeakness);
        oldWeakness.setMonster(null);
    }

    public void removeAllWeaknesses(){
        weaknesses.clear();
    }

    /**
     * if not already in list adds new strength to the strengths
     * @param nStrength
     */
    public void addStrength(Affinity nStrength){
        if (!strengths.contains(nStrength)){
            strengths.add(nStrength);
            nStrength.setMonster(this);
        }
    }

    public void removeStrength(Affinity oldStrength){
        strengths.remove(oldStrength);
        oldStrength.setMonster(null);
    }

    public void removeAllStrengths(){
        strengths.clear();
    }

    /**
     * if not already signed to specific quest, adds connection to quest through QuestMonster
     * @param questMonster
     */
    public void assignToQuest(QuestMonster questMonster){
        if (!assignedToQuests.contains(questMonster)){
            assignedToQuests.add(questMonster);
            questMonster.setMonster(this);
        }
    }

    /**
     * from various reasons we may want to remove assignment of monster to quest
     * (quest deleted, failed, succesful)
     * @param questMonster
     */
    public void removeFromQuest(QuestMonster questMonster){
        assignedToQuests.remove(questMonster);
    }

    // GETTERS AND SETTERS

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if ( size == null ) throw new IllegalArgumentException("Size of a monster can't be null");
        this.size = size;
    }

    public List<Affinity> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<Affinity> strengths) {
        if ( strengths == null ) throw new IllegalArgumentException("Strengths of monster cannot be null");
        this.strengths = strengths;
        for (Affinity a : strengths) {
            a.setMonster(this);
        }
    }

    public List<Affinity> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<Affinity> weaknesses) {
        if ( weaknesses == null ) throw new IllegalArgumentException("Weaknesses of monster cannot be null");
        this.weaknesses = weaknesses;
        for (Affinity a : strengths) {
            a.setMonster(this);
        }
    }

    @Override
    public void setDamage(int damage) {
        if ( damage <= 0 ) throw new IllegalArgumentException("A monster must have more than 0 damage");
        super.setDamage(damage);
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Monster)) return false;

        Monster monster = (Monster) o;
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
