package stegoWebAdmin.models;

import generalLogic.TimeUtils;

import java.sql.Timestamp;

public class AuthLogEntry {
    private int idEntry;
    private int officeId;
    private int typeOfEntryId;
    private Timestamp timeOfAction;
    private int employeeId;

    public AuthLogEntry() {
    }

    public AuthLogEntry(int officeId, String result) {
        this.officeId = officeId;
        if (result.equals("ok"))
            this.typeOfEntryId = 1;
        else
            this.typeOfEntryId = 2;

        this.timeOfAction = new java.sql.Timestamp(TimeUtils.getCurrentUtcDate().getTime());
    }

    public AuthLogEntry(int officeId, String result, int employeeId) {
        this.officeId = officeId;
        if (result.equals("ok"))
            this.typeOfEntryId = 1;
        else
            this.typeOfEntryId = 2;

        this.timeOfAction = new java.sql.Timestamp(TimeUtils.getCurrentUtcDate().getTime());
        this.employeeId = employeeId;
    }

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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
