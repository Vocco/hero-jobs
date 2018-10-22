package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.*;

/**
 * Actor class describes common properties of entities capable of dying and dealing damage.
 * For example: Heroes, Monsters or Items.
 * Each class deriving from Actor will have its inherited attributes in its table,
 * to prevent confusion and avoid unnecessary table joins on entity fetch.
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Actor {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
    private int hitpoints;
    private int damage;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of an actor can't be null");
        this.name = name;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        if (hitpoints <= 0) throw new IllegalArgumentException("An actor must have more than 0 hitpoints");
        this.hitpoints = hitpoints;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
