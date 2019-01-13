package cz.muni.fi.pa165.heroes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Affinity describes a relation of an actor to an effect of a skill.
 *
 * Consider a following scenario:
 * Monster is weak to fire, therefore affinity with a name fire is in its weakness list.
 * Hero whose skillset contains a skill with a fire affinity will be more likely to be
 * recommended to join the quest regarding this monster.
 *
 * @author Michal Pavúk
 */
@Entity
public class Affinity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    private int level;

    @ManyToOne
    private Monster monster;

    public Affinity() {}

    public Affinity(String name, int level) {
        this.setName(name);
        this.setLevel(level);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof  Affinity)) return false;

        Affinity affinity = (Affinity) o;

        if (getLevel() != affinity.getLevel()) return false;
        return getName().equals(affinity.getName());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getLevel();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Affinity: %d\n\tname: %s\n\tlevel: %d\n", getId(), getName(), getLevel());
    }
}
