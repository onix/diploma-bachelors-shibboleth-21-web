package stegoWebAdmin.controllers;

import generalLogic.AuthenticatorGenerator;
import generalLogic.PassImageGenerator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import stegoWebAdmin.db.dao.EmployeeDAO;
import stegoWebAdmin.db.dao.PassEmployeeDAO;
import stegoWebAdmin.models.Employee;
import stegoWebAdmin.models.PassEmployee;

import java.io.IOException;
import java.net.URL;

@Controller
@RequestMapping("passes")
public class PassEmployeeController extends AbstractController {
    @Autowired
    EmployeeDAO employeeDao;
    @Autowired
    PassEmployeeDAO passEmployeeDao;

    @RequestMapping(value = "get/{employeeId}", produces = {"image/png"}, method = RequestMethod.GET)
    public
    @ResponseBody
    byte[] getPassImage(@PathVariable int employeeId) throws IOException {
        Employee employee = employeeDao.findByEmployeeId(employeeId);
        PassEmployee passEmployee = passEmployeeDao.findPassByEmployeeId(employeeId);
        URL backgroundUrl = getClass().getResource("/pass-bg.png");

        return PassImageGenerator.getPassImageForEmployee(employee, passEmployee, backgroundUrl);
    }

    @RequestMapping(value = "regenerate-authenticator/{employeeId}", method = RequestMethod.POST)
    public
    @ResponseBody
    String regenerateAuthenticator(@PathVariable int employeeId) {
        PassEmployee pass = passEmployeeDao.findPassByEmployeeId(employeeId);
        pass.setAuthenticationId(AuthenticatorGenerator.authenticatorRandomGenerator(32));
        passEmployeeDao.update(pass);
        return Base64.encodeBase64String(pass.getAuthenticationId());
    }
}
