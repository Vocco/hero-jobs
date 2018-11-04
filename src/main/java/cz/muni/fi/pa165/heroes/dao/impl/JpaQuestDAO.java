package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.QuestDAO;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;

import javax.persistence.Query;
import java.util.List;

/**
 * A {@link Quest} JPA DAO implementation.
 *
 * @author Metodej Klang
 */
public class JpaQuestDAO extends JpaDAO<Quest>  implements QuestDAO {
	@Override
	public List<Quest> findByState(QuestState state) {
		entityManager.getTransaction().begin();

		Query query = entityManager
				.createQuery("select q from Quest q where q.state = :state", Quest.class)
				.setParameter("state", state);

		List<Quest> quests = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return quests;
	}

	@Override
	public List<Quest> findByLocation(String location) {
		entityManager.getTransaction().begin();

		Query query = entityManager
				.createQuery(
						"select q from Quest q where q.location = :location", Quest.class);
		query.setParameter("location", location);

		List<Quest> quests = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return quests;
	}

	@Override
	public List<Quest> findByMonster(Monster monster) {
		entityManager.getTransaction().begin();

		Query query = entityManager
				.createQuery(
						"select q from Quest q join q.monsters m where m.id = :monsterId", Quest.class);
		query.setParameter("monsterId", monster.getId());

		List<Quest> quests = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return quests;
	}

	@Override
	public List<Quest> findByAssignedHero(Hero hero) {
		entityManager.getTransaction().begin();

		Query query = entityManager
				.createQuery(
						"select q from Quest q join q.assignedHeroes h where h.id = :heroId", Quest.class);
		query.setParameter("heroId", hero.getId());

		List<Quest> quests = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return quests;
	}
}
