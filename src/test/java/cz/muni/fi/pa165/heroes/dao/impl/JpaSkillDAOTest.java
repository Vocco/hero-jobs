package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.HeroDAO;
import cz.muni.fi.pa165.heroes.dao.SkillDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class JpaSkillDAOTest {

	private ApplicationContext context = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");

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
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		List<Skill> skills = skillDAO.findByName("Poison Spear");

		assertTrue(skills.size() == 2);

		assertTrue(skills.get(0).getName().equals("Poison Spear"));
		assertTrue(skills.get(1).getName().equals("Poison Spear"));
	}

	/**
	 * Test for the findWithAffinity method.
	 */
	@Test
	public void testFindWithAffinity() {
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		List<Skill> skills = skillDAO.findWithAffinity(fire1);

		assertTrue(skills.size() == 2);

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
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		List<Skill> skills = skillDAO.findWithBaseDamage(5);

		assertTrue(skills.size() == 1);

		assertTrue(skills.get(0).getName().equals("Meteor Shower"));
	}

	/**
	 * Test for the findWithGreaterBaseDamage method.
	 */
	@Test
	public void testFindWithGreaterBaseDamage() {
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		List<Skill> skills = skillDAO.findWithGreaterBaseDamage(5);

		assertTrue(skills.size() == 2);

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
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		List<Skill> skills = skillDAO.findAll();

		assertTrue(skills.size() == 4);

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
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		Skill mS = skillDAO.findById(meteorShower.getId());
		Skill pS = skillDAO.findById(poisonSpear.getId());
		Skill pS2 = skillDAO.findById(poisonSpear2.getId());
		Skill fA = skillDAO.findById(fireArrow.getId());
		Skill nonexistent = (Skill) skillDAO.findById(new Long("-1"));

		assertTrue(mS.getName().equals(meteorShower.getName()));
		assertTrue(pS.getName().equals(poisonSpear.getName()));
		assertTrue(pS2.getName().equals(poisonSpear2.getName()));
		assertTrue(fA.getName().equals(fireArrow.getName()));
		assertTrue(nonexistent == null);
	}

	/**
	 * Test for the update method.
	 */
	@Test
	public void testUpdate() {
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		poisonSpear2.setName("Better Poison Spear");

		skillDAO.update(poisonSpear2);

		Skill newPoisonSpear = skillDAO.findById(poisonSpear2.getId());
		assertTrue(newPoisonSpear.getName().equals("Better Poison Spear"));
	}

	/**
	 * Test for the save method.
	 */
	@Test
	public void testSave() {
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		Skill newSkill = new Skill("New Skill", Arrays.asList(pierce2), 8);

		skillDAO.save(newSkill);

		assertTrue(skillDAO.findAll().size() == 5);
		Skill skill = skillDAO.findById(newSkill.getId());
		assertTrue(skill.getName().equals("New Skill"));
	}

	/**
	 * Test for the delete method.
	 */
	@Test
	public void testDelete() {
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		assertTrue(skillDAO.delete(poisonSpear2));
		assertTrue(skillDAO.findAll().size() == 3);
		assertTrue(skillDAO.findById(poisonSpear2.getId()) == null);
	}

	/**
	 * Test for the deleteById method.
	 */
	@Test
	public void testDeleteById() {
		SkillDAO skillDAO = context.getBean("jpaSkillDAO", JpaSkillDAO.class);
		assertTrue(skillDAO.deleteById(poisonSpear2.getId()));
		assertTrue(skillDAO.findAll().size() == 3);
		assertTrue(skillDAO.findById(poisonSpear2.getId()) == null);
	}
}
