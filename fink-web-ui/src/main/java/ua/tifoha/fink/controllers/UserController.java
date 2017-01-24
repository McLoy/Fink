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
import ua.tifoha.fink.exceptions.NotFoundException;
import ua.tifoha.fink.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                             @RequestParam String newpassword,
                             @RequestParam String confirmpassword,
                             @ModelAttribute User user) {

        Map<String, String> errorsMap = new HashMap<>();
        String userPassword = user.getPassword();
        UserDetails userFromDB = userService.findOne(id);

        if (userFromDB != null) {
            String passFromDB = userFromDB.getPassword();
            if (userPassword.isEmpty()) {
                errorsMap.put("password", "Enter password");
            }
            if (!passFromDB.equals(userPassword)) {
                errorsMap.put("password", "Wrong password");
            }
            if (newpassword.isEmpty()) {
                errorsMap.put("newpassword", "Enter new password");
            }
            if (newpassword.length() <= 3) {
                errorsMap.put("newpassword", "New password must be more than 3 symbols");
            }
            if (newpassword.equals(confirmpassword)) {
                user.setPassword(newpassword);
                user.setId(id);
            } else {
                errorsMap.put("newpassword", "New password not match with confirm password");
            }
        }

        return getModelForUser(user, errorsMap);

    }

    private ModelAndView getModelForUser(@ModelAttribute User user, Map<String, String> errorsMap) {
        if (errorsMap.isEmpty()) {
            userService.save(user);
            return new ModelAndView("redirect:/user");
        } else {
            ModelAndView view = getUserModel(user);
            view.addObject("errors", errorsMap);
            return view;
        }
    }

    @RequestMapping(value = "/newuser", method = POST)
    public ModelAndView save(@RequestParam String confirmpassword,
                             @ModelAttribute User user) {

        Map<String, String> errorsMap = new HashMap<>();
        if (user.getPassword().isEmpty()) {
            errorsMap.put("password", "Enter password");
        }
        if (user.getPassword().length() <= 3){
            errorsMap.put("password", "Password must be more than 3 symbols");
        }
        if (!user.getPassword().equals(confirmpassword)) {
            errorsMap.put("confirmpassword", "Password and confirm password not match");
        }

        return getModelForUser(user, errorsMap);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void delete(@PathVariable("id") Long id, HttpServletResponse response) {
        userService.delete(id);
    }
}
