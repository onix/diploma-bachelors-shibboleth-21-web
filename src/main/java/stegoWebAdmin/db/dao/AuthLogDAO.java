package stegoWebAdmin.db.dao;

import stegoWebAdmin.jsonResponses.AuthLogReadableJsonResponse;
import stegoWebAdmin.models.AuthLogEntry;

import java.util.ArrayList;

public interface AuthLogDAO {
    ArrayList<AuthLogEntry> findAllEntries();

    public void insert(AuthLogEntry entry);

    ArrayList<AuthLogReadableJsonResponse> findEntriesInReadableForm();
}
