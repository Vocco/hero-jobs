package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

/**
 * Skill is a named group of affinities usable by Hero.
 */
@Entity
public class Skill {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(targetEntity = Affinity.class)
    private List<Affinity> effects;
    private int baseDamage;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of a skill can't be null");
        this.name = name;
    }

    public List<Affinity> getEffects() {
        return Collections.unmodifiableList(effects);
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
        return getEffects() != null ? getEffects().equals(skill.getEffects()) : skill.getEffects() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getEffects() != null ? getEffects().hashCode() : 0);
        result = 31 * result + getBaseDamage();
        return result;
    }
}
