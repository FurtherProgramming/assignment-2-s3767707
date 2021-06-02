package main.model;

public class User{

    private String employerId;
    private String firstname;
    private String lastname;
    private String role;
    private String username;
    private String password;
    private String question;
    private String answer;


    public User(String empId, String fn, String ln, String role, String un, String pass, String ques, String ans) {

        this.employerId = empId;
        this.firstname = fn;
        this.lastname = ln;
        this.role = role;
        this.username = un;
        this.password = pass;
        this.question = ques;
        this.answer = ans;
    }

    public String getUsername() {

        return username;
    }

    public String getAnswer() {

        return answer;
    }

    public String getQuestion() {

        return question;
    }

    public String getEmployerId() {

        return employerId;
    }

    public String getPassword() {

        return employerId;
    }

    public String getFirstName() {

        return firstname;
    }

    public String getLastName() {

        return lastname;
    }

    public String getRole() {

        return role;
    }

}
