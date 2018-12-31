package cz.muni.fi.pa165.service.interfaces;

import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.User;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;

import java.util.List;

public interface UserService {
    User findById(Long id) throws EntityNotFoundException;

    List<User> findAll();

    boolean save(User user) throws EntityValidationException;

    User update(User user) throws EntityValidationException;

    boolean delete(User user);

    boolean deleteById(Long id);

    User findByName(String name) throws EntityNotFoundException;

    User findByEmail(String email) throws EntityNotFoundException;

    List<User> findByHero(Hero hero) throws EntityNotFoundException;

    User authenticate(String username, String passwordHash) throws EntityNotFoundException;
}
