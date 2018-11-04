package cz.muni.fi.pa165.heroes.dao;

import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;

import java.util.List;

/**
 * Quest specific DAO.
 *
 * @author Metodej Klang
 */
public interface QuestDAO {


	/**
	 * Finds all {@link Quest}s with certain {@link QuestState}
	 *
	 * @param state The {@link QuestState} of a {@link Quest}.
	 * @return A {@link List} of {@link Quest}s with given {@link QuestState}.
	 */
	List<Quest> findByState(QuestState state);

	/**
	 * Finds all {@link Quest}s in certain location.
	 *
	 * @param location The {@link String} representing location.
	 * @return A {@link List} of {@link Quest}s in given location.
	 */
	List<Quest> findByLocation(String location);

	/**
	 * Finds all {@link Quest}s with certain {@link Monster}
	 *
	 * @param monster The {@link Monster} in a {@link Quest}.
	 * @return A {@link List} of {@link Quest}s with given {@link Monster}.
	 */
	List<Quest> findByMonster(Monster monster);

	/**
	 * Finds all {@link Quest}s with certain {@link Hero} assigned.
	 *
	 * @param hero The {@link Hero} on a {@link Quest}.
	 * @return A {@link List} of {@link Quest}s with given {@link Hero} assigned.
	 */
	List<Quest> findByAssignedHero(Hero hero);
}
