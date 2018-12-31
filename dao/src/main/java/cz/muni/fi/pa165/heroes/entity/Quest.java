package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Entity for a Quest (a job posting for heroes).
 *
 * Quests have a name, location, a reward for heroes, and a limit of maximum
 * heroes that may be assigned to it. Quests can be found in multiple states:
 * NEW, ONGOING, FAILED, or SUCCESSFUL. (See {@link QuestState}.)
 *
 * NEW quests can be (re)assigned heroes and monsters, and can have their
 * name, location, reward, and heroLimit modified.
 *
 * A quest becomes ONGOING either by calling its start() method, or when the
 * maximum number of heroes are assigned to it.
 *
 * FAILED and SUCCESSFUL quests can be assigned a performance evaluation.
 *
 * @author Vojtech Krajnansky (423126)
 */
@Entity
public class Quest implements Serializable {


	// ATTRIBUTES

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int reward;

    /** minimum of heroes to start quest */
    @Column(nullable = false)
    private int heroLimit;

    @Enumerated(value = EnumType.ORDINAL)
    private QuestState state;

    private int performanceEvaluation;

    @OneToMany(targetEntity = Hero.class, fetch = FetchType.LAZY)
    @JoinTable(name="quest_assignedheroes")
    private List<Hero> assignedHeroes = new ArrayList<>();

    @OneToMany(targetEntity = Hero.class, fetch = FetchType.LAZY)
    @JoinTable(name="quest_deadheroes")
    private List<Hero> deadHeroes = new ArrayList<>();

    @OneToMany(mappedBy = "quest", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<QuestMonster> monsters = new ArrayList<>();


    // CONSTRUCTORS

    public Quest() {}

    public Quest(String name, String location, int reward, int heroLimit) {
        if (name == null) throw new IllegalArgumentException("Name of a quest can't be null");
        if (location == null) throw new IllegalArgumentException("Location of a quest can't be null");
        if (reward <= 0) throw new IllegalArgumentException("Reward of a quest must be a positive integer");
        if (heroLimit <= 0) throw new IllegalArgumentException("HeroLimit of a quest must be a positive integer");

        this.name = name;
        this.location = location;
        this.reward = reward;
        this.heroLimit = heroLimit;

        state = QuestState.NEW;
    }


    // PUBLIC METHODS

    /**
     * Assigns a new hero to the quest.
     *
     * If the hero is already assigned to the quest, ignores. If the
     * assignment results in the hero limit being reached, starts the quest.
     *
     * @param hero The hero to assign to the quest.
     *
     * @exception IllegalStateException When called on a non-NEW quest.
     */
    public void addHero(Hero hero) {
        if (state != QuestState.NEW) throw new IllegalStateException("Heroes can only be added to NEW quests");

        if (!assignedHeroes.contains(hero)) {
            assignedHeroes.add(hero);

            if (assignedHeroes.size() == heroLimit) {
                state = QuestState.ONGOING;
            }
        }
    }

    /**
     * Assigns a new monster to the quest.
     *
     * If a monster of the same type is already assigned, increases the number
     * of monsters of this type assigned to the quest by one.
     *
     * @param monster The monster type to add to the quest.
     *
     * @exception IllegalStateException When called on a non-NEW quest.
     */
    public void addMonster(Monster monster) {
        if (state != QuestState.NEW) throw new IllegalStateException("Monsters can only be added to NEW quests");

        QuestMonster questMonster = new QuestMonster(this, monster);

        int index = monsters.indexOf(questMonster);
        if (index == -1) {
            monsters.add(questMonster);
            monster.assignToQuest(questMonster);
        } else {
            monsters.get(index).increaseCount();
        }
    }

    /**
     * Removes a hero from the quest.
     *
     * If the hero has not been assigned to the quest, ignores.
     *
     * @param hero The hero to remove from this quest.
     *
     * @exception IllegalStateException When called on a non-NEW quest.
     */
    public void removeHero(Hero hero) {
        if (state != QuestState.NEW) throw new IllegalStateException("Heroes can only be removed from NEW quests");

        if (assignedHeroes.contains(hero)) {
            assignedHeroes.remove(hero);
        }
    }

    /**
     * Removes a monster from the quest.
     *
     * If this monster type has not been assigned to the quest, ignores. If it
     * has, removes one unit of this monster type from the quest. If the
     * removed unit is the last of this type, removes this type from the quest
     * completely.
     *
     * @param monster The monster type to remove from this quest.
     *
     * @exception IllegalStateException When called on a non-NEW quest.
     */
    public void removeMonster(Monster monster) {
        if (state != QuestState.NEW) throw new IllegalStateException("Monsters can only be removed from NEW quests");

        for (Iterator<QuestMonster> iter = monsters.iterator(); iter.hasNext(); ) {
            QuestMonster questMonster = iter.next();

            if (questMonster.getQuest().equals(this) && questMonster.getMonster().equals(monster)) {

                if (questMonster.getMonsterCount() <= 0) {
                    iter.remove();
                    questMonster.getMonster().removeFromQuest(questMonster);
                    questMonster.setQuest(null);
                    questMonster.setMonster(null);	
                } else {
                    questMonster.decreaseCount();
                }

                break;
            }
        }
    }

    /**
     * Sets this quest's state to ONGOING.
     *
     * @exception IllegalStateException When called on a non-NEW quest.
     */
    public void start() {
        if (state != QuestState.NEW) throw new IllegalStateException("Only NEW quests can be started");
        state = QuestState.ONGOING;
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of a quest can't be null");
        if (state != QuestState.NEW) throw new IllegalStateException("Only NEW quests can have their name changed");
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null) throw new IllegalArgumentException("Location of a quest can't be null");
        if (state != QuestState.NEW) throw new IllegalStateException("Only NEW quests can have the location changed");
        this.location = location;
    }

