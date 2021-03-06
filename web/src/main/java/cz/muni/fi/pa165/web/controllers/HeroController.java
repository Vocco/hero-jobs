package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.facade.HeroFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
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
 * @author Michal Pavuk
 */
@Controller
@RequestMapping("/hero")
public class HeroController {

    @Inject
    private HeroFacade heroFacade;

    @Inject
    private AuthFacade authFacade;

    @Inject
    private UserFacade userFacade;

    @RequestMapping("/")
    public String defaultPage() {
        return "redirect:/hero/all";
    }

    @RequestMapping("/all")
    public String list(Model model) {
        model.addAttribute("heroes", heroFacade.findAll());
        return "hero/all";
    }

    @RequestMapping("/view/{id}")
    public String detail(Model model, @PathVariable long id) {
        final HeroDto hero = heroFacade.findById(id);
        model.addAttribute("hero", hero);
        model.addAttribute("quest", hero.getQuest());
        model.addAttribute("skills", hero.getSkills());
        model.addAttribute("canEdit", canEdit(hero));
        return "hero/view";
    }

    @RequestMapping("/new")
    public String create(Model model) {
        if (authFacade.hasRole(Role.ADMIN)) {
            model.addAttribute("hero", new HeroDto());
            return "hero/edit";
        } else {
            model.addAttribute("message", "You need to log in, to add heroes.");
            return "error/403";
        }
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        HeroDto hero = heroFacade.findById(id);
        if (canEdit(hero)) {
            model.addAttribute("hero", hero);
            return "hero/edit";
        } else {
            model.addAttribute("message", "You don't have permissions to edit this hero.");
            return "error/403";
        }
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@ModelAttribute("hero") HeroDto hero, BindingResult bindingResult) {

        // TODO Validation

        if (bindingResult.hasErrors()) {
            return "hero/edit";
        }

        heroFacade.update(hero);
        return "redirect:/hero/all";
    }

    private void validate(HeroDto hero) throws EntityValidationException {
        if (hero == null
                || hero.getName() == null) {
            throw new EntityValidationException("Hero is not in valid state.");
        }
    }

    private boolean canEdit(HeroDto hero) {
        if (authFacade.getUsername().equals("anonymousUser")) return false;

        HeroDto allowedHero = userFacade.findByName(authFacade.getUsername()).getManagedHero();

        return authFacade.hasRole(Role.ADMIN) || allowedHero.equals(hero);
    }
}
