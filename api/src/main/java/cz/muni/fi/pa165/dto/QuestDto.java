package cz.muni.fi.pa165.dto;

import java.util.List;

public class QuestDto extends BaseDto {
    private String name;
    private String location;
    private int reward;
    private int heroLimit;
    private QuestState state;
    private int performanceEvaluation;
    private List<HeroDto> assignedHeroes;
    private List<HeroDto> deadHeroes;
    private List<QuestMonsterDto> monsters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getHeroLimit() {
        return heroLimit;
    }

    public void setHeroLimit(int heroLimit) {
        this.heroLimit = heroLimit;
    }

    public QuestState getState() {
        return state;
    }

    public void setState(QuestState state) {
        this.state = state;
    }

    public int getPerformanceEvaluation() {
        return performanceEvaluation;
    }

    public void setPerformanceEvaluation(int performanceEvaluation) {
        this.performanceEvaluation = performanceEvaluation;
    }

    public List<HeroDto> getAssignedHeroes() {
        return assignedHeroes;
    }

    public void setAssignedHeroes(List<HeroDto> assignedHeroes) {
        this.assignedHeroes = assignedHeroes;
    }

    public List<HeroDto> getDeadHeroes() {
        return deadHeroes;
    }

    public void setDeadHeroes(List<HeroDto> deadHeroes) {
        this.deadHeroes = deadHeroes;
    }

    public List<QuestMonsterDto> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<QuestMonsterDto> monsters) {
        this.monsters = monsters;
    }
}
