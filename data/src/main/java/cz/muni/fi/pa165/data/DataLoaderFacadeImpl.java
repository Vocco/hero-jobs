package cz.muni.fi.pa165.data;

import cz.muni.fi.pa165.heroes.entity.*;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
@Transactional
public class DataLoaderFacadeImpl implements DataLoaderFacade {

    private HeroService heroService;
    private MonsterService monsterService;
    private QuestService questService;
    private SkillService skillService;
    private UserService userService;

    private Random random = new Random(1338);

    @Inject
    public DataLoaderFacadeImpl(HeroService heroService, MonsterService monsterService, QuestService questService, SkillService skillService, UserService userService) {
        this.heroService = heroService;
        this.monsterService = monsterService;
        this.questService = questService;
        this.skillService = skillService;
        this.userService = userService;
    }

    private int randomRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    private List<String> affinityNames = Arrays.asList("Earth", "Water", "Fire", "Air", "Light", "Dark");

    private String getDigest(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        return String.format("%064x", new BigInteger(1, digest));
    }

    private void generateHeroes(int number) throws EntityValidationException {
        List<String> usedNames = new ArrayList<>();
        List<String> adjectives = Arrays.asList("Natural", "Liquid", "Fiery", "Etheral", "Divine", "Unholy");
        List<String> nouns = Arrays.asList("Vine", "Tide", "Flame", "Wind", "Aura", "Curse");
        List<String> firstNames = Arrays.asList(
                "Tristan", "Arthur", "Edmund", "Percival", "Ronan", "Thor", "Leon", "Roman", "Adam", "Ferris", "Zephyr", "Gawain", "Perseus", "Cormac", "Odin", "Faramond", "Beowulf",
                "Aemilia", "Ophelia", "Cordelia", "Portia", "Nerissa", "Bianca", "Rowena", "Circe", "Elysia", "Briar", "Ione", "Amaryllis", "Astoria", "Andromeda", "Cressida", "Atalanta", "Zephyrine", "Vasilisa", "Vesper"
        );
        List<String> lastNames = Arrays.asList(
                "Quintus", "Epicurus", "Meriadoc", "Reliquary", "Galahad", "Zoroaster", "Xanthias", "Eindride", "Leodegrance", "Ophion", "Charlemagne", "Lovejoy", "Gwydion", "Romulus", "Kazimir", "Loxias", "Hafgan",
                "Ronchelli", "Dunteau", "Choimbert", "Bonnellac", "Estiezac", "Rassier", "Cardainie", "Estiechade", "Guignon", "Gaiveron"
        );

        for (int i = 0; i < number; i++) {
            int a = randomRange(0, affinityNames.size());
            int b = randomRange(0, affinityNames.size());
            int c = randomRange(0, affinityNames.size());
            int d = randomRange(0, affinityNames.size());
            if (a == b) {
                if (a == 0) {
                    b = randomRange(1, affinityNames.size());
                } else if (a == affinityNames.size() - 1) {
                    b = randomRange(0, affinityNames.size() - 2);
                }
            }
            if (c == d) {
                if (c == 0) {
                    d = randomRange(1, affinityNames.size());
                } else if (c == affinityNames.size() - 1) {
                    d = randomRange(0, affinityNames.size() - 2);
                }
            }

            Affinity a1 = new Affinity(affinityNames.get(a), randomRange(1, 5));
            Affinity a2 = new Affinity(affinityNames.get(b), randomRange(1, 5));
            Affinity a3 = new Affinity(affinityNames.get(c), randomRange(1, 5));
            Affinity a4 = new Affinity(affinityNames.get(d), randomRange(1, 5));

            Skill s1 = new Skill(adjectives.get(a) + " " + nouns.get(b), Arrays.asList(a1, a2), (a1.getLevel() + a2.getLevel()) * 10);
            Skill s2 = new Skill(adjectives.get(c) + " " + nouns.get(d), Arrays.asList(a3, a4), (a3.getLevel() + a4.getLevel()) * 10);
            em.persist(s1);
            em.persist(s2);

            String name;
            do {
                a = randomRange(0, firstNames.size());
                b = randomRange(0, lastNames.size());
                if (a == b) {
                    if (a == 0) {
                        b = randomRange(1, lastNames.size());
                    } else if (a == firstNames.size()) {
                        b = randomRange(0, lastNames.size() - 1);
                    }
                }

                name = firstNames.get(a) + " " + lastNames.get(b);
            } while (usedNames.indexOf(name) != -1);
            usedNames.add(name);

            List<Skill> selected = Arrays.asList(s1, s2);
            Hero hero = new Hero(name, randomRange(30, 150), randomRange(10, 60), randomRange(100, 3000), randomRange(1, 10), randomRange(1, 10), randomRange(1, 10), selected);
            hero.setAlive(random.nextBoolean());
            em.persist(hero);

            User user = new User();
            String username = name.replace(" ", ".").toLowerCase();
            user.setUsername(username);
            user.setEmail(username + "@heroes.example.com");
            user.setPasswordHash(getDigest(username));
            user.setManagedHero(hero);
            em.persist(user);

            if (i % 8 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    private void generateQuests(int number) throws EntityValidationException {
        List<String> locationAdjectives = Arrays.asList("Holy", "Infernal", "Icy", "Sunny", "The Great", "Rocky", "Misty", "Forbidden");
        List<String> locationNouns = Arrays.asList("Plains", "Forest", "Swamp", "Jungle", "Taiga", "Mountain", "Village", "Desert", "Temple");

        List<String> monsterAdjectives = Arrays.asList("Crazy", "Confused", "Mysterious", "Dark", "Arcane", "Fast", "Lesser", "Magical", "Exiled", "Restless");
        List<String> monsterNouns = Arrays.asList("Rat", "Skeleton", "Demon", "Wraith", "Assassin", "Maniac", "Necromancer", "Ogre", "Zombie", "Spirit");
        List<String> sizes = Arrays.asList("tiny", "small", "medium", "big", "large", "huge", "gigantic");


        for (int i = 0; i < number; i++) {
            int a = randomRange(0, affinityNames.size());
            int b = randomRange(0, affinityNames.size());
            int c = randomRange(0, affinityNames.size());
            int d = randomRange(0, affinityNames.size());
            if (a == b) {
                if (a == 0) {
                    b = randomRange(1, affinityNames.size());
                } else if (a == affinityNames.size() - 1) {
                    b = randomRange(0, affinityNames.size() - 2);
                }
            }
            if (c == d) {
                if (c == 0) {
                    d = randomRange(1, affinityNames.size());
                } else if (c == affinityNames.size() - 1) {
                    d = randomRange(0, affinityNames.size() - 2);
                }
            }

            Affinity a1 = new Affinity(affinityNames.get(a), randomRange(1, 5));
            Affinity a2 = new Affinity(affinityNames.get(b), randomRange(1, 5));
            Affinity a3 = new Affinity(affinityNames.get(c), randomRange(1, 5));
            Affinity a4 = new Affinity(affinityNames.get(d), randomRange(1, 5));

            Collections.shuffle(monsterAdjectives);
            Collections.shuffle(monsterNouns);
            Collections.shuffle(sizes);
            String name = monsterAdjectives.get(0) + " " + monsterNouns.get(0);
            Monster monster = new Monster(name, randomRange(30, 300), randomRange(5, 90), sizes.get(0), Arrays.asList(a1, a2), Arrays.asList(a3, a4));
            em.persist(monster);

            a = randomRange(0, locationNouns.size());
            b = randomRange(0, locationAdjectives.size());
            String location = locationAdjectives.get(b) + " " + locationNouns.get(a);

            String questName;
            if (random.nextBoolean()) {
                questName = ((random.nextBoolean()) ? monsterNouns.get(0) + "'s" : monsterAdjectives.get(0)) + " " + locationNouns.get(a);
            } else {
                questName = locationAdjectives.get(b) + " " + monsterNouns.get(0) + "s";
            }
            Quest quest = new Quest(questName, location, randomRange(100, 2000), randomRange(1, 5));
            quest.addMonster(monster);
            em.persist(quest);

            if (i % 8 == 0) {
                em.flush();
                em.clear();
            }
        }

    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");
    private EntityManager em = emf.createEntityManager();

    @Override
    public void initData() {
        try {
            // This is used for performance reasons to control when hibernate flushes the persistence context into db
            em.getTransaction().begin();

            generateHeroes(20);
            generateQuests(30);

            em.getTransaction().commit();
            em.close();
            emf.close();

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
            e.printStackTrace();
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