package stegoWebAdmin.controllers;

import generalLogic.PassImageChecker;
import generalLogic.TimeUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import stegoWebAdmin.db.dao.AuthLogDAO;
import stegoWebAdmin.db.dao.EmployeeDAO;
import stegoWebAdmin.db.dao.OfficeDAO;
import stegoWebAdmin.db.dao.PassEmployeeDAO;
import stegoWebAdmin.models.AuthLogEntry;
import stegoWebAdmin.models.Employee;
import stegoWebAdmin.models.Office;
import stegoWebAdmin.models.PassEmployee;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("passes")
public class PassDemoController extends AbstractController {
    @Autowired
    EmployeeDAO employeeDao;
    @Autowired
    PassEmployeeDAO passEmployeeDao;
    @Autowired
    AuthLogDAO authLogDao;
    @Autowired
    OfficeDAO officeDao;

    private boolean fileContentTypeIsValid(String fileContentType) {
        return fileContentType.equals(MediaType.IMAGE_JPEG_VALUE) || fileContentType.equals(MediaType.IMAGE_PNG_VALUE);
    }

    @RequestMapping(value = "check", method = RequestMethod.GET)
    public String checkUploadedPassImageGetPage(Model model) {
        ArrayList<Office> officesList = officeDao.findAllOffices();
        Map<Integer, String> offices = new LinkedHashMap<Integer, String>();

        for (Office office : officesList) {
            offices.put(office.getIdOffice(), office.getName());
        }
        model.addAttribute("office", new Office());

        model.addAttribute("officesList", offices);
        return "pass-authentication-demo/pass-demo";
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public String checkUploadedPassImage(MultipartHttpServletRequest request) throws IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            int officeID = Integer.parseInt(request.getParameter("office"));
            Iterator<String> itr = request.getFileNames();
            MultipartFile file;

            while (itr.hasNext()) {
                file = request.getFile(itr.next());
                if (!file.isEmpty() && fileContentTypeIsValid(file.getContentType())) {
                    logger.info("Pass image uploaded!");
                    final BufferedImage passImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
                    PassImageChecker checker = new PassImageChecker();
                    byte[] message = checker.checkPassImage(passImage);

                    if (message != null) {
                        logger.info("a");
                        PassEmployee pe = passEmployeeDao.findByEmployeeAuthenticator(message);
                        if (pe != null) {
                            logger.info("b");
                            if (pe.isPassActive()) {
                                logger.info("c");
                                if (pe.getDateOfExpiration() != null) {
                                    logger.info("d");
                                    if (pe.getDateOfExpiration().getTime() >= TimeUtils.getCurrentUtcDate().getTime()) {
                                        logger.info("e");
                                        authLogDao.insert(new AuthLogEntry(officeID, "ok", pe.getIdEmployee())); //TODO
                                        return "redirect:success/" + pe.getIdEmployee();
                                    } else {
                                        logger.info("f");
                                        authLogDao.insert(new AuthLogEntry(officeID, "fail", pe.getIdEmployee())); //TODO
                                        return "redirect:fail/" + pe.getIdEmployee();
                                    }
                                }
                                authLogDao.insert(new AuthLogEntry(officeID, "ok", pe.getIdEmployee())); //TODO
                                return "redirect:success/" + pe.getIdEmployee();
                            } else {
                                authLogDao.insert(new AuthLogEntry(officeID, "fail", pe.getIdEmployee())); //TODO
                                return "redirect:fail/" + pe.getIdEmployee();
                            }
                        }
                    }
                } else {
                    logger.info("Wrong MIME type! Need image/jpeg or image/png, type found: " + file.getContentType());
                }
            }
        }
        authLogDao.insert(new AuthLogEntry(1, "fail"));
        return "pass-authentication-demo/authentication-falure";
    }

    @RequestMapping(value = "success/{employeeId}", method = RequestMethod.GET)
    public String authenticationSuccess(Model model, @PathVariable int employeeId) {
        Employee employee = employeeDao.findByEmployeeId(employeeId);

        model.addAttribute("authStatus", "Success");

        model.addAttribute("idEmployee", employee.getIdEmployee());
        model.addAttribute("employeeName", employee.getName());
        model.addAttribute("employeeSurname", employee.getSurname());
        model.addAttribute("employeeSecondName", employee.getSecondName());
        model.addAttribute("employeeAddress", employee.getAddress());
        model.addAttribute("employeePhone", employee.getPhone());
        model.addAttribute("employeeEmail", employee.getEmail());
        model.addAttribute("employeePhoto", Base64.encodeBase64String(employee.getPhoto()));

        return "pass-authentication-demo/authentication-employee";
    }

    @RequestMapping(value = "fail/{employeeId}", method = RequestMethod.GET)
    public String authenticationFail(Model model, @PathVariable int employeeId) {
        Employee employee = employeeDao.findByEmployeeId(employeeId);

        model.addAttribute("authStatus", "Fail");
        model.addAttribute("authMessage", "We have info about this employee, but his pass isn't active now.");

        model.addAttribute("idEmployee", employee.getIdEmployee());
        model.addAttribute("employeeName", employee.getName());
        model.addAttribute("employeeSurname", employee.getSurname());
        model.addAttribute("employeeSecondName", employee.getSecondName());
        model.addAttribute("employeeAddress", employee.getAddress());
        model.addAttribute("employeePhone", employee.getPhone());
        model.addAttribute("employeeEmail", employee.getEmail());
        model.addAttribute("employeePhoto", Base64.encodeBase64String(employee.getPhoto()));

        return "pass-authentication-demo/authentication-employee";
    }
}
