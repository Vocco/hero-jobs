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

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("questId")
    private Quest quest;

    @ManyToOne(fetch = FetchType.EAGER)
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


    // EQUALS, HASH, TOSTRING

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ClassName: QuestMonster");
        sb.append("\nid:\n\n");
        sb.append(id.toString());

        sb.append("\n\nquest: ");
        if (quest == null) {
            sb.append("N/A");
        } else {
            sb.append(quest.getName());
        }

        if (monster == null) {
            sb.append("\nmonster: N/A\nmonsterCount: N/A");
        } else {
            sb.append("\nmonster: ");
            sb.append(monster.getName());
            sb.append("\nmonsterCount: ");
            sb.append(Integer.toString(monsterCount));
        }

        return sb.toString();
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


        // EQUALS, HASH, TOSTRING

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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("ClassName: QuestMonsterId");
            sb.append("\nquestId: ");
            sb.append(questId.toString());
            sb.append("\nmonserId: ");
            sb.append(monsterId.toString());
            return sb.toString();
        }
    }
}
