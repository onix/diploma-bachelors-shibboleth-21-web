package stegoWebAdmin.db.dao;

import stegoWebAdmin.models.PassEmployee;

public interface PassEmployeeDAO {
    public PassEmployee findPassByEmployeeId(int employeeId);

    PassEmployee findByEmployeeAuthenticator(byte[] authenticator);

    public void insert(PassEmployee passEmployee);

    public void update(PassEmployee passEmployee);

    public boolean deleteByEmployeeId(int employeeId);
}
