package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Hero extends Actor {

    private int gold;
    private boolean isAlive;
    private int might;
    private int agility;
    private int magic;

    // TODO: Add a relation to quests
    private Quest quest;

    @OneToMany(targetEntity = Skill.class)
    private List<Skill> skills;

    // TODO: Add accessors and mutators as you see fit
}
