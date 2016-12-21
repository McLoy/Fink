package ua.tifoha.fink.controllers;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

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
//        List<User> userList = userService.findAll()
//                .stream()
//                .map(User::new)
//                .collect(Collectors.toList());
        model.addObject("userList", userService.findAll());
        return model;
    }

    @RequestMapping (params = "form", method = GET)
    public ModelAndView getUserNewForm() {
//        return getJobDetailModel(new JobModel());
        return null;
    }

    @RequestMapping (value = "/{key}", method = GET, params = "form")
    public ModelAndView getUserEditForm(@PathVariable(value = "key") Optional<JobKey> key) {
//        JobModel jobModel = key
//                .flatMap(jobService::findJobByKey)
//                .map(JobModel::new)
//                .orElseThrow(NotFoundException::new);
//
//        Collection<SimpleTrigger> triggers = jobService.getTriggersOfJob(key.orElseThrow(NotFoundException::new));
//        jobModel.setTriggers(triggers);
//
//        return getJobDetailModel(jobModel);
        return null;
    }

    @RequestMapping (value = "/{key}", method = POST)
    public ModelAndView save(@PathVariable ("key") Optional<JobKey> key,
                             @ModelAttribute("job") JobModel model) {
//        JobBuilder jobBuilder = key.flatMap(jobService::findJobByKey)
//                .map(JobDetail::getJobBuilder)
//                .orElseGet(JobBuilder::newJob);
//
//
//        JobDetail jobDetail = jobBuilder
//                .ofType(model.getJobClass())
//                .withDescription(model.getDescription())
//                .usingJobData(new JobDataMap(model.getJobDataMap()))
//                .storeDurably(model.isDurable())
//                .requestRecovery(model.isRequestsRecovery())
//                .build();
//
//        jobService.saveJob(jobDetail);
//
//        return new ModelAndView("redirect:/user/" + jobDetail.getKey() + "?form");
        return null;
    }

    private ModelAndView getUserDetailModel(JobModel model) {
//        ModelAndView view = new ModelAndView("user/edit");
//        view.addObject("timeUnits", TimeUnit.values());
//        view.addObject("entity", model);
//        view.addObject("title", "Job");
//        view.addObject("jobClasses", jobService.getJobClasses());
//        return view;
        return null;
    }


    @RequestMapping (value = "/{key}", method = DELETE)
    public void delete(@PathVariable ("key") Optional<JobKey> key,
                       HttpServletResponse response) {
//        jobService.deleteJob(key.orElseThrow(NotFoundException::new));
    }

}
