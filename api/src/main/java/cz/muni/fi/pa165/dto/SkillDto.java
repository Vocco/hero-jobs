package cz.muni.fi.pa165.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A data transfer object for skills.
 *
 * @author Vojtech Krajnansky (423126)
 */
public class SkillDto {

    // ATTRIBUTES

    private Long id;

    private String name;

    private List<AffinityDto> affinities = new ArrayList<AffinityDto>();
    private int baseDamage;

    // CONSTRUCTORS

    public SkillDto () {}

    public SkillDto(String name, List<AffinityDto> affinities, int baseDamage) {
        this.setName(name);
        // If you ask why Affinity[0] see: https://shipilev.net/blog/2016/arrays-wisdom-ancients/
        this.setAffinities(affinities.toArray(new AffinityDto[0]));
        this.setBaseDamage(baseDamage);
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of a skill can't be null");
        this.name = name;
    }

    public List<AffinityDto> getAffinities() {
        return Collections.unmodifiableList(affinities);
    }

    public void setAffinities(AffinityDto... affinities) {
        if (affinities == null) throw new IllegalArgumentException("Affinities can't be null");

        this.affinities.clear();
        this.affinities.addAll(Arrays.asList(affinities));
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    // EQUALS AND HASHCODE

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof SkillDto)) return false;

        SkillDto skill = (SkillDto) o;

        if (getBaseDamage() != skill.getBaseDamage()) return false;
        if (!getName().equals(skill.getName())) return false;
        return getAffinities() != null ? getAffinities().equals(skill.getAffinities()) : skill.getAffinities() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getAffinities() != null ? getAffinities().hashCode() : 0);
        result = 31 * result + getBaseDamage();
        return result;
    }

  @Override
  public String toString() {
      return String.format("SkillDto: %d\n\tname: %s\n\tbaseDamage: %d\n\taffinities:\n%s\n",
              getId(), getName(), getBaseDamage(), getAffinities());
  }
}
