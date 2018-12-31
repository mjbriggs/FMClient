package result;
import model.AuthToken;
import model.User;
import request.LoginRequest;
/** LoginResult will contain AuthToken of successful login
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */

public class LoginResult {
    /** resulting AuthToken object from successful login*/
    private String authToken;
    /** Resulting user object from successful login */
    private String userName;
    /** error message from an unsuccesful login attempt*/
    private String personID;

    /**Default Constructor
     */
    public LoginResult(){
        authToken = "";
        userName = "";
        personID = ""; // default error message
    }
    /**Constructor that will set AuthToken based on username
     * @param tokenIn AuthToken of logged In
     * @param userIn User that logged in
     * @param errorMessage String that is desribing error that occured
     */
    public LoginResult(AuthToken tokenIn, User userIn, String errorMessage){
        if(tokenIn != null && userIn != null){
            authToken = tokenIn.getToken();
            userName = userIn.getUsername();
            personID = userIn.getPersonID();
        }
        else{
            authToken = "";
            userName = "";
            personID = "";
        }

    }
    public String getLoginAuthToken(){
        return authToken;
    }
    public String getLoginUser(){
        return userName;
    }
    public String getLoginPersonID(){
        return personID;
    }
    /**Returns an message describing error that occured
     *@return String of errorMessage
     */
    public String errorMessage(){
        if(authToken.length() == 0 || userName.length() == 0 || personID.length() == 0)
            return "Request property missing or has invalid value";
        return "";
    }
    @Override
    public String toString(){
        return "authToken:" + authToken +
                "\nuserName:" + userName +
                "\npersonID:" + personID;
    }

}