    public int getReward() {
      return reward;
    }

    public void setReward(int reward) {
        if (reward <= 0) throw new IllegalArgumentException("Reward of a quest must be a positive integer");
        if (state != QuestState.NEW) throw new IllegalStateException("Only NEW quests can have their reward changed");
        this.reward = reward;
    }

    public int getHeroLimit() {
        return heroLimit;
    }

    public void setHeroLimit(int heroLimit) {
        if (heroLimit <= 0) throw new IllegalArgumentException("HeroLimit of a quest must be a positive integer");
        if (state != QuestState.NEW) throw new IllegalStateException("Only NEW quests can have their heroLimit changed");
        this.heroLimit = heroLimit;
    }

    public QuestState getQuestState() {
        return state;
    }

    public int getPerformanceEvaluation() {
        return performanceEvaluation;
    }

    public void setPerformanceEvaluation(int performanceEvaluation) {
        if (state == QuestState.NEW || state == QuestState.ONGOING) {
            throw new IllegalStateException("Cannot set performance evaluation to NEW or ONGOING quests");
        }

        if (performanceEvaluation < 0 || performanceEvaluation > 10) {
            throw new IllegalArgumentException("Performance evaluation must be an integer between 0 and 10 (incl.)");
        }

        this.performanceEvaluation = performanceEvaluation;
    }

    public List<Hero> getAssignedHeroes() {
        return Collections.unmodifiableList(assignedHeroes);
    }

    public List<Hero> getDeadHeroes() {
        return Collections.unmodifiableList(deadHeroes);
    }

    public List<QuestMonster> getMonsters() {
        return Collections.unmodifiableList(monsters);
    }


    // EQUALS, HASH, TOSTRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Quest)) return false;

        Quest other = (Quest) o;

        if (!name.equals(other.getName())) return false;
        if (!location.equals(other.getLocation())) return false;

        if (reward != other.getReward()) return false;
        if (heroLimit != other.getHeroLimit()) return false;
        if (state != other.getQuestState()) return false;
        if (performanceEvaluation != other.getPerformanceEvaluation()) return false;

        if (!assignedHeroes.equals(other.getAssignedHeroes())) return false;
        if (!deadHeroes.equals(other.getAssignedHeroes())) return false;
        if (!monsters.equals(other.getMonsters())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 11 * result + name.hashCode();
        result = 11 * result + location.hashCode();
        result = 11 * result + reward;
        result = 11 * result + heroLimit;
        result = 11 * result + state.hashCode();
        result = 11 * result + performanceEvaluation;
        result = 11 * result + assignedHeroes.hashCode();
        result = 11 * result + deadHeroes.hashCode();
        result = 11 * result + monsters.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ClassName: Quest");
        sb.append("\nid: ");
        sb.append(id.toString());
        sb.append("\nname: ");
        sb.append(name);
        sb.append("\nlocation: ");
        sb.append(location);
        sb.append("\nreward: ");
        sb.append(Integer.toString(reward));
        sb.append("\nheroLimit: ");
        sb.append(Integer.toString(heroLimit));
        sb.append("\nstate: ");
        sb.append(state.toString());
        sb.append("\nperfomanceEvaluation: ");

        if (state != QuestState.NEW && state != QuestState.ONGOING) {
            sb.append(Integer.toString(performanceEvaluation));
        } else {
            sb.append("N/A");
        }

        sb.append("\nassignedHeroes: ");
        if (assignedHeroes.size() != 0) {
            appendHeroNames(assignedHeroes, sb);
        } else {
           sb.append("none");
        }

        sb.append("\ndeadHeroes: ");
        if (deadHeroes.size() != 0) {
            appendHeroNames(deadHeroes, sb);
        } else {
            sb.append("none");
        }

        sb.append("\nmonsters: ");
        if (monsters.size() != 0) {
            for (Iterator<QuestMonster> iter = monsters.iterator(); iter.hasNext(); ) {
                QuestMonster questMonster = iter.next();

                sb.append(questMonster.getMonster().getName());
                sb.append(" (");
                sb.append(Integer.toString(questMonster.getMonsterCount()));
                sb.append(")");

                if (iter.hasNext()) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("none");
        }

        return sb.toString();
    }


    // PRIVATE HELPER METHODS

    /**
     * Appends to given {@link StringBuilder} a list of hero names from a {@Link List} of {@link Hero}es.
     *
     * @param heroes A {@link List} of {@link Hero}es to be parsed.
     * @param sb A {@link StringBuilder} to which to append the comma (and space) separated list of hero names.
     */
    private void appendHeroNames(List<Hero> heroes, StringBuilder sb) {
        for (Iterator<Hero> iter = heroes.iterator(); iter.hasNext(); ) {
            Hero hero = iter.next();

            sb.append(hero.getName());

            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
    }
}
