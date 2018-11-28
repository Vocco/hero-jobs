package cz.muni.fi.pa165.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MonsterDto {

    // ATTRIBUTES

    private Long id;

    private String name;
    private int hitpoints;
    private int damage;
    private String size;

    private List<AffinityDto> strengths = new ArrayList<>();
    private List<AffinityDto> weaknesses = new ArrayList<>();

    private List<QuestMonsterDto> assignedToQuests = new ArrayList<>();

    // CONSTRUCTORS

    private MonsterDto() { super(); }

    public MonsterDto(String name, int hitpoints, int damage,
                      String size, List<AffinityDto> strengths, List<AffinityDto> weaknesses) {
        setName(name);
        setHitpoints(hitpoints);
        setDamage(damage);
        setSize(size);
        setStrengths(strengths);
        setWeaknesses(weaknesses);
    }

    public MonsterDto(String name, int hitpoints, int damage, String size) {
        setName(name);
        setHitpoints(hitpoints);
        setDamage(damage);
        setSize(size);
    }


    // PUBLIC METHODS

    /**
     * if not already in list adds new weakness to the weaknesses
     * @param nWeakness
     */
    public void addWeakness(AffinityDto nWeakness){
        if (!weaknesses.contains(nWeakness)){
            weaknesses.add(nWeakness);
        }
    }


    public void removeWeakness(AffinityDto oldWeakness){
        weaknesses.remove(oldWeakness);
    }

    public void removeAllWeaknesses(){
        weaknesses.clear();
    }

    /**
     * if not already in list adds new strength to the strengths
     * @param nStrength
     */
    public void addStrength(AffinityDto nStrength){
        if (!strengths.contains(nStrength)){
            strengths.add(nStrength);
        }
    }

    public void removeStrength(AffinityDto oldStrength){
        strengths.remove(oldStrength);
    }

    public void removeAllStrengths(){
        strengths.clear();
    }

    /**
     * if not already signed to specific quest, adds connection to quest through QuestMonster
     * @param questMonster
     */
    public void assignToQuest(QuestMonsterDto questMonster){
        if (!assignedToQuests.contains(questMonster)){
            assignedToQuests.add(questMonster);
        }
    }

    /**
     * from various reasons we may want to remove assignment of monster to quest
     * (quest deleted, failed, succesful)
     * @param questMonster
     */
    public void removeFromQuest(QuestMonsterDto questMonster){
        assignedToQuests.remove(questMonster);
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of a monster can't be null");
        this.name = name;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        if (hitpoints <= 0) throw new IllegalArgumentException("A hero must have more than 0 hitpoints");
        this.hitpoints = hitpoints;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if ( damage <= 0 ) throw new IllegalArgumentException("A monster must have more than 0 damage");
        this.damage = damage;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if ( size == null ) throw new IllegalArgumentException("Size of a monster can't be null");
        this.size = size;
    }

    public List<AffinityDto> getStrengths() {
        return Collections.unmodifiableList(strengths);
    }

    public void setStrengths(List<AffinityDto> strengths) {
        if ( strengths == null ) throw new IllegalArgumentException("Strengths of monster cannot be null");
        this.strengths = strengths;
    }

    public List<AffinityDto> getWeaknesses() {
        return Collections.unmodifiableList(weaknesses);
    }

    public void setWeaknesses(List<AffinityDto> weaknesses) {
        if ( weaknesses == null ) throw new IllegalArgumentException("Weaknesses of monster cannot be null");
        this.weaknesses = weaknesses;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof MonsterDto)) return false;

        MonsterDto monster = (MonsterDto) o;
        if (!getName().equals(monster.getName())
            || getHitpoints() != monster.getHitpoints()
            || getDamage() != monster.getDamage()) {
            return false;
        }

        return Objects.equals(size, monster.size);
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 17 * result + getName().hashCode();
        result = 17 * result + getHitpoints();
        result = 17 * result + getDamage();
        result = 17 * result + getSize().hashCode();
        return result;
    }
}
