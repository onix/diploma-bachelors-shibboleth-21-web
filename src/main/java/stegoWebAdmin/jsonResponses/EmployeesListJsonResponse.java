package stegoWebAdmin.jsonResponses;

import stegoWebAdmin.models.Employee;

import java.util.ArrayList;


public class EmployeesListJsonResponse {
    private ArrayList<Employee> employees;

    public EmployeesListJsonResponse(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
}
