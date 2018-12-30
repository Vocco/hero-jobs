package cz.muni.fi.pa165.heroes.dao;

import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.User;

import java.util.List;

/**
 * @author Michal Pavuk
 */
public interface UserDAO extends DAO<User> {
    /**
     * @param name a username of the user
     * @return the User with a given username
     */
    User findByName(String name);

    /**
     * @param email an email of the user
     * @return the User with a given email
     */
    User findByEmail(String email);

    /**
     * @param hero a hero managed by the user
     * @return list of users which manage the given hero
     */
    List<User> findByHero(Hero hero);

}
