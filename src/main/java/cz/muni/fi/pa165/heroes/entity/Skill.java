package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

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
        return effects;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
}
