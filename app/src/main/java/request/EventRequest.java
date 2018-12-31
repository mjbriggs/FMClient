package request;
/** EventRequest will specify request for one event or all events
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */

public class EventRequest {
    /**ID that will specifiy a single event, if empty we will get all events*/
    private String eventID;


    /** Default Constructor
     */
    public EventRequest(){
        eventID = null;
    }
    /** Constructor that specifies eventID
     * @param eventIDIn ID of event to be searched
     */
    public EventRequest(String eventIDIn){
        eventID = eventIDIn;
    }
    /** will return true or false based on the number of events we will grab
     * @return returns true for one event, false for all events
     */
    public boolean returningOneEvent(){
        return eventID != null;
    }
    public String getEventID(){
        return eventID;
    }


}
