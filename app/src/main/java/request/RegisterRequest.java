package request;
import model.User;
/** RegisterRequest will contain user information to be registered
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */
public class RegisterRequest {
    /** user to be registered */
    private User user;

    /** Default Constructor
     */
    public RegisterRequest(){
        user = null;
    }
    /** Constructor that sets user to be registered
     * @param userIn user to be registered
     */
    public RegisterRequest(User userIn){
        user = userIn;
    }
    public User getUser(){
        return user;
    }

}
