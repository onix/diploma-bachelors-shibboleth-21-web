package stegoWebAdmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import stegoWebAdmin.db.dao.EmployeeDAO;

@Controller
@RequestMapping("search")
public class SearchController extends AbstractController {
    @Autowired
    EmployeeDAO employeeDao;

    @RequestMapping(method = RequestMethod.GET)
    public String getSearchPage() {
        return "search";
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    public String searchRouter(@RequestParam("criteria") String criteria,
                               @RequestParam("query") String query) {
        if (criteria.equals("surname"))
            return "redirect:surname/" + query;
        if (criteria.equals("phone"))
            return "redirect:phone/" + query;
        return "redirect:email/" + query;
    }

    @RequestMapping(value = "surname/{surname}", method = RequestMethod.GET)
    public String findEmployeesBySurname(Model model, @PathVariable String surname) {
        if (surname != null) {
            model.addAttribute("searchBy", "surname");
            model.addAttribute("query", surname);
            model.addAttribute("employeesList", employeeDao.findAllEmployeesBySurname(surname));
        }
        return "search-results";
    }

    @RequestMapping(value = "phone/{phone}", method = RequestMethod.GET)
    public String findEmployeesByPhone(Model model, @PathVariable String phone) {
        if (phone != null) {
            model.addAttribute("searchBy", "phone");
            model.addAttribute("query", phone);
            model.addAttribute("employeesList", employeeDao.findAllEmployeesByPhone(phone));
        }
        return "search-results";
    }

    @RequestMapping(value = "email/{email}", method = RequestMethod.GET)
    public String findEmployeesByEmail(Model model, @PathVariable String email) {
        if (email != null) {
            model.addAttribute("searchBy", "email");
            model.addAttribute("query", email);
            model.addAttribute("employeesList", employeeDao.findAllEmployeesByEmail(email));
        }
        return "search-results";
    }
}
