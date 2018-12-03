package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;

import java.util.List;

public interface MonsterFacade extends BaseFacade<MonsterDto> {

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