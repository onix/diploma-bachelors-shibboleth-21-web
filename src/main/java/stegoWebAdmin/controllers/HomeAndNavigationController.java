package stegoWebAdmin.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeAndNavigationController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            logger.info("Authenticated user, redirecting to /employees");
            return "redirect:/employees";
        } else {
            logger.info("Unauthorized user, redirecting to /unauthorized");
            return "redirect:/unauthorized";
        }
    }

    @RequestMapping(value = "employees", method = RequestMethod.GET)
    public String displayEmployeesPage() {
        logger.info("/employees called");
        return "employees";
    }

    @RequestMapping(value = "offices", method = RequestMethod.GET)
    public String displayOfficesPage() {
        logger.info("/offices called");
        return "offices";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String displaySearchPage() {
        logger.info("/search called");
        return "search";
    }

    @RequestMapping(value = "auth-log", method = RequestMethod.GET)
    public String displayAuthenticationLogPage() {
        logger.info("/auth-log called");
        return "auth-log";
    }
}