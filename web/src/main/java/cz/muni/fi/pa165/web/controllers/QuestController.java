package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.QuestStateDto;
import cz.muni.fi.pa165.facade.QuestFacade;
import cz.muni.fi.pa165.heroes.entity.QuestState;
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
import java.util.Arrays;

/**
 * @author Metodej Klang
 */

@Controller
@RequestMapping("/quest")
public class QuestController {

	@Inject
	private QuestFacade questFacade;

	@Inject
	private AuthFacade authFacade;

	private boolean nameErr;
	private boolean locErr;
	private boolean rewardErr;
	private boolean perfErr;

  private QuestStateDto questStateEdited;

    @RequestMapping("/")
    public String defaultPage() {
        return "redirect:/quest/all";
    }

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String allQuests(Model model) {
		model.addAttribute("quests", questFacade.findAll());
		return "quest/all";
	}

	@RequestMapping("/view/{id}")
	public String detail(Model model, @PathVariable long id) {
		final QuestDto quest = questFacade.findById(id);
		model.addAttribute("quest", quest);
		model.addAttribute("questsize", quest.getMonsters().size());
		model.addAttribute("canEdit", canEdit(quest));
		return "quest/view";
	}

	@RequestMapping("/new")
	public String create(Model model) {
		if (authFacade.hasRole(Role.ADMIN)) {
			model.addAttribute("quest", new QuestDto());
			return "quest/new";
		} else {
			model.addAttribute("message", "You need to log in, to add quests.");
			return "error/403";
		}
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable long id) {
		QuestDto quest = questFacade.findById(id);

		questStateEdited = quest.getState();
		model.addAttribute("queststate", questStateEdited);
		model.addAttribute("statusOptions", Arrays.stream(QuestState.values()).map(Enum::name).toArray());
		if (authFacade.hasRole(Role.ADMIN)) {
			model.addAttribute("quest", quest);
			return "quest/edit";
		} else {
			model.addAttribute("message", "You need to log in, to edit quests.");
			return "error/403";
		}
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(Model model, @ModelAttribute("quest") QuestDto questDto, BindingResult bindingResult) {

    questDto.setState(questStateEdited);

		nameErr = questDto.getName() == null || questDto.getName().equals("");
		locErr = questDto.getLocation() == null || questDto.getLocation().equals("");
		rewardErr = questDto.getReward() < 1;
		perfErr = questDto.getPerformanceEvaluation() < 0 || questDto.getPerformanceEvaluation() > 5;

		model.addAttribute("nameErr", nameErr);
		model.addAttribute("locErr", locErr);
		model.addAttribute("rewardErr", rewardErr);
		model.addAttribute("perfErr", perfErr);

		if (nameErr || locErr || rewardErr || perfErr) {
		    return "quest/edit";
		}

		if (bindingResult.hasErrors()) {
			return "quest/edit";
		}

		questFacade.update(questDto);
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

	private boolean canEdit(QuestDto quest) {
        return authFacade.isAuthenticated()
                && authFacade.hasRole(Role.ADMIN);
    }
}
