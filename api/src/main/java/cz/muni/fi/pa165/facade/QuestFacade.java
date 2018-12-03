package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.QuestStateDto;

import java.util.List;

public interface QuestFacade extends BaseFacade<QuestDto> {

    /**
     * Finds all quests with certain state.
     *
     * @param state The stte of a quest.
     *
     * @return A {@link List} of quests with given state.
     */
    List<QuestDto> findByState(QuestStateDto state);

    /**
     * Finds all quests in certain location.
     *
     * @param location The {@link String} representing location.
     *
     * @return A {@link List} of quests in given location.
     */
    List<QuestDto> findByLocation(String location);

    /**
     * Finds all quests with certain monster.
     *
     * @param monster The monster in a quest.
     *
     * @return A {@link List} of quests with given monster.
     */
    List<QuestDto> findByMonster(MonsterDto monster);

    /**
     * Finds all quests with certain hero assigned.
     *
     * @param hero The hero on a quest.
     *
     * @return A {@link List} of quests with given hero assigned.
     */
    List<QuestDto> findByAssignedHero(HeroDto hero);
}
