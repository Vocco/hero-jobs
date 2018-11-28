package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.SkillDto;

import java.util.List;

public interface SkillFacade {

  /**
   * Gets all the skills available.
   *
   * @return A {@link List} of all existing skills.
   */
  List<SkillDto> findAll();

  /**
   * Gets a single skill by its ID, or null if such a skill doesn't exist.
   *
   * @param id The ID of the skill being accessed.
   *
   * @return The skill with given ID, or null if not found.
   */
  SkillDto findById(Long id);

  /**
   * Updates the skill's attributes.
   *
   * @param skill The updated skill.
   *
   * @return The updated skill.
   */
  SkillDto update(SkillDto skill);

  /**
   * Persists a new skill.
   *
   * @param skill The skill to be persisted.
   *
   * @return true if the operation succeeded, false otherwise.
   */
  boolean save(SkillDto skill);

  /**
   * Deletes a skill.
   *
   * @param skill The skill to be deleted.
   *
   * @return true if the operation succeeded, false otherwise.
   */
  boolean delete(SkillDto skill);

  /**
   * Deletes a skill with given ID.
   *
   * @param id The ID of the skill to be deleted.
   *
   * @return true if the operation succeeded, false otherwise.
   */
  boolean deleteById(Long id);

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
