package stegoWebAdmin.jsonResponses;

import stegoWebAdmin.models.Office;

import java.util.ArrayList;


public class OfficesListJsonResponse {
    private ArrayList<Office> offices;

    public OfficesListJsonResponse(ArrayList<Office> offices) {
        this.offices = offices;
    }

    public ArrayList<Office> getOffices() {
        return offices;
    }

    public void setOffices(ArrayList<Office> offices) {
        this.offices = offices;
    }
}
