package lab1.emt.web.controller;


import lab1.emt.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final AuthorService authorService;

    public ManufacturerController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getCategoryPage(Model model){
        model.addAttribute("manufacturers", this.authorService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "master-template";
    }
}
