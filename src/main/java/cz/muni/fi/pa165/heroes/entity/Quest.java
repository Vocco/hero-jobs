package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Quest {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
    private String location;
    private int reward;
    private int heroLimit;

    @Enumerated(value = EnumType.ORDINAL)
    private QuestState state;

    // TODO: add a relation to heroes
    private List<Hero> assignedHeroes;
    private List<Hero> deadHeroes;

    @ManyToMany(targetEntity = Monster.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "quest_monster",
            joinColumns = @JoinColumn(name = "quest_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "monster_id", referencedColumnName = "id")
    )
    private List<Monster> monsters;
    private int performanceEvaluation;

    // TODO: Add accessors and mutators as you see fit
}
