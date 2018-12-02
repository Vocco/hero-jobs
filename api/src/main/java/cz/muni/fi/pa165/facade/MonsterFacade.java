package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;

import java.util.List;

public interface MonsterFacade {

    /**
     * Gets all the monsters available.
     *
     * @return A {@link List} of all existing monsters.
     */
    List<MonsterDto> findAll();

    /**
     * Gets a single monster by its ID, or null if such a monster doesn't exist.
     *
     * @param id The ID of the monster being accessed.
     *
     * @return The monster with given ID, or null if not found.
     */
    MonsterDto findById(Long id);

    /**
     * Updates the monster's attributes.
     *
     * @param monster The updated monster.
     *
     * @return The updated monster.
     */
    MonsterDto update(MonsterDto monster);

    /**
     * Persists a new monster.
     *
     * @param monster The monster to be persisted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean save(MonsterDto monster);

    /**
     * Deletes a monster.
     *
     * @param monster The monster to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean delete(MonsterDto monster);

    /**
     * Deletes a monster with given ID.
     *
     * @param id The ID of the monster to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean deleteById(Long id);

    /**
     * Finds all monsters with given affinity as a strength.
     *
     * @param strength A name-level pair.
     *
     * @return List of monsters with matching strength or empty list if none exist
     */
    List<MonsterDto> findWithStrength(AffinityDto strength);

    /**
     * Finds all monsters with given affinity as a weakness.
     *
     * @param weakness A name-level pair.
     *
     * @return List of monsters with matching weakness or empty list if none exist
     */
    List<MonsterDto> findWithWeakness(AffinityDto weakness);

    /**
     * Finds all monsters of given size.
     *
     * @param size A string parameter denoting a size of the monster.
     *
     * @return A list of monsters with a specified size or empty if none exist
     */
    List<MonsterDto> findWithSize(String size);

    /**
     * Finds all monsters on a given quest.
     *
     * @param quest Quest containing monsters to look for
     *
     * @return A list of monsters with a given quest
     */
    List<MonsterDto> findByQuest(QuestDto quest);

    // TODO: Move these methods to AffinityFacade (and reflect in service layer).

    /**
     * Finds all weaknesses of monsters on a given quest.
     *
     * @param quest Quest containing monsters to look for
     *
     * @return A list of all weaknesses of monsters with a given quest
     */
    List<AffinityDto> findAllWeaknessesOfMonstersOnQuest(QuestDto quest);

    /**
     * Finds all strengths of monsters on a given quest.
     *
     * @param quest Quest containing monsters to look for
     *
     * @return A list of all strengths of monsters with a given quest
     */
    List<AffinityDto> findAllStrengthsOfMonstersOnQuest(QuestDto quest);
}