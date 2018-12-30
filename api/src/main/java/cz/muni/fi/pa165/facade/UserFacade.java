package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.UserDto;

import java.util.List;

public interface UserFacade extends BaseFacade<UserDto> {
    /**
     * @param name a username of the user
     * @return the User with a given username
     */
    UserDto findByName(String name);

    /**
     * @param email an email of the user
     * @return the UserDto with a given email
     */
    UserDto findByEmail(String email);

    /**
     * @param hero a hero managed by the user
     * @return list of users which manage the given hero
     */
    List<UserDto> findByHero(HeroDto hero);
}
