package cz.muni.fi.pa165.service.interfaces;

import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkillService {

	Skill findById(Long id);
	void create(Skill skill);
	void remove(Skill skill);
	List<Skill> findByName(String name);
	List<Skill> findWithAffinity(Affinity affinity);
	List<Skill> findWithBaseDamage(int baseDamage);
	List<Skill> findWithGreaterBaseDamage(int minBaseDamage);
}