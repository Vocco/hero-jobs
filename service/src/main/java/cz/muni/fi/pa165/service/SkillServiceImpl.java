package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.SkillDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.SkillService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Metodej Klang
 */
@Service
public class SkillServiceImpl implements SkillService {

	@Inject
	private SkillDAO skillDAO;

	@Override
	public Skill findById(Long id) throws EntityNotFoundException {
		Skill skill = skillDAO.findById(id);

		if (skill == null) {
			throw new EntityNotFoundException("Skill with id: "+ id +" was not found.");
		}

		return skill;
	}

	@Override
	public List<Skill> findAll() {
		return skillDAO.findAll();
	}

	@Override
	public boolean save(Skill skill) throws EntityValidationException {
		validate(skill);
		return skillDAO.save(skill);
	}

	@Override
	public Skill update(Skill skill) throws EntityValidationException {
		validate(skill);
		return skillDAO.update(skill);
	}

	@Override
	public boolean delete(Skill skill) {
		return skillDAO.delete(skill);
	}

	@Override
	public boolean deleteById(Long id) {
		return skillDAO.deleteById(id);
	}

	@Override
	public List<Skill> findByName(String name) throws EntityNotFoundException {
		List<Skill> skill = skillDAO.findByName(name);

		if (skill.isEmpty()) {
			throw new EntityNotFoundException("Skills with name: " + name + " was not found.");
		}

		return skill;
	}

	@Override
	public List<Skill> findWithAffinity(Affinity affinity) throws EntityNotFoundException {
		List<Skill> skill = skillDAO.findWithAffinity(affinity);

		if (skill.isEmpty()) {
			throw new EntityNotFoundException("Skills with affinity: " + affinity + " was not found.");
		}

		return skill;
	}

	@Override
	public List<Skill> findWithBaseDamage(int baseDamage) throws EntityNotFoundException {
		List<Skill> skill = skillDAO.findWithBaseDamage(baseDamage);

		if (skill.isEmpty()) {
			throw new EntityNotFoundException("Skills with base damage: " + baseDamage + " was not found.");
		}

		return skill;
	}

	@Override
	public List<Skill> findWithGreaterBaseDamage(int minBaseDamage) throws EntityNotFoundException {
		List<Skill> skill = skillDAO.findWithGreaterBaseDamage(minBaseDamage);

		if (skill.isEmpty()) {
			throw new EntityNotFoundException("Skills with higher damage than: " + minBaseDamage + " was not found.");
		}

		return skill;
	}

	private void validate(Skill skill) throws EntityValidationException {
		if (skill == null || skill.getName() == null || skill.getAffinities() == null) {
			throw new EntityValidationException("Skill is not in a valid state.");
		}
	}

}
