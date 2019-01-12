package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.facade.MonsterFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.web.security.AuthFacade;
import cz.muni.fi.pa165.web.security.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author Vojtech Krajnansky
 */
@Controller
@RequestMapping("/monster")
public class MonsterController {

    @Inject
    private MonsterFacade monsterFacade;

    @Inject
    private AuthFacade authFacade;

    @Inject
    private UserFacade userFacade;

    @RequestMapping("/")
    public String defaultPage() {
        return "redirect:/monster/all";
    }

    @RequestMapping("/all")
    public String list(Model model) {
        model.addAttribute("monsters", monsterFacade.findAll());
        return "monster/all";
    }

    @RequestMapping("/view/{id}")
    public String detail(Model model, @PathVariable long id) {
        final MonsterDto monster = monsterFacade.findById(id);
        model.addAttribute("monster", monster);
        model.addAttribute("size", monster.getSize());
        model.addAttribute("strengths", monster.getStrengths());
        model.addAttribute("weaknesses", monster.getWeaknesses());
        model.addAttribute("canEdit", canEdit(monster));
        return "monster/view";
    }

    @RequestMapping("/new")
    public String create(Model model) {
        if (authFacade.hasRole(Role.ADMIN)) {
            model.addAttribute("monster", new MonsterDto());
            return "monster/new";
        } else {
            model.addAttribute("message", "You need to log in, to add monsters.");
            return "error/403";
        }
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        MonsterDto monster = monsterFacade.findById(id);
        if (canEdit(monster)) {
            model.addAttribute("monster", monster);
            return "monster/edit";
        } else {
            model.addAttribute("message", "You need to log in, to edit monsters.");
            return "error/403";
        }
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@ModelAttribute("monster") MonsterDto monster, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "monster/edit";
        }

        monsterFacade.save(monster);
        return "redirect:/monster/all";
    }

    private boolean canEdit(MonsterDto monster) {
        return authFacade.isAuthenticated()
                && authFacade.hasRole(Role.ADMIN);
    }
}
