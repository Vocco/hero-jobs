package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.SkillDto;
import cz.muni.fi.pa165.facade.SkillFacade;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.interfaces.SkillService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Metodej Klang
 */
public class SkillFacadeImpl implements SkillFacade {

	@Inject
	private SkillService skillService;

	@Inject
	private BeanMappingService beanMappingService;

	@Override
	public List<SkillDto> findAll() {
		return beanMappingService.mapTo(skillService.findAll(), SkillDto.class);
	}

	@Override
	public SkillDto findById(Long id) {
		try {
			Skill skill = skillService.findById(id);
			return beanMappingService.mapTo(skill, SkillDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SkillDto update(SkillDto dto) {
		try {
			Skill skill = skillService.update(beanMappingService.mapTo(dto, Skill.class));
			return beanMappingService.mapTo(skill, SkillDto.class);
		} catch (EntityValidationException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean save(SkillDto dto) {
		try {
			return skillService.save(beanMappingService.mapTo(dto, Skill.class));
		} catch (EntityValidationException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(SkillDto dto) {
		return skillService.delete(beanMappingService.mapTo(dto, Skill.class));
	}

	@Override
	public boolean deleteById(Long id) {
		return skillService.deleteById(id);
	}

	@Override
	public List<SkillDto> findByName(String name) {
		try {
			return beanMappingService.mapTo(skillService.findByName(name), SkillDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SkillDto> findWithAffinity(AffinityDto affinity) {
		try {
			return beanMappingService.mapTo(skillService.findWithAffinity(beanMappingService.mapTo(affinity, Affinity.class)), SkillDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SkillDto> findWithBaseDamage(int baseDamage) {
		try {
			return beanMappingService.mapTo(skillService.findWithBaseDamage(baseDamage), SkillDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SkillDto> findWithGreaterBaseDamage(int minBaseDamage) {
		try {
			return beanMappingService.mapTo(skillService.findWithGreaterBaseDamage(minBaseDamage), SkillDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
