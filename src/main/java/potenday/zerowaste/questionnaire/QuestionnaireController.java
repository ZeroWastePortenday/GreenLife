package potenday.zerowaste.questionnaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/questionnaires")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/{id}")
    public String getQuestionnaireDetails(@PathVariable("id") Long id, Model model) {
        Optional<Questionnaire> questionnaire = questionnaireService.getQuestionnaireById(id);
        questionnaire.ifPresent(q -> model.addAttribute("questionnaire", q));
        return "questionnaire/questionnaire-details";
    }

    @GetMapping("/list")
    public String getQuestionnaireList(Model model) {
        List<Questionnaire> questionnaireList = questionnaireService.getAllQuestionnaires();
        model.addAttribute("questionnaireList", questionnaireList);
        System.out.println("hihi" + questionnaireList.toString());
        return "questionnaire/questionnaire-list";
    }

    @GetMapping("/create")
    public String showCreateQuestionnaireForm(Model model) {
        model.addAttribute("questionnaire", new Questionnaire());
        return "questionnaire/questionnaire-form";
    }

    @PostMapping("/create")
    public String createQuestionnaire(@ModelAttribute("questionnaire") Questionnaire questionnaire) {
        Questionnaire createdQuestionnaire = questionnaireService.createQuestionnaire(questionnaire);
        // Redirect to the questionnaire list page
        return "redirect:/questionnaires/list";
    }

    @GetMapping("/edit/{id}")
    public String editQuestionnaire(@PathVariable("id") Long id, Model model) {
        Optional<Questionnaire> questionnaire = questionnaireService.getQuestionnaireById(id);
        if (questionnaire.isPresent()) {
            model.addAttribute("questionnaire", questionnaire.get());
            return "questionnaire/questionnaire-edit";
        } else {
            // Handle the case when the questionnaire is not found
            // You can redirect to an error page or handle it as per your requirement
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateQuestionnaire(
            @PathVariable("id") Long id, @ModelAttribute("questionnaire") Questionnaire questionnaire) {
        Optional<Questionnaire> existingQuestionnaire = questionnaireService.getQuestionnaireById(id);
        if (existingQuestionnaire.isPresent()) {
            questionnaire.setId(id);
            Questionnaire updatedQuestionnaire = questionnaireService.updateQuestionnaire(questionnaire);
            return "redirect:/questionnaires/list";
        } else {
            // Handle questionnaire not found case
            return "error";
        }
    }

    // Fix me: change to @DeleteMapping
    @GetMapping("/delete/{id}")
    public String deleteQuestionnaire(@PathVariable("id") Long id) {
        try {
            questionnaireService.deleteQuestionnaire(id);
            return "redirect:/questionnaires/list";
        } catch (EmptyResultDataAccessException e) {
            return "error";
        }
    }
}