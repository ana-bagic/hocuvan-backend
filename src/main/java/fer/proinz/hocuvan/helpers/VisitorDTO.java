package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.Visitor;

public class VisitorDTO {
	
    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phoneNumber;


    private boolean isVisitor = true;



    public VisitorDTO(String firstName, String lastName, String username, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }



    public VisitorDTO() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean getIsVisitor() {
        return isVisitor;
    }

    public static VisitorDTO fromVisitorToVisitorDTO(Visitor visitor){
        return new VisitorDTO(visitor.getFirstName(), visitor.getLastName(),visitor.getUsername(),visitor.getEmail(),visitor.getPhoneNumber());
    }

}
