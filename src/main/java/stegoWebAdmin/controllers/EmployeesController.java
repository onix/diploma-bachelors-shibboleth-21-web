package stegoWebAdmin.controllers;

import generalLogic.AuthenticatorGenerator;
import generalLogic.PassImageGenerator;
import generalLogic.SecretStorage;
import generalLogic.TimeUtils;
import imageUtil.ImageLoader;
import imageUtil.Picture;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import stegoWebAdmin.db.dao.EmployeeDAO;
import stegoWebAdmin.db.dao.PassEmployeeDAO;
import stegoWebAdmin.jsonResponses.EmployeesListJsonResponse;
import stegoWebAdmin.models.Employee;
import stegoWebAdmin.models.PassEmployee;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

@Controller
@RequestMapping("employees")
public class EmployeesController extends AbstractController {
    @Autowired
    EmployeeDAO employeeDao;
    @Autowired
    PassEmployeeDAO passEmployeeDao;


    private boolean fileContentTypeIsValid(String fileContentType) {
        return fileContentType.equals(MediaType.IMAGE_JPEG_VALUE) || fileContentType.equals(MediaType.IMAGE_PNG_VALUE);
    }

    @RequestMapping(value = "get-employees-list", method = RequestMethod.GET)
    public
    @ResponseBody
    EmployeesListJsonResponse getEmployeesList() {
        return new EmployeesListJsonResponse(employeeDao.findAllEmployees());
    }

    @RequestMapping(value = "create-new-employee", method = RequestMethod.GET)
    public String createNewEmployeeGetPage(Model model) {
        model.addAttribute("pageTitle", "Create New Employee");
        model.addAttribute("formProcessUrl", "create-new-employee");
        logger.info("Create new employee page returned.");
        return "employee-create-edit";
    }

    @RequestMapping(value = "create-new-employee", method = RequestMethod.POST)
    public String createNewEmployeePostPage(MultipartHttpServletRequest request) throws FileUploadException, ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            String employeeName = request.getParameter("employeeName");
            String employeeSurname = request.getParameter("employeeSurname");
            String employeeSecondName = request.getParameter("employeeSecondName");
            String employeeAddress = request.getParameter("employeeAddress");
            String employeePhone = request.getParameter("employeePhone");
            String employeeEmail = request.getParameter("employeeEmail");
            byte[] employeePhoto = new byte[0];

            Iterator<String> itr = request.getFileNames();
            MultipartFile file;

            while (itr.hasNext()) {
                file = request.getFile(itr.next());
                if (!file.isEmpty() && fileContentTypeIsValid(file.getContentType())) {
                    Picture pic = ImageLoader.fromBytes(file.getBytes()); //TODO
                    Picture newPic = PassImageGenerator.resizeInputImageToParameters(pic, SecretStorage.STEGO_PHOTO_WIDTH, SecretStorage.STEGO_PHOTO_HEIGHT);

                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    ImageIO.write(newPic.getBufferedImage(), "png", output);

                    employeePhoto = output.toByteArray();
                    logger.info("Uploaded!");
                } else {
                    logger.info("Wrong MIME type! Need image/jpeg or image/png, type found: " + file.getContentType());
                }
            }
            int newEmployeeId = employeeDao.insert(new Employee(employeeName, employeeSurname, employeeSecondName, employeeAddress, employeePhone, employeeEmail, employeePhoto));
            passEmployeeDao.insert(new PassEmployee(newEmployeeId, true, null, AuthenticatorGenerator.authenticatorRandomGenerator(32)));
        }
        return "redirect:/employees";
    }

    @RequestMapping(value = "delete/{employeeId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteOffice(@PathVariable int employeeId) {
        passEmployeeDao.deleteByEmployeeId(employeeId);
        employeeDao.deleteByEmployeeId(employeeId);
    }

    @RequestMapping(value = "get-employee-photo/{employeeId}", produces = {"image/png"}, method = RequestMethod.GET)
    public
    @ResponseBody
    byte[] getEmployeePhoto(@PathVariable int employeeId) {
        return employeeDao.findByEmployeeId(employeeId).getPhoto();
    }

    @RequestMapping(value = "edit/{employeeId}", method = RequestMethod.GET)
    public String editEmployeeInfoGetPage(Model model, @PathVariable int employeeId) {
        Employee employee = employeeDao.findByEmployeeId(employeeId);
        PassEmployee pass = passEmployeeDao.findPassByEmployeeId(employeeId);

        model.addAttribute("pageTitle", "Edit Employee with id " + employee.getIdEmployee());
        model.addAttribute("formProcessUrl", "edit/" + employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("pass", pass);
        model.addAttribute("photo", "data:image/png;base64," + Base64.encodeBase64String(employee.getPhoto()));
        model.addAttribute("authId", Base64.encodeBase64String(pass.getAuthenticationId()));
        model.addAttribute("editMode", true);

        logger.info("Edit employee page returned.");
        return "employee-create-edit";
    }

    @RequestMapping(value = "edit/{employeeId}", method = RequestMethod.POST)
    public String editEmployeeInfoPostPage(
            MultipartHttpServletRequest request,
            @PathVariable int employeeId,
            @RequestParam("employeeName") String name,
            @RequestParam("employeeSurname") String surname,
            @RequestParam("employeeSecondName") String secondName,
            @RequestParam("employeeAddress") String address,
            @RequestParam("employeePhone") String phone,
            @RequestParam("employeeEmail") String email,
            @RequestParam(value = "isPassActive", required = false) String isPassActive,
            @RequestParam("passExpirationDate") String passExpirationDate
    ) throws IOException {
        Employee employee = employeeDao.findByEmployeeId(employeeId);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setSecondName(secondName);
        employee.setAddress(address);
        employee.setPhone(phone);
        employee.setEmail(email);

        Iterator<String> itr = request.getFileNames();  //TODO repeated code
        MultipartFile file;

        while (itr.hasNext()) {
            file = request.getFile(itr.next());
            if (!file.isEmpty() && fileContentTypeIsValid(file.getContentType())) {
                Picture pic = ImageLoader.fromBytes(file.getBytes()); //TODO
                Picture newPic = PassImageGenerator.resizeInputImageToParameters(pic, SecretStorage.STEGO_PHOTO_WIDTH, SecretStorage.STEGO_PHOTO_HEIGHT);

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(newPic.getBufferedImage(), "png", output);

                employee.setPhoto(output.toByteArray());
                logger.info("Uploaded!");
            } else {
                logger.info("Wrong MIME type! Need image/jpeg or image/png, type found: " + file.getContentType());
            }
        }
        employeeDao.update(employee);

        PassEmployee pass = passEmployeeDao.findPassByEmployeeId(employeeId);
        if (isPassActive == null)
            pass.setPassActive(false);
        else
            pass.setPassActive(true);


        if (!passExpirationDate.equals(""))
            pass.setDateOfExpiration(new Timestamp(TimeUtils.convertStringToDate(passExpirationDate).getTime()));
        else
            pass.setDateOfExpiration(null);
        passEmployeeDao.update(pass);

        return "redirect:" + employeeId;
    }
}
