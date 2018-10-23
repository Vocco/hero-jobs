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

    @OneToMany(targetEntity = Affinity.class, fetch = FetchType.LAZY)
    private List<Affinity> strengths = new ArrayList<>();

    @OneToMany(targetEntity = Affinity.class, fetch = FetchType.LAZY)
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
        if (damage <= 0) throw new IllegalArgumentException("A monster must have more than 0 damage");
        setDamage(damage);
        setSize(size);
        setStrengths(strengths);
        setWeaknesses(weaknesses);

    }

    // PUBLIC METHODS

    /**
     * if not already in list adds new weakness to the weaknesses
     * @param nWeakness
     */
    public void addWeakness(Affinity nWeakness){
        if (!weaknesses.contains(nWeakness)){
            weaknesses.add(nWeakness);
        }
    }

    /**
     * if not already in list adds new strength to the strengths
     * @param nStrength
     */
    public void addStrength(Affinity nStrength){
        if (!strengths.contains(nStrength)){
            strengths.add(nStrength);
        }
    }

    /**
     * if not already signed to specific quest, adds connection to quest through QuestMonster
     * @param questMonster
     */
    public void assignToQuest(QuestMonster questMonster){
        if (!assignedToQuests.contains(questMonster)){
            assignedToQuests.add(questMonster);
        }
    }

    // GETTERS AND SETTERS

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (size == null) throw new IllegalArgumentException("Size of a monster can't be null");
        this.size = size;
    }

    public List<Affinity> getStrengths() {
        return Collections.unmodifiableList(strengths);
    }

    public void setStrengths(List<Affinity> strengths) {
        if (strengths.isEmpty()) throw new IllegalArgumentException("Strengths of monster cannot be set to empty list");
        this.strengths = strengths;
    }

    public List<Affinity> getWeaknesses() {
        return Collections.unmodifiableList(weaknesses);
    }

    public void setWeaknesses(List<Affinity> weaknesses) {
        if (weaknesses.isEmpty()) throw new IllegalArgumentException("Weaknesses of monster cannot be set to empty list");
        this.weaknesses = weaknesses;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Monster)) return false;

        Monster monster = (Monster) o;
        if (getName() != monster.getName() ||
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
