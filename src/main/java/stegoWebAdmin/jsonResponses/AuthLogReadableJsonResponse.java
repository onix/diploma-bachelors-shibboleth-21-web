package stegoWebAdmin.jsonResponses;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AuthLogReadableJsonResponse {
    private int idEntry;
    private int officeId;
    private int typeOfEntryId;
    private Timestamp timeOfAction;
    private int idEmployee;
    private String name;
    private String surname;

    public int getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(int idEntry) {
        this.idEntry = idEntry;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public int getTypeOfEntryId() {
        return typeOfEntryId;
    }

    public void setTypeOfEntryId(int typeOfEntryId) {
        this.typeOfEntryId = typeOfEntryId;
    }

    public Timestamp getTimeOfAction() {
        return timeOfAction;
    }

    public void setTimeOfAction(Timestamp timeOfAction) {
        this.timeOfAction = timeOfAction;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
