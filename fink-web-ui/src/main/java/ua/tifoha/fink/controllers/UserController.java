package ua.tifoha.fink.controllers;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.tifoha.fink.entities.User;
import ua.tifoha.fink.model.JobModel;
import ua.tifoha.fink.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping (method = GET)
    public ModelAndView getUserList() {
        ModelAndView model = new ModelAndView("user/list");
        List<User> userList = userService.findAll();
        model.addObject("userList", userList);
        return model;
    }

    @RequestMapping (params = "form", method = GET)
    public ModelAndView getUserNewForm() {
        return getUserDetailModel(new User());
    }

    private ModelAndView getUserDetailModel(User user) {
        ModelAndView view = new ModelAndView("user/edit");
        view.addObject("name", user.getName());
        view.addObject("email", user.getEmail());
        view.addObject("password", user.getPassword());
        view.addObject("enabled", user.isEnabled());
        return view;
    }

    @RequestMapping (value = "/{name}", method = POST)
    public ModelAndView save(@PathVariable ("name") String name,
                             @ModelAttribute("user") User user) {
        userService.save(user);

        return new ModelAndView("redirect:/user/" + user.getName() + "?form");
    }

    @RequestMapping (value = "/{id}", method = DELETE)
    public void delete(@PathVariable("id") Long id, HttpServletResponse response) {
        userService.delete(id);
    }

}
