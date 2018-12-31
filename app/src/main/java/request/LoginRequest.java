package request;
/** LoginRequest will pass in user credentials to login user
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */

public class LoginRequest {
    /**username for login*/
    private String userName;
    /** password for login*/
    private String password;

    /**Default Constructor
     */
    public LoginRequest(){
        userName = null;
        password = null;
    }
    /**Constructor that will set login credentials
     *@param usernameIn username value that is passed in
     *@param passwordIn password value that is passed in
     */
    public LoginRequest(String usernameIn, String passwordIn){
        userName = usernameIn;
        password = passwordIn;
    }
    public String getUsername(){
        return userName;
    }
    public String getPassword(){
        return password;
    }


}