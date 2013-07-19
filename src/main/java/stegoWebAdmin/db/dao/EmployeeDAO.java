package stegoWebAdmin.db.dao;

import stegoWebAdmin.models.Employee;

import java.util.ArrayList;

public interface EmployeeDAO {
    ArrayList<Employee> findAllEmployees();

    ArrayList<Employee> findAllEmployeesBySurname(String surname);

    ArrayList<Employee> findAllEmployeesByPhone(String phone);

    ArrayList<Employee> findAllEmployeesByEmail(String email);

    public Employee findByEmployeeId(int employeeId);

    public int insert(Employee employee);

    public void update(Employee employee);

    public boolean deleteByEmployeeId(int employeeId);
}
