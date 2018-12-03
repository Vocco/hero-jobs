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

    // EQUALS, HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof QuestDto)) return false;

        QuestDto other = (QuestDto) o;

        if (!name.equals(other.getName())) return false;
        if (!location.equals(other.getLocation())) return false;

        if (reward != other.getReward()) return false;
        if (heroLimit != other.getHeroLimit()) return false;
        if (state != other.getState()) return false;
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
}
