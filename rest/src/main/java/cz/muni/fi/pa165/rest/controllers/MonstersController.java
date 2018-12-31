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
}