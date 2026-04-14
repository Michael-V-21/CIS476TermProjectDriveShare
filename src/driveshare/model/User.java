package driveshare.model;

// Class representing the user
// Stores login credentials

public class User
{
    private String email;
    private String password;

    private String securityQuestion1;
    private String securityAnswer1;
    private String securityQuestion2;
    private String securityAnswer2;
    private String securityQuestion3;
    private String securityAnswer3;

    public User(String email, String password,
                String securityQuestion1, String securityAnswer1,
                String securityQuestion2, String securityAnswer2,
                String securityQuestion3, String securityAnswer3)
    {
        this.email = email;
        this.password = password;
        this.securityQuestion1 = securityQuestion1;
        this.securityAnswer1 = securityAnswer1;
        this.securityQuestion2 = securityQuestion2;
        this.securityAnswer2 = securityAnswer2;
        this.securityQuestion3 = securityQuestion3;
        this.securityAnswer3 = securityAnswer3;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestion1() {
        return securityQuestion1;
    }

    public String getSecurityAnswer1() {
        return securityAnswer1;
    }

    public String getSecurityQuestion2() {
        return securityQuestion2;
    }

    public String getSecurityAnswer2() {
        return securityAnswer2;
    }

    public String getSecurityQuestion3() {
        return securityQuestion3;
    }

    public String getSecurityAnswer3() {
        return securityAnswer3;
    }
}