package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.facade.QuestFacade;
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
 * @author Metodej Klang
 */

@Controller
public class QuestController {

	@Inject
	private QuestFacade questFacade;

	@Inject
	private AuthFacade authFacade;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String allQuests(Model model) {
		model.addAttribute("quests", questFacade.findAll());
		return "quest/all";
	}

	@RequestMapping("/view/{id}")
	public String detail(Model model, @PathVariable long id) {
		final QuestDto quest = questFacade.findById(id);
		model.addAttribute("quest", quest);
		model.addAttribute("editingEnabled", authFacade.hasRole(Role.ADMIN));
		return "quest/view";
	}

	@RequestMapping("/new")
	public String create(Model model) {
		if (authFacade.hasRole(Role.ADMIN)) {
			model.addAttribute("hero", new QuestDto());
			return "quest/new";
		} else {
			model.addAttribute("message", "You need to log in, to add quests.");
			return "error/403";
		}
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable long id) {
		QuestDto hero = questFacade.findById(id);
		if (authFacade.hasRole(Role.ADMIN)) {
			model.addAttribute("hero", hero);
			return "quest/edit";
		} else {
			model.addAttribute("message", "You need to log in, to edit quests.");
			return "error/403";
		}
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(@ModelAttribute("quest") QuestDto questDto, BindingResult bindingResult) {

		try {
			validate(questDto);
		} catch (EntityValidationException e) {
			return "quest/edit";
		}

		if (bindingResult.hasErrors()) {
			return "quest/edit";
		}

		questFacade.save(questDto);
		return "redirect:/quest/all";
	}

	private void validate(QuestDto quest) throws EntityValidationException {
		if (quest == null
				|| quest.getName() == null
				|| quest.getLocation() == null
				|| quest.getReward() <= 0
				|| quest.getHeroLimit() <= 0) {
			throw new EntityValidationException("Quest is not in valid state.");
		}
	}
}
