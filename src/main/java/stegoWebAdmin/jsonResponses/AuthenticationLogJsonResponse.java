package stegoWebAdmin.jsonResponses;

import stegoWebAdmin.models.AuthLogEntry;

import java.util.ArrayList;


public class AuthenticationLogJsonResponse {
    private ArrayList<AuthLogEntry> logEntries;

    public AuthenticationLogJsonResponse(ArrayList<AuthLogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    public ArrayList<AuthLogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(ArrayList<AuthLogEntry> logEntries) {
        this.logEntries = logEntries;
    }
}
