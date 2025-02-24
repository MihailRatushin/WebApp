package ru.ratushin.spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ratushin.spring.DAO.PersonDAO;
import ru.ratushin.spring.Model.Person;

@Controller
@RequestMapping("/admin")
public class AdminController {

    PersonDAO personDAO;

    @Autowired
    public AdminController(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDAO.index());
        return "adminPage";
    }
    @PatchMapping("/add")
    public String addAdmin(@ModelAttribute("person") Person person){
        System.out.println(person.getId());
        return "redirect:/people";
    }
}
