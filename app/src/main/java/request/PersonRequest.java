package request;
/** PersonRequest will contain personID to be searched, will specify if search is for one person or all persons
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */

public class PersonRequest {
    /** PersonID to be searched*/
    private String personID;

    /**Default Constructor
     */
    public PersonRequest(){
        personID = "";
    }
    /**Constructor that sets PersonID to be searched
     * @param personIDIn ID of person to be searched
     */
    public PersonRequest(String personIDIn){
        personID = personIDIn;
    }
    /** returns true for one person, false for all people
     *@return boolean value describing if there is one person to search for
     */
    public boolean gettingOnePerson(){
        if(personID == null)
            return false;
        else
            return true;
    }
    public String getPersonID(){
        return personID;
    }


}