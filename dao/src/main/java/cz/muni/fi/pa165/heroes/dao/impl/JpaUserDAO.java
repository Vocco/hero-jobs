package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.UserDAO;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Michal Pavuk
 */
@Repository
public class JpaUserDAO extends JpaDAO<User> implements UserDAO {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findByName(String name) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :name"
                , User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public User findByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email"
                , User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public List<User> findByHero(Hero hero) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.managedHero = :hero"
                , User.class)
                .setParameter("hero", hero)
                .getResultList();
    }
}
