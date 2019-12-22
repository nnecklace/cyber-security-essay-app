package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sec.project.domain.User;
import sec.project.repository.UserRepository;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/home/{id}", method = RequestMethod.GET)
    public String index(@PathVariable String id, Model model) {
        User user = userRepository.findOne(Long.parseLong(id));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userid", user.getId());
        return "home";
    }

    @RequestMapping(value = "/home/{id}/details", method = RequestMethod.GET)
    public String details(@PathVariable String id, Model model) {
        User user = userRepository.findOne(Long.parseLong(id));

        if (user == null) {
            // return error user with id not found
            return "error";
        }

        model.addAttribute("user", user);
        return "details";
    }
}