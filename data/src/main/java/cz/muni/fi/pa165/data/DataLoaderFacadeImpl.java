package cz.muni.fi.pa165.data;

import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.HeroService;
import cz.muni.fi.pa165.service.interfaces.MonsterService;
import cz.muni.fi.pa165.service.interfaces.QuestService;
import cz.muni.fi.pa165.service.interfaces.SkillService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Component
@Transactional
public class DataLoaderFacadeImpl implements DataLoaderFacade {

    private HeroService heroService;
    private MonsterService monsterService;
    private QuestService questService;
    private SkillService skillService;


    @Inject
    public DataLoaderFacadeImpl(HeroService heroService, MonsterService monsterService, QuestService questService, SkillService skillService) {
        this.heroService = heroService;
        this.monsterService = monsterService;
        this.questService = questService;
        this.skillService = skillService;
    }


    @Override
    public void initData() {
        try {
            Skill fireBlast = getFireBlast();
            Skill freeze = getFreeze();
            Skill featherTickle = getFeatherTickle();
            Skill lightningZap = getLightningZap();

            List<Skill> heavySkills = new ArrayList<>();
            List<Skill> chickenSkills = new ArrayList<>();
            List<Skill> thorSkills = new ArrayList<>();

            heavySkills.add(fireBlast);
            heavySkills.add(featherTickle);
            chickenSkills.add(featherTickle);
            thorSkills.add(lightningZap);

            Hero heavy = getHeavy(heavySkills);
            Hero chicken = getChicken(chickenSkills);
            Hero thor = getThor(thorSkills);

            Monster zombie = getZombie();
            Monster headlessChicken = getHeadlessChicken();

            Quest catacombs = getCatacombs(heavy, zombie);
            Quest farmersHell = getFarmersHell(headlessChicken);
        } catch (EntityValidationException e) {

        }
    }

    private Skill getFireBlast() throws EntityValidationException {
        List<Affinity> affinities = new ArrayList<>();
        affinities.add(new Affinity("fire", 2));
        affinities.add(new Affinity("explosion", 5));
        Skill fb = new Skill("Fire Blast", affinities, 50);

        skillService.save(fb);
        return fb;
    }

    private Skill getFreeze() throws EntityValidationException {
        List<Affinity> affinities = new ArrayList<>();
        affinities.add(new Affinity("frost", 40));
        affinities.add(new Affinity("slow", 5));
        Skill freeze = new Skill("Freeze", affinities, 3);

        skillService.save(freeze);
        return freeze;
    }

    private Skill getFeatherTickle() throws EntityValidationException {
        Skill tickle = new Skill("Feather Tickle", new ArrayList<>(), 1);

        skillService.save(tickle);
        return tickle;
    }

    private Skill getLightningZap() throws EntityValidationException {
        List<Affinity> affinities = new ArrayList<>();
        affinities.add(new Affinity("electricity", 50));
        Skill zap = new Skill("Lightning Zap", affinities, 10);

        skillService.save(zap);
        return zap;
    }

    private Hero getHeavy(List<Skill> skills) throws EntityValidationException {
        Hero heavy = new Hero("Heavy", 100, 30, 1, 3, 1, 0, skills);

        heroService.save(heavy);
        return heavy;
    }

    private Hero getChicken(List<Skill> skills) throws EntityValidationException {
        Hero chicken = new Hero("Chicken", 2, 1, 0, 1, 4, 0, skills);

        heroService.save(chicken);
        return chicken;
    }

    private Hero getThor(List<Skill> skills) throws EntityValidationException {
        Hero thor = new Hero("Thor", 50, 42, 10000, 5, 3, 5, skills);

        heroService.save(thor);
        return thor;
    }

    private Monster getZombie() throws EntityValidationException {
        List<Affinity> strengths = new ArrayList<>();
        List<Affinity> weaknesses = new ArrayList<>();

        strengths.add(new Affinity("electricity", 3));
        weaknesses.add(new Affinity("fire", 5));
        weaknesses.add(new Affinity("explosion", 50));

        Monster zombie = new Monster("Zombie", 16, 5,
                   "medium", strengths, weaknesses);

        monsterService.save(zombie);
        return zombie;
    }

    private Monster getHeadlessChicken() throws EntityValidationException {
        List<Affinity> strengths = new ArrayList<>();
        List<Affinity> weaknesses = new ArrayList<>();

        weaknesses.add(new Affinity("electricity", 10));
        weaknesses.add(new Affinity("fire", 15));
        weaknesses.add(new Affinity("explosion", 49));
        strengths.add(new Affinity("slow", 4));

        Monster headlessChicken = new Monster("Headless Chicken", 4, 3,
                   "small", strengths, weaknesses);

        monsterService.save(headlessChicken);
        return headlessChicken;
    }

    private Quest getCatacombs(Hero hero, Monster zombie) throws EntityValidationException {
        Quest cata = new Quest("Dark Catacombs", "The Sewers", 10000, 2);

        cata.addHero(hero);
        cata.addMonster(zombie);
        cata.addMonster(zombie);
        cata.addMonster(zombie);
        cata.addMonster(zombie);
        cata.addMonster(zombie);

        questService.save(cata);
        return cata;
    }

    private Quest getFarmersHell(Monster chicken) throws EntityValidationException {
        Quest hell = new Quest("Farmer's Hell", "McDonald's Farm", 60, 1);

        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);
        hell.addMonster(chicken);

        questService.save(hell);
        return hell;
    }
}