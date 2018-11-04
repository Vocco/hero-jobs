package cz.muni.fi.pa165.heroes.dao;

import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;

import java.util.List;

/**
 * A monster-specific DAO interface
 *
 * @author Michal Pavúk
 */
public interface MonsterDAO extends DAO<Monster> {
    /**
     * @param strength A name-level pair, see {@link Affinity}
     * @return List of monsters with matching strength or empty list if none exist
     */
    List<Monster> findWithStrength(Affinity strength);

    /**
     * @param weakness A name-level pair, see {@link Affinity}
     * @return List of monsters with matching weakness or empty list if none exist
     */
    List<Monster> findWithWeakness(Affinity weakness);

    /**
     * @param size A string parameter denoting a size of the monster.
     * @return A list of monsters with a specified size or empty if none exist
     */
    List<Monster> findWithSize(String size);

    /**
     * @param quest Quest containing monsters to look for
     * @return A list of monsters with a given quest
     */
    List<Monster> findByQuest(Quest quest);
}
