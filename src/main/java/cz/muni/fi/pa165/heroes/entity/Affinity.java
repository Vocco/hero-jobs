package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Affinity describes a relation of an actor to an effect of a skill.
 *
 * Consider a following scenario:
 * Monster is weak to fire, therefore affinity with a name fire is in its weakness list.
 * Hero whose skillset contains a skill with a fire affinity will be more likely to be
 * recommended to join the quest regarding this monster.
 */
@Entity
public class Affinity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
    private int level;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of an affinity can't be null");
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level <= 0) throw new IllegalArgumentException("Affinity level must be greater than zero.");
        this.level = level;
    }
}
