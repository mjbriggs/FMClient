package model;
import java.io.Serializable;
import java.util.Random;

/** Event object modeling a row in the Event table.
 * Contains event information and the person involved in event
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */
public class Event implements Serializable {
    /** unique String for this event */
    private String eventID;
    /** user to which this person belongs */
    private String descendant;
    /** ID of person to which this event belongs */
    private String personID;
    /** Latitude of events location */
    private double latitude;
    /** Longitude of events location */
    private double longitude;
    /** Country in which event occured */
    private String country;
    /** city in which event occured */
    private String city;
    /** Type of event */
    private String eventType;
    /** year in which event occured */
    private int year;

    /** Default Constructor
     */
    public Event(){
        eventID = "";
        descendant = "";
        personID = "";
        latitude = 0.0;
        longitude = 0.0;
        country = "";
        city = "";
        eventType = "";
        year = 0;
    }
    /** Constructor that sets the object variables for this event
     * @param eventIDIn event ID to be set
     * @param descendantIn descendant to be set
     * @param personIDIn PersonID to be set
     * @param latitudeIn latitude to be set
     * @param longitudeIn longitude to be set
     * @param countryIn country to be set
     * @param cityIn city to be set
     * @param eventTypeIn type of event to be set
     * @param yearIn year to be set
     */
    public Event(String eventIDIn, String descendantIn, String personIDIn, double latitudeIn,
                 double longitudeIn, String countryIn, String cityIn, String eventTypeIn, int yearIn){
        eventID = eventIDIn;
        descendant = descendantIn;
        personID = personIDIn;
        latitude = latitudeIn;
        longitude = longitudeIn;
        country = countryIn;
        city = cityIn;
        eventType = eventTypeIn;
        year = yearIn;
    }
    public String getEventID(){
        return eventID;
    };
    public void setEventID(String eventIDIn){
        eventID = eventIDIn;
    };
    public String getDescendent(){
        return descendant;
    };
    public void setDescendent(String descendantIn){
        descendant = descendantIn;
    };
    public String getPersonID(){
        return personID;
    };
    public void setPersonID(String personIDIn){
        personID = personIDIn;
    };
    public double getLatitude(){
        return latitude;
    };
    public void setLatitude(double latitudeIn){
        latitude = latitudeIn;
    };
    public double getLongitude(){
        return longitude;
    };
    public void setLongitude(double longitudeIn){
        longitude = longitudeIn;
    };
    public String getCountry(){
        return country;
    };
    public void setCountry(String countryIn){
        country = countryIn;
    };
    public String getCity(){
        return city;
    };
    public void setCity(String cityIn){
        city = cityIn;
    };
    public String getEventType(){
        return eventType;
    };
    public void setEventType(String eventTypeIn){
        eventType = eventTypeIn;
    };
    public int getYear(){
        return year;
    };
    public void setYear(int yearIn){
        year = yearIn;
    };
    public String generateEventID(){
        int wordLength = 10;
        String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!_-+~";
        Random random = new Random();
        String eventOut = "";
        for(int i = 0; i < wordLength; i++){
            eventOut += availableChars.charAt(random.nextInt(availableChars.length()));
        }

        return eventOut;
    }
    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        else if(o instanceof Event){
            Event e = (Event) o;
            if(this.eventID.equals(e.getEventID()) &&
                    this.descendant.equals(e.getDescendent()) &&
                    this.personID.equals(e.getPersonID()) &&
                    this.latitude == e.getLatitude() &&
                    this.longitude == e.getLongitude() &&
                    this.country.equals(e.getCountry()) &&
                    this.city.equals(e.getCity()) &&
                    this.eventType.equals(e.getEventType()) &&
                    this.year == e.getYear()){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        return this.eventID + "; " +
                this.descendant + "; " +
                this.personID + "; " +
                this.latitude + "; " +
                this.longitude + "; " +
                this.country + "; " +
                this.city + "; " +
                this.eventType + "; " +
                this.year + "; ";
    }

}
