package a2_2001040230.model;

import a2_2001040230.common.PatronType;

import java.util.Date;
import java.util.regex.Pattern;

public class Patron {

    private int id;
    private String name;
    private Date dob;
    private String email;
    private String phone;
    private PatronType type;

    public static int currentId = 0;

    public Patron(String name, Date dob, String email, String phone, PatronType type) throws Exception {
        if (!validateName(name)) {
            throw new Exception("Name is invalid");
        }
        if (!validateEmail(email)) {
            throw new Exception("Email is invalid");
        }
        if (!validatePhone(phone)) {
            throw new Exception("Phone is invalid");
        }
        this.id = currentId;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    public Patron(int id, String name, Date dob, String email, String phone, PatronType type) throws Exception {
        this(name, dob, email, phone, type);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PatronType getType() {
        return type;
    }

    public void setType(PatronType type) {
        this.type = type;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int currentId) {
        Patron.currentId = currentId;
    }

    private boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }

    private boolean validatePhone(String phone) {
        String regex = "\\d+";
        Pattern emailPattern = Pattern.compile(regex);
        return emailPattern.matcher(phone).matches();
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^(.+)@(\\S+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        return emailPattern.matcher(email).matches();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
