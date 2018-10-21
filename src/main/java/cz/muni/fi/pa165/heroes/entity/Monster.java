package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Monster extends Actor {

    @Column(nullable = false)
    private String size;

    @OneToMany(targetEntity = Affinity.class, fetch = FetchType.LAZY)
    private List<Affinity> strengths;

    @OneToMany(targetEntity = Affinity.class, fetch = FetchType.LAZY)
    private List<Affinity> weaknesses;

    public Monster() { super(); }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    // TODO: Add accessors and mutators as you see fit
}
