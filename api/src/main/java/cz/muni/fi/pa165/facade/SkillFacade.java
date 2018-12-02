package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.SkillDto;

import java.util.List;

public interface SkillFacade extends BaseFacade<SkillDto> {

  /**
   * Finds all skills with given name - note that name doesn't have to be unique.
   *
   * @param name The name to search by.
   *
   * @return All skills with given name.
   */
  List<SkillDto> findByName(String name);

  /**
   * Finds all skills containing given affinity.
   *
   * @param affinity The affinity to search by.
   *
   * @return All skills with given affinity.
   */
  List<SkillDto> findWithAffinity(AffinityDto affinity);

  /**
   * Finds all skills with given base damage
   *
   * @param baseDamage The base damage of the skills to search for.
   *
   * @return @return A {@link List} of skills with given base damage
   */
  List<SkillDto> findWithBaseDamage(int baseDamage);

  /**
   * Finds all skills with base damage greater or equal to given parameter.
   *
   * @param minBaseDamage The base damage to search by.
   *
   * @return A {@link List} of skills with base damage greater or equal to given parameter.
   */
  List<SkillDto> findWithGreaterBaseDamage(int minBaseDamage);
}
