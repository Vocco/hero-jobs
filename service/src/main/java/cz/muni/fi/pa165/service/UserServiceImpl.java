package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.UserDAO;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.User;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Michal Pavuk
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    UserDAO userDAO;

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        User user = userDAO.findById(id);

        if (user == null) {
            throw new EntityNotFoundException();
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public boolean save(User user) throws EntityValidationException {
        validate(user);
        return userDAO.save(user);
    }

    @Override
    public User update(User user) throws EntityValidationException {
        validate(user);
        return userDAO.update(user);
    }

    @Override
    public boolean delete(User user) {
        return userDAO.delete(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return userDAO.deleteById(id);
    }

    @Override
    public User findByName(String name) throws EntityNotFoundException {
        User user = userDAO.findByName(name);

        if (user == null) {
            throw new EntityNotFoundException("User with username: " + name + "was not found.");
        }

        return user;
    }

    @Override
    public User findByEmail(String email) throws EntityNotFoundException {
        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new EntityNotFoundException("User with email: " + email + "was not found.");
        }

        return user;
    }

    @Override
    public List<User> findByHero(Hero hero) throws EntityNotFoundException {
        List<User> users = userDAO.findByHero(hero);

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException("Hero " + hero.getName() + " is not managed by anyone.");
        }

        return users;
    }

    @Override
    public User authenticate(String username, String passwordHash) throws EntityNotFoundException {
        User user = this.findByName(username);
        if (user.getPasswordHash().equals(passwordHash)) {
            return user;
        } else return null;
    }

    private void validate(User user) throws EntityValidationException {
        if (user == null || user.getUsername() == null || user.getPasswordHash() == null) {
            throw new EntityValidationException("User is not in a valid state.");
        }
    }
}
