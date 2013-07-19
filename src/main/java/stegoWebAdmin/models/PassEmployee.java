package stegoWebAdmin.models;

import java.sql.Timestamp;

public class PassEmployee {
    private int idEmployee;
    private boolean isPassActive;
    private Timestamp dateOfExpiration;
    private byte[] authenticationId;

    public PassEmployee(int idEmployee, boolean passActive, Timestamp dateOfExpiration, byte[] authenticationId) {
        this.idEmployee = idEmployee;
        isPassActive = passActive;
        this.dateOfExpiration = dateOfExpiration;
        this.authenticationId = authenticationId;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public boolean isPassActive() {
        return isPassActive;
    }

    public void setPassActive(boolean passActive) {
        isPassActive = passActive;
    }

    public Timestamp getDateOfExpiration() {
        return dateOfExpiration;
    }

    public void setDateOfExpiration(Timestamp dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
    }

    public byte[] getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(byte[] authenticationId) {
        this.authenticationId = authenticationId;
    }
}