package stegoWebAdmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import stegoWebAdmin.db.dao.AuthLogDAO;
import stegoWebAdmin.jsonResponses.AuthLogReadableJsonResponse;

import java.util.ArrayList;

@Controller
@RequestMapping("logs")
public class LogsController extends AbstractController {
    @Autowired
    AuthLogDAO authLogDao;

    @RequestMapping(value = "get-authentication-log", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<AuthLogReadableJsonResponse> getAuthenticationLog() {
        return authLogDao.findEntriesInReadableForm();
        //return new AuthenticationLogJsonResponse(authLogDao.findAllEntries());
    }
}
