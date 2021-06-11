package main.model;

/*
 * Class:		User
 * Description:	A class that represents a user object. A user can be an admin or normal user
 * Author:		Anson Go Guang Ping
 */
public class User {

    private String employeeId;
    private String firstname;
    private String lastname;
    private String role;
    private String username;
    private String password;
    private String question;
    private String answer;
    private String status;


    public User(String empId, String fn, String ln, String role, String un, String pass, String ques, String ans, String status) {

        this.employeeId = empId;
        this.firstname = fn;
        this.lastname = ln;
        this.role = role;
        this.username = un;
        this.password = pass;
        this.question = ques;
        this.answer = ans;
        this.status = status;
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

    public String getEmployeeId() {

        return employeeId;
    }

    public String getPassword() {

        return password;
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

    public String getStatus() {

        return status;
    }
}
