package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.UserDto;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.User;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Michal Pavuk
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public UserDto findByName(String name) {
        try {
            return beanMappingService.mapTo(userService.findByName(name), UserDto.class);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserDto findByEmail(String email) {
        try {
            return beanMappingService.mapTo(userService.findByEmail(email), UserDto.class);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserDto> findByHero(HeroDto hero) {
        try {
            return beanMappingService.mapTo(userService.findByHero(beanMappingService.mapTo(hero, Hero.class)), UserDto.class);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserDto authenticate(String username, String passwordHash) {
        try {
            return beanMappingService.mapTo(userService.authenticate(username, passwordHash), UserDto.class);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserDto> findAll() {
        return beanMappingService.mapTo(userService.findAll(), UserDto.class);
    }

    @Override
    public UserDto findById(Long id) {
        try {
            User user = userService.findById(id);
            return beanMappingService.mapTo(user, UserDto.class);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserDto update(UserDto dto) {
        try {
            User user = userService.update(beanMappingService.mapTo(dto, User.class));
            return beanMappingService.mapTo(user, UserDto.class);
        } catch (EntityValidationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(UserDto dto) {
        try {
            return userService.save(beanMappingService.mapTo(dto, User.class));
        } catch (EntityValidationException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UserDto dto) {
        return userService.delete(beanMappingService.mapTo(dto, User.class));
    }

    @Override
    public boolean deleteById(Long id) {
        return userService.deleteById(id);
    }
}
