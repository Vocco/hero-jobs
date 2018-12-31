package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.facade.MonsterFacade;

import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.rest.exceptions.ConflictException;
import cz.muni.fi.pa165.rest.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUris.ROOT_MONSTERS)
public class MonstersController {


    @Autowired
    private MonsterFacade monsters;


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MonsterDto> getAllMonsters() {
        return monsters.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final MonsterDto getMonsterById(@PathVariable long id) throws NotFoundException {

        MonsterDto monster = monsters.findById(id);

        if (monster == null) {
            throw new NotFoundException();
        }

        return monster;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final boolean deleteMonster(@PathVariable Long id) throws NotFoundException {
        boolean success = monsters.deleteById(id);

        if (!success) {
            throw new NotFoundException();
        }

        return success;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean createMonster(@RequestBody MonsterDto monster) throws ConflictException {
        boolean success = monsters.save(monster);

        if (!success) {
            throw new ConflictException();
        }

        return success;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MonsterDto updateMonster(@RequestBody MonsterDto monster) throws NotFoundException {
        try {
            return monsters.update(monster);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException();
        }
    }
}