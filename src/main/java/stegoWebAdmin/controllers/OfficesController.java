package stegoWebAdmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import stegoWebAdmin.db.dao.OfficeDAO;
import stegoWebAdmin.jsonResponses.OfficesListJsonResponse;
import stegoWebAdmin.models.Office;

@Controller
@RequestMapping("offices")
public class OfficesController extends AbstractController {
    @Autowired
    OfficeDAO officeDao;

    @RequestMapping(value = "get-offices-list", method = RequestMethod.GET)
    public
    @ResponseBody
    OfficesListJsonResponse getOfficesList() {
        return new OfficesListJsonResponse(officeDao.findAllOffices());
    }

    @RequestMapping(value = "create-new-office", method = RequestMethod.GET)
    public String createNewOfficeGetPage(Model model) {
        model.addAttribute("headerTitle", "Create New Office");
        model.addAttribute("formSendUrlPart", "create-new-office");
        model.addAttribute("pageLegend", "Create new office entry");
        return "office-create-edit";
    }

    @RequestMapping(value = "create-new-office", method = RequestMethod.POST)
    public String createNewOffice(@RequestParam("officeName") String name,
                                  @RequestParam("officeAddress") String address
    ) {
        Office newOfficeEntry = new Office(name, address);
        officeDao.insert(newOfficeEntry);
        return "redirect:/offices";
    }

    @RequestMapping(value = "edit/{officeId}", method = RequestMethod.GET)
    public String editOfficeGet(ModelMap model, @PathVariable int officeId) {
        Office officeToEdit = officeDao.findByOfficeId(officeId);
        model.addAttribute("officeName", officeToEdit.getName());
        model.addAttribute("officeAddress", officeToEdit.getAddress());

        model.addAttribute("headerTitle", "Edit Office");
        model.addAttribute("formSendUrlPart", "edit/" + officeToEdit.getIdOffice());
        model.addAttribute("pageLegend", "Edit Office Info with id " + officeToEdit.getIdOffice());
        return "office-create-edit";
    }

    @RequestMapping(value = "edit/{officeId}", method = RequestMethod.POST)
    public String editOfficePost(@PathVariable int officeId,
                                 @RequestParam("officeName") String name,
                                 @RequestParam("officeAddress") String address
    ) {
        Office officeToEdit = officeDao.findByOfficeId(officeId);
        officeToEdit.setName(name);
        officeToEdit.setAddress(address);
        officeDao.update(officeToEdit);
        return "redirect:/offices";
    }

    @RequestMapping(value = "delete/{officeId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteOffice(@PathVariable int officeId) {
        officeDao.deleteByOfficeId(officeId);
    }
}
