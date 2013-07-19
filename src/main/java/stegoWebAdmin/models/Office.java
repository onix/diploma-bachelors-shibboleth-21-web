package stegoWebAdmin.models;

public class Office {
    private int idOffice;
    private String name;
    private String address;

    public Office() {
    }

    public Office(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Office(int idOffice, String name, String address) {
        this.idOffice = idOffice;
        this.name = name;
        this.address = address;
    }

    public int getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(int idOffice) {
        this.idOffice = idOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
