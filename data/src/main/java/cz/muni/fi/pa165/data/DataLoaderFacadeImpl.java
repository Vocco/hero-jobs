package cz.muni.fi.pa165.data;

import cz.muni.fi.pa165.heroes.entity.*;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataLoaderFacadeImpl implements DataLoaderFacade {

    private HeroService heroService;
    private MonsterService monsterService;
    private QuestService questService;
    private SkillService skillService;
    private UserService userService;


    @Inject
    public DataLoaderFacadeImpl(HeroService heroService, MonsterService monsterService, QuestService questService, SkillService skillService, UserService userService) {
        this.heroService = heroService;
        this.monsterService = monsterService;
        this.questService = questService;
        this.skillService = skillService;
        this.userService = userService;
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
            chickenSkills.add(featherTickle);
            thorSkills.add(lightningZap);

            Hero heavy = getHeavy(heavySkills);
            Hero chicken = getChicken(chickenSkills);
            Hero thor = getThor(thorSkills);

            Monster zombie = getZombie();
            Monster headlessChicken = getHeadlessChicken();

            Quest catacombs = getCatacombs(heavy, zombie);
            Quest farmersHell = getFarmersHell(headlessChicken);

            User user = getUser(thor);
            User admin = getAdmin();
        } catch (EntityValidationException | EntityNotFoundException e) {

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

    private Hero getChicken(List<Skill> skills) throws EntityValidationException, EntityNotFoundException {
        Skill featherTickle = skillService.findByName("Feather Tickle").get(0);
        List<Skill> chskill = new ArrayList<>();
        chskill.add(featherTickle);

        Hero chicken = new Hero("Chicken", 2, 1, 0, 1, 4, 0, chskill);

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

    private User getUser(Hero hero) throws EntityValidationException {
        User user = new User();
        user.setUsername("Thor");
        user.setEmail("thor@valhalla.example.com");
        user.setPasswordHash("7d445240c97cb8b39b22030981d77679608f91c7a4000e41a1794cde953a1846"); // thor
        user.setManagedHero(hero);

        userService.save(user);
        return user;
    }

    private User getAdmin() throws EntityValidationException {
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPasswordHash("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918"); //admin
        admin.setManagedHero(null);

        userService.save(admin);
        return admin;
    }
}