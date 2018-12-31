package result;
import java.util.ArrayList;

import model.Event;

/** EventService will either grab one event by ID or all events
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */

public class EventResult {
    /** event object for specified event */
    private Event event;
    /** array of all events */
    private ArrayList<Event> events;
    /** String containing output message*/
    private String message;
    /** boolean stating success of event request*/
    private boolean success;
    /** boolean stating if request is for one or all events*/
    private boolean singleEvent;
    /**ID of a specific event*/
    private String eventID;

    /** Default Constructor
     */
    public EventResult(){
        event = null;
        events = null;
        message = "";
        success = false;
        singleEvent = false;
        eventID = "";
    }
    /**Constructor that sets event to be returned
     * @param eventIn event to be returned
     */
    public EventResult(Event eventIn, String errorIn){
        events = null;
        success = false;
        singleEvent = true;
        eventID = "";
        event = eventIn;
        message = errorIn;
    }
    /**Constructor that sets events to be returned
     * @param eventsIn events to be returned
     */
    public EventResult(ArrayList<Event> eventsIn, String errorIn){
        event = null;
        success = false;
        singleEvent = false;
        eventID = "";
        events = eventsIn;
        message = errorIn;
    }
    public Event getOneEvent(){
        return event;
    }
    public ArrayList<Event> getAllEvents(){
        return events;
    }
    public void setMessage(String message){
        this.message = message;
    }
    /** returns a description of error
     * @return String description of error
     */
    public String errorMessage(){
        return message;
    }
    /** represents whether or not fill was successful
     * @return boolean stating that fill was successful
     */
    public boolean success(){
        return success;
    }


}
