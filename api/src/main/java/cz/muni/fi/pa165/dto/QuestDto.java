package cz.muni.fi.pa165.dto;

import java.util.List;

public class QuestDto extends BaseDto {

    private Long id;
    private String name;
    private String location;
    private int reward;
    private int heroLimit;
    private QuestStateDto state;
    private int performanceEvaluation;
    private List<HeroDto> assignedHeroes;
    private List<HeroDto> deadHeroes;
    private List<QuestMonsterDto> monsters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public QuestStateDto getState() {
        return state;
    }

    public void setState(QuestStateDto state) {
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
