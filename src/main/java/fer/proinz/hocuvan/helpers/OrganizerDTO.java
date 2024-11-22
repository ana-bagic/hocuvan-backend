package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.Organizer;

public class OrganizerDTO {
	
    private String orgName;

    private String headquarters;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private  boolean isVisitor=false;



    public OrganizerDTO(String orgName, String headquarters, String username, String password, String email, String phoneNumber) {
        this.orgName = orgName;
        this.headquarters = headquarters;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    public OrganizerDTO() {
    }



    public void setOrgName(String organisationName) {
        this.orgName = organisationName;
    }

    public void setHeadquarters(String headquarter) {
        this.headquarters = headquarters;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsVisitor() {
        return isVisitor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static OrganizerDTO fromOrganizerToOrganizerDTO(Organizer organizer){
        return new OrganizerDTO(organizer.getOrgName(), organizer.getHeadquarters(),organizer.getUsername(),organizer.getPassword(),organizer.getEmail(),organizer.getPhoneNumber());
    }
}
