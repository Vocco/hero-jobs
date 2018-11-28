package cz.muni.fi.pa165.dto;

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
}
