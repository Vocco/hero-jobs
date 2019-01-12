package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Skill is a named group of affinities usable by Hero.
 *
 * @author Michal Pavúk
 */
@Entity
public class Skill implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;


    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "affinity_id")
    )
    private List<Affinity> affinities = new ArrayList<Affinity>();
    private int baseDamage;

    public Skill () {}

    public Skill(String name, List<Affinity> affinities, int baseDamage) {
        this.setName(name);
        // If you ask why Affinity[0] see: https://shipilev.net/blog/2016/arrays-wisdom-ancients/
        this.setAffinities(affinities.toArray(new Affinity[0]));
        this.setBaseDamage(baseDamage);
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
        if (name == null) throw new IllegalArgumentException("Name of a skill can't be null");
        this.name = name;
    }

    public List<Affinity> getAffinities() {
        return affinities;
    }

    public void setAffinities(Affinity... affinities) {
        if (affinities == null) throw new IllegalArgumentException("Affinities can't be null");

        this.affinities.clear();
        this.affinities.addAll(Arrays.asList(affinities));
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Skill)) return false;

        Skill skill = (Skill) o;

        if (getBaseDamage() != skill.getBaseDamage()) return false;
        if (!getName().equals(skill.getName())) return false;
        return getAffinities() != null ? getAffinities().equals(skill.getAffinities()) : skill.getAffinities() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getAffinities() != null ? getAffinities().hashCode() : 0);
        result = 31 * result + getBaseDamage();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Skill: %d\n\tname: %s\n\tbaseDamage: %d\n\taffinities:\n%s\n",
                getId(), getName(), getBaseDamage(), getAffinities());
    }
}
