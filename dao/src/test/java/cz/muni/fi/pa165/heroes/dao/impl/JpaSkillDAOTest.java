package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.HeroDAO;
import cz.muni.fi.pa165.heroes.dao.SkillDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for Skill JPA DAO implementation.
 *
 * @author Metodej Klang
 */

@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JpaSkillDAOTest {

	@Autowired
	private SkillDAO skillDAO;

	private Affinity fire1;
	private Affinity pierce2;
	private Affinity blunt2;
	private Affinity poison3;

	private Skill meteorShower;
	private Skill poisonSpear;
	private Skill poisonSpear2;
	private Skill fireArrow;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		fire1 = new Affinity("Fire", 1);
		pierce2 = new Affinity("Pierce", 2);
		blunt2 = new Affinity("Blunt", 2);
		poison3 = new Affinity("Poison", 3);

		em.persist(fire1);
		em.persist(pierce2);
		em.persist(blunt2);
		em.persist(poison3);

		meteorShower = new Skill("Meteor Shower", Arrays.asList(fire1, blunt2), 5);
		poisonSpear = new Skill("Poison Spear", Arrays.asList(pierce2, poison3), 3);
		poisonSpear2 = new Skill("Poison Spear", Arrays.asList(pierce2, poison3), 30);
		fireArrow = new Skill("Fire Arrow", Arrays.asList(fire1, pierce2), 2);

		em.persist(meteorShower);
		em.persist(poisonSpear);
		em.persist(poisonSpear2);
		em.persist(fireArrow);

		em.flush();
		em.getTransaction().commit();

		em.close();
	}

	/**
	 * Test for the findByName method.
	 */
	@Test
	public void TestFindByName() {
		List<Skill> skills = skillDAO.findByName("Poison Spear");

		assertEquals(2, skills.size());

		assertEquals("Poison Spear", skills.get(0).getName());
		assertEquals("Poison Spear", skills.get(1).getName());
	}

	/**
	 * Test for the findWithAffinity method.
	 */
	@Test
	public void testFindWithAffinity() {
		List<Skill> skills = skillDAO.findWithAffinity(fire1);

		assertEquals(2, skills.size());

		List<String> names = new ArrayList<>();

		for (Skill s : skills) {
			names.add(s.getName());
		}

		assertTrue(names.contains("Meteor Shower"));
		assertTrue(names.contains("Fire Arrow"));
	}

	/**
	 * Test for the findWithBaseDamage method.
	 */
	@Test
	public void testFindWithBaseDamage() {
		List<Skill> skills = skillDAO.findWithBaseDamage(5);

		assertEquals(1, skills.size());

		assertEquals("Meteor Shower", skills.get(0).getName());
	}

	/**
	 * Test for the findWithGreaterBaseDamage method.
	 */
	@Test
	public void testFindWithGreaterBaseDamage() {
		List<Skill> skills = skillDAO.findWithGreaterBaseDamage(5);

		assertEquals(2, skills.size());

		List<String> names = new ArrayList<>();

		for (Skill s : skills) {
			names.add(s.getName());
		}

		assertTrue(names.contains("Meteor Shower"));
		assertTrue(names.contains("Poison Spear"));
	}

	/**
	 * Test for the findAll method.
	 */
	@Test
	public void testFindAll() {
		List<Skill> skills = skillDAO.findAll();

		assertEquals(4, skills.size());

		List<Integer> dmgs = new ArrayList<>();

		for (Skill s : skills) {
			dmgs.add(s.getBaseDamage());
		}

		assertTrue(dmgs.contains(5));
		assertTrue(dmgs.contains(30));
		assertTrue(dmgs.contains(3));
		assertTrue(dmgs.contains(2));
	}

	/**
	 * Test for the findById method.
	 */
	@Test
	public void testFindById() {
		Skill mS = skillDAO.findById(meteorShower.getId());
		Skill pS = skillDAO.findById(poisonSpear.getId());
		Skill pS2 = skillDAO.findById(poisonSpear2.getId());
		Skill fA = skillDAO.findById(fireArrow.getId());
		Skill nonexistent = (Skill) skillDAO.findById(new Long("-1"));

		assertEquals(mS.getName(), meteorShower.getName());
		assertEquals(pS.getName(), poisonSpear.getName());
		assertEquals(pS2.getName(), poisonSpear2.getName());
		assertEquals(fA.getName(), fireArrow.getName());
		assertNull(nonexistent);
	}

	/**
	 * Test for the update method.
	 */
	@Test
	public void testUpdate() {
		poisonSpear2.setName("Better Poison Spear");

		skillDAO.update(poisonSpear2);

		Skill newPoisonSpear = skillDAO.findById(poisonSpear2.getId());
		assertEquals("Better Poison Spear", newPoisonSpear.getName());
	}

	/**
	 * Test for the save method.
	 */
	@Test
	public void testSave() {
		Skill newSkill = new Skill("New Skill", new ArrayList<Affinity>(), 8);

		skillDAO.save(newSkill);

		assertEquals(5, skillDAO.findAll().size());
		Skill skill = skillDAO.findById(newSkill.getId());
		assertEquals("New Skill", skill.getName());
	}

	/**
	 * Test for the delete method.
	 */
	@Test
	public void testDelete() {
		assertTrue(skillDAO.delete(poisonSpear2));
		assertEquals(3, skillDAO.findAll().size());
		assertNull(skillDAO.findById(poisonSpear2.getId()));
	}

	/**
	 * Test for the deleteById method.
	 */
	@Test
	public void testDeleteById() {
		assertTrue(skillDAO.deleteById(poisonSpear2.getId()));
		assertEquals(3, skillDAO.findAll().size());
		assertNull(skillDAO.findById(poisonSpear2.getId()));
	}
}
