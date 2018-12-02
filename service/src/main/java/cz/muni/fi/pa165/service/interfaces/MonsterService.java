package cz.muni.fi.pa165.service.interfaces;

import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MonsterService {

	  Monster findById(Long id);
	  List<Monster> findAll();

	  void create(Monster monster);
	  void remove(Monster monster);
	  void update(Monster monster);

	  List<Monster> findWithStrength(Affinity strength);
	  List<Monster> findWithWeakness(Affinity weakness);
	  List<Monster> findWithSize(String size);
	  List<Monster> findByQuest(Quest quest);
	  List<Affinity> findAllWeaknessesOfMonstersOnQuest(Quest quest);
	  List<Affinity> findAllStrengthsOfMonstersOnQuest(Quest quest);
}