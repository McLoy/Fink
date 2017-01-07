package ua.tifoha.fink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.tifoha.fink.entities.User;
import ua.tifoha.fink.exceptions.HttpException;
import ua.tifoha.fink.exceptions.NotFoundException;
import ua.tifoha.fink.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET)
    public ModelAndView getUserList() {
        ModelAndView model = new ModelAndView("user/list");
        List<User> userList = userService.findAll();
        model.addObject("userList", userList);
        return model;
    }

    @RequestMapping(params = "form", method = GET)
    public ModelAndView getUserNewForm() {
        return getUserModel(new User());
    }

    private ModelAndView getUserModel(User user) {
        ModelAndView view = new ModelAndView("user/edit");
        view.addObject("entity", user);
        return view;
    }

    @RequestMapping(value = "/{id}", method = GET, params = "form")
    public ModelAndView getUserEditForm(@PathVariable(value = "id") Long id) {
        User currUser = userService.findOne(id);
        if (currUser == null) {
            throw new NotFoundException();
        } else {
            currUser.setPassword("******");
        }
        return getUserModel(currUser);
    }

    @RequestMapping(value = "/{id}", method = POST)
    public ModelAndView save(@PathVariable("id") Long id,
                             @RequestParam String newpass,
                             @RequestParam String confirm,
                             @ModelAttribute User user) {

        String userPassword = user.getPassword();
        UserDetails userFromDB = userService.findOne(id);

        if (userFromDB == null) {
            if (user.getPassword().isEmpty() || !user.getPassword().equals(confirm)) {
                throw new NotFoundException();
            }

        } else {
            String passFromDB = userFromDB.getPassword();
            if (passFromDB.equals(userPassword) && newpass.equals(confirm)) {
                user.setPassword(newpass);
                user.setId(id);
            }
        }
        userService.save(user);
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping(value = "/newuser", method = POST)
    public ModelAndView save(@RequestParam String confirm,
                             @ModelAttribute User user) {
        if (user.getPassword().isEmpty() || !user.getPassword().equals(confirm)) {
            throw new NotFoundException();
        }
        userService.save(user);
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void delete(@PathVariable("id") Long id, HttpServletResponse response) {
        userService.delete(id);
    }
}
