package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.SkillDto;

import java.util.List;

public interface HeroFacade extends BaseFacade<HeroDto> {

    /**
     * Finds all living heroes who are not currently assigned to an ongoing quest.
     *
     * @return A {@link List} of heroes who are available to join a new quest.
     */
    List<HeroDto> findAvailable();

    /**
     * Finds all living heroes.
     *
     * @return A {@link List} of heroes who are still alive.
     */
    List<HeroDto> findAlive();

    /**
     * Finds all heroes with the given name.
     *
     * @param name The name to search by.
     *
     * @return A {@link List} of heroes who have the given name.
     */
    List<HeroDto> findByName(String name);

    /**
     * Finds all dead heroes.
     *
     * @return A {@link List} of heroes who are not alive anymore.
     */
    List<HeroDto> findDead();

    /**
     * Finds all heroes that are currently assigned to a given quest.
     *
     * @param quest The quest to search by.
     *
     * @return A {@link List} of heroes that are currently assigned to a given quest.
     */
    List<HeroDto> findOnQuest(QuestDto quest);

    /**
     * Finds all {@link Hero}es who have a given skill.
     *
     * @param skill The skill to search by.
     *
     * @return A {@link List} of {@link Hero}es with the given skill.
     */
    List<HeroDto> findWithSkill(SkillDto skill);
}
