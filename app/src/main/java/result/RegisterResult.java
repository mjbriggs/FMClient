package result;

import model.AuthToken;
import model.User;
//import dao.AuthTokenDAO;

/** RegisterResult will provide the user's AuthToken upon successful registration
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */
public class RegisterResult {
    /** resulting token from user registration*/
    private String authToken;
    /** resulting username from user registration*/
    private String userName;
    /**resulting personID from user registration */
    private String personID;
    /** message to describe potential error*/

    /** default Constructor
     */
    public RegisterResult(){
        authToken = "";
        userName = "";
        //errorMessage = "";
        personID = "";
    }
    /** Constructor that will set AuthToken based on username
     * @param userIn user that has been added to the database
     * @param error String describing error that occured
     */
    public RegisterResult(User userIn, AuthToken tokenIn, String error){
        //AuthTokenDAO tokenDAO = new AuthTokenDAO();
        if(userIn != null){
            userName = userIn.getUsername();
            personID = userIn.getPersonID();
           // AuthToken token = tokenDAO.getMostRecentAuthToken(userIn.getUsername());
            if(tokenIn != null)
                authToken = tokenIn.getToken();
            else{
                userName = error;
                authToken = "";
                personID = "";
            }
        }
        else  {
            userName = error;
            authToken = "";
            personID = "";
        }
    }
    public String getAuthToken(){
        return authToken;
    }
    public String getUser(){
        return userName;
    }
    public String getPersonID(){
        return personID;
    }
    public void setUserName(String userIn){
        userName = userIn;
    }
    /** Will provide description of error
     * @return String containing error description
     */
    public String errorMessage(){
        if(authToken.length() == 0 || userName.length() == 0 || personID.length() == 0)
            return userName; // userName is set to error if neccesary, format must remain the same to line up with server sJSON response
        return "";
    }


}
