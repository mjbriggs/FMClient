package model;
/** authToken object modeling a row in the authToken table.
 * Contains user info and an authToken
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */
public class AuthToken {
    /** username String */
    private String username;
    /** random 8 character token String */
    private String token;

    /** Default Constructor
     */
    public AuthToken(){
        username = "";
        token = "";
    };
    /** Constructor that will set object variables
     *@param usernameIn sets username variable
     *@param tokenIn sets token variable
     */
    public AuthToken(String usernameIn, String tokenIn){
        username = usernameIn;
        token = tokenIn;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String usernameIn){
        username = usernameIn;
    }
    public String getToken(){
        return token;
    }
    public void setToken(String tokenIn){
        token = tokenIn;
    }
    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        else if(o instanceof AuthToken){
            AuthToken aT = (AuthToken) o;
            if(this.username.equals(aT.getUsername()) &&
                    this.token.equals(aT.getToken())){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        return this.username + "; " + this.token + "; ";
    }
}
