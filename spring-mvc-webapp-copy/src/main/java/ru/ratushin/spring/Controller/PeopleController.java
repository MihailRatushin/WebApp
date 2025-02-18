package ru.ratushin.spring.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ru.ratushin.spring.DAO.PersonDAO;
import ru.ratushin.spring.Model.Person;
import ru.ratushin.spring.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
	
    private PersonDAO personDAO;
    private PersonValidator personValidator;
    
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator){
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }
    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String getNew(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        personDAO.create(person);
        return "redirect:/people";
    }
    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable("id") int id ,Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }
    @PatchMapping("{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/remove/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.remove(id);
        return "redirect:/people";
    }
}
