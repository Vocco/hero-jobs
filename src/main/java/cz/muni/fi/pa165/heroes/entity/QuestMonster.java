package cz.muni.fi.pa165.heroes.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import java.io.Serializable;

import java.util.Objects;

/**
 * Composite entity for the M-to-N relationship between {@link Quest} and {@link Monster}.
 *
 * This is needed because we need additional information about the number of
 * monsters of given type present in the {@link Quest}, so a simple join table is not
 * sufficient.
 *
 * @author Vojtech Krajnansky (423126)
 */
@Entity
public class QuestMonster {


    // ATTRIBUTES

    @EmbeddedId
    private QuestMonsterId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("questId")
    private Quest quest;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("monsterId")
    private Monster monster;

    @Column(nullable = false)
    private int monsterCount;


    // CONSTRUCTORS

    private QuestMonster() {}

    public QuestMonster(Quest quest, Monster monster) {
        if (quest == null) throw new IllegalArgumentException("Quest can't be null");
        if (monster == null) throw new IllegalArgumentException("Monster can't be null");

        this.quest = quest;
        this.monster = monster;
        this.id = new QuestMonsterId(quest.getId(), monster.getId());

        monsterCount = 1;
    }

    public QuestMonster(Quest quest, Monster monster, int monsterCount) {
        if (quest == null) throw new IllegalArgumentException("Quest can't be null");
        if (monster == null) throw new IllegalArgumentException("Monster can't be null");
        if (monsterCount <= 0) throw new IllegalArgumentException("MonsterCount must be a positive integer");

        this.quest = quest;
        this.monster = monster;
        this.id = new QuestMonsterId(quest.getId(), monster.getId());

        this.monsterCount = monsterCount;
    }


    // PUBLLIC METHODS

    /**
     * Increases the count of monsters of this monster type in the quest.
     */
    public void increaseCount() {
        monsterCount++;
    }

    /**
     * Decreases the count of monsters of this monster type in the quest.
     *
     * This should only be called when monsterCount is positive.
     *
     * @exception IllegalStateException When called on QuestMonster with
     *            monsterCount already 0.
     */
    public void decreaseCount() {
        if (monsterCount == 0) throw new IllegalStateException("Cannot decrease count of monsters to negative integer");
        monsterCount--;
    }


    // GETTERS AND SETTERS  

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Monster getMonster() {
        return this.monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public int getMonsterCount() {
        return monsterCount;
    }


    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof  QuestMonster)) return false;

        QuestMonster other = (QuestMonster) o;
        return Objects.equals(quest, other.quest)
            && Objects.equals(monster, other.monster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quest, monster);
    }


    // NESTED CLASSES

    /**
     * Composite entity identifier for QuestMonster.
     *
     * @author Vojtech Krajnansky (423126)
     */
    @Embeddable
    public static class QuestMonsterId implements Serializable {


        // ATTRIBUTES

        @Column(name = "quest_id")
        private Long questId;

        @Column(name = "monster_id")
        private Long monsterId;


        // CONSTRUCTORS

        private QuestMonsterId() {}

        public QuestMonsterId(Long questId, Long monsterId) {
            if (questId == null) throw new IllegalArgumentException("QuestId can't be null");
            if (monsterId == null) throw new IllegalArgumentException("MonsterId can't be null");

            this.questId = questId;
            this.monsterId = monsterId;
        }

        // GETTERS

        public Long getQuestId() {
            return questId;
        }

        public Long getMonsterId() {
            return monsterId;
        }


        // EQUALS AND HASH

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof  QuestMonsterId)) return false;

            QuestMonsterId other = (QuestMonsterId) o;
            return Objects.equals(questId, other.questId)
                && Objects.equals(monsterId, other.monsterId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(questId, monsterId);
        }
    }
}
