package stegoWebAdmin.db.dao;

import stegoWebAdmin.models.Office;

import java.util.ArrayList;

public interface OfficeDAO {
    ArrayList<Office> findAllOffices();

    public Office findByOfficeId(int officeId);

    public void insert(Office office);

    public void update(Office office);

    public boolean deleteByOfficeId(int officeId);
}
