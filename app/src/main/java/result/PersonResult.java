package result;
import model.Person;
import java.util.ArrayList;
/** PersonResult will either contain information for one person or all persons
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */

public class PersonResult {
    /**person to be returned*/
    private Person person;
    /**persons to be returned*/
    private ArrayList<Person> persons;
    /**output message*/
    private String message;
    /**states the success of the request*/
    private boolean success;
    /**states whether a single person was searched*/
    private boolean singlePerson;

    /*Default Constructor
     */
    public PersonResult(){
        person = null;
        persons = new ArrayList<>();
        message = "";
        success = false;
        singlePerson = false;

    }
    /**Constructor that will set requested person
     * @param personIn person that was requested
     * @param messageIn message describing person service action
     */
    public PersonResult(Person personIn, String messageIn){
        person = personIn;
        persons = null;
        message = messageIn;
        success = false;
        singlePerson = true;
    }
    /**Constructor that will set all persons to be returned
     * @param personsIn person that was requested
     * @param messageIn message describing person service action
     */
    public PersonResult(ArrayList<Person> personsIn, String messageIn){
        person = null;
        persons = personsIn;
        message = messageIn;
        success = false;
        singlePerson = false;
    }
    public Person getOnePerson(){
        return person;
    }
    public ArrayList<Person> getAllPersons(){
        return persons;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getPersonID(){
        return person.getPersonID();
    }

    public boolean isSinglePerson() {
        return singlePerson;
    }

    /** will create error errorMessage
     *@return String of errorMessage
     */
    public String errorMessage(){
        return message;
    }
    public boolean success(){
        if(message == null || message.length() == 0)
            success = true;
        return success;
    }


}