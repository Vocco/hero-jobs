package cz.muni.fi.pa165.service.interfaces;

import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkillService {

	Skill findById(Long id) throws EntityNotFoundException;
	List<Skill> findAll();

	boolean save(Skill skill) throws EntityValidationException;
	Skill update(Skill skill) throws EntityValidationException;
	boolean delete(Skill skill);
	boolean deleteById(Long id);

	List<Skill> findByName(String name) throws EntityNotFoundException;
	List<Skill> findWithAffinity(Affinity affinity) throws EntityNotFoundException;
	List<Skill> findWithBaseDamage(int baseDamage) throws EntityNotFoundException;
	List<Skill> findWithGreaterBaseDamage(int minBaseDamage) throws EntityNotFoundException;
}