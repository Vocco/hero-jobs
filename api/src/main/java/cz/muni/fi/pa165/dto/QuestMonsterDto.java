package cz.muni.fi.pa165.dto;

import java.util.Objects;

public class QuestMonsterDto {
    private QuestMonsterId id;
    private QuestDto quest;
    private MonsterDto monster;
    private int monsterCount;

    public QuestDto getQuest() {
        return quest;
    }

    public void setQuest(QuestDto quest) {
        this.quest = quest;
    }

    public MonsterDto getMonster() {
        return this.monster;
    }

    public void setMonster(MonsterDto monster) {
        this.monster = monster;
    }

    public int getMonsterCount() {
        return monsterCount;
    }

    public void setMonsterCount(int monsterCount) {
        this.monsterCount = monsterCount;
    }

    public static class QuestMonsterId {

        private Long questId;
        private Long monsterId;

        public Long getQuestId() {
            return questId;
        }

        public Long getMonsterId() {
            return monsterId;
        }

        public void setQuestId(Long questId) {
            this.questId = questId;
        }

        public void setMonsterId(Long monsterId) {
            this.monsterId = monsterId;
        }
    }

    // EQUALS, HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof  QuestMonsterDto)) return false;

        QuestMonsterDto other = (QuestMonsterDto) o;
        return Objects.equals(quest, other.quest)
                && Objects.equals(monster, other.monster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quest, monster);
    }
}
