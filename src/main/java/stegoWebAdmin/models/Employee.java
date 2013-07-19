package stegoWebAdmin.models;

public class Employee {
    private int idEmployee;
    private String name;
    private String surname;
    private String secondName;
    private String address;
    private String phone;
    private String email;
    private byte[] photo;

    public Employee() {
    }

    public Employee(int idEmployee, String name, String surname, String secondName, String address, String phone, String email, byte[] photo) {
        this.idEmployee = idEmployee;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
    }

    public Employee(String name, String surname, String secondName, String address, String phone, String email, byte[] photo) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
