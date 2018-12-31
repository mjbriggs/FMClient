package model;
/** User object modeling a row in the User tablu.
 * Contains User information
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */
public class User {
    /** Unique user name */
    private String userName = null;
    /** user's password */
    private String password  = null;
    /** user's first name */
    private String firstName  = null;
    /** user's last name */
    private String lastName = null;
    /** user's gender */
    private String gender = null;
    /** User's email address */
    private String email = null;
    /** unique person id assigned to this user's generated person object */
    private String personID = null;

    /** Default Constructor
     */
    public User(){
        userName = "";
        password = "";
        firstName = "";
        lastName = "";
        gender = "";
        email = "";
        personID = "";
    };
    /** Constructor that sets user information
     * @param usernameIn username to be set
     * @param passwordIn password to be set
     * @param firstNameIn first name to be set
     * @param lastNameIn last name to be set
     * @param genderIn gender to be set
     * @param emailIn email to be set
     * @param personIDIn person ID to be set
     */
    public User(String usernameIn, String passwordIn, String emailIn, String firstNameIn, String lastNameIn,
                String genderIn, String personIDIn){
        userName = usernameIn;
        password = passwordIn;
        firstName = firstNameIn;
        lastName = lastNameIn;
        gender = genderIn;
        email = emailIn;
        personID = personIDIn;
    }
    public String getUsername(){
        return userName;
    };
    public void setUsername(String usernameIn){
        userName = usernameIn;
    };
    public String getPassword(){
        return password;
    };
    public void setPassword(String passwordIn){
        password = passwordIn;
    };
    public String getFirstName(){
        return firstName;
    };
    public void setFirstName(String firstNameIn){
        firstName = firstNameIn;
    };
    public String getLastName(){
        return lastName;
    };
    public void setLastName(String lastNameIn){
        lastName = lastNameIn;
    };
    public String getGender(){
        return gender;
    };
    public void setGender(String genderIn){
        gender = genderIn;
    };
    public String getEmail(){
        return email;
    };
    public void setEmail(String emailIn){
        email = emailIn;
    };
    public String getPersonID(){
        return personID;
    };
    public void setPersonID(String personIDIn){
        personID = personIDIn;
    };
    public boolean validUser(){
        System.out.println("checking if User is valid");
        if(userName == null || password == null|| firstName == null
                || lastName == null || gender == null || email == null
                || personID == null){
            System.out.println("User is NOT valid");
            return false;
        }
        else if(userName.equals("") || password.equals("") || firstName.equals("")
                || lastName.equals("") || gender.equals("") || email.equals("")
                || personID.equals("")){
            System.out.println("User is NOT valid");
            return false;
        }
        else if(!gender.equals("m") && !gender.equals("f")){
            System.out.println("User is NOT valid");
            return false;
        }
        else {
            System.out.println("User is valid");
            return true;
        }
    }
    public boolean equals(Object o){
        if(o == null)
            return false;
        else if(o instanceof User){
            User u = (User) o;
            if(this.userName.equals(u.getUsername()) &&
                    this.password.equals(u.getPassword()) &&
                    this.firstName.equals(u.getFirstName()) &&
                    this.lastName.equals(u.getLastName()) &&
                    this.gender.equals(u.getGender()) &&
                    this.email.equals(u.getEmail()) &&
                    this.personID.equals(u.getPersonID())){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        return this.userName + "; " + this.password + "; "
                + this.email + "; " + this.firstName + "; " + this.lastName + "; "
                + this.gender + "; "  + this.personID + "; ";
    }

}
