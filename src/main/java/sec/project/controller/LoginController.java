package sec.project.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sec.project.domain.User;
import sec.project.repository.UserRepository;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String get() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String post(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            return "error";
        }

        response.addCookie(new Cookie("user", ""+user.getId()));

        return "redirect:/home/" + user.getId();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login";
    }
}