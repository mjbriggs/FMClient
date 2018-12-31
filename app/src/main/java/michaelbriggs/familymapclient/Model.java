package michaelbriggs.familymapclient;


import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Event;
import model.Person;
import model.Relative;
import model.User;
import resources.Global;

// Java program implementing Singleton class
// with getInstance() method
public class Model {

    // static variable single_instance of type Singleton
    private static Model single_instance = null;

    // variable of type String
    public String s;
    private ArrayList<Person> mUserPersons;
    private String mPersonID;
    private ArrayList<Event> mUserEvents;
    private String [] mEventsTypes;
    private Map<String, List<Event>> mEventMap;
    private Map<String, List<Event>> mPersonEventsMap;
    private Map<String, List<Person>> mMotherMap;
    private Map<String, List<Person>> mFatherMap;
    private Map<String, List<Relative>> mFamilyMap;
    private Person mUserPerson;
    private User mUser;
    private String mServerHost;
    private String mServerPort;
    private boolean mShowFamilyLine;
    private boolean mShowLifeLine;
    private boolean mShowSpouseLine;
    private int mLifeLineColor;
    private int mFamilyLineColor;
    private int mSpouseLineColor;
    private int mLifeLineColorIndex;
    private int mFamilyLineColorIndex;
    private int mSpouseLineColorIndex;
    private int mGoogleMapType;
    private int mGoogleMapTypeIndex;

    public int getGoogleMapTypeIndex() {
        return mGoogleMapTypeIndex;
    }

    public void setGoogleMapTypeIndex(int googleMapTypeIndex) {
        mGoogleMapTypeIndex = googleMapTypeIndex;
    }

    public int getGoogleMapType() {
        return mGoogleMapType;
    }

    public void setGoogleMapType(int googleMapType) {
        mGoogleMapType = googleMapType;
    }

    public int getSpouseLineColorIndex() {
        return mSpouseLineColorIndex;
    }

    public void setSpouseLineColorIndex(int spouseLineColorIndex) {
        mSpouseLineColorIndex = spouseLineColorIndex;
    }

    public int getFamilyLineColorIndex() {
        return mFamilyLineColorIndex;
    }

    public void setFamilyLineColorIndex(int familyLineColorIndex) {
        mFamilyLineColorIndex = familyLineColorIndex;
    }

    public int getLifeLineColorIndex() {
        return mLifeLineColorIndex;
    }

    public void setLifeLineColorIndex(int lifeLineColorIndex) {
        mLifeLineColorIndex = lifeLineColorIndex;
    }

    public int getSpouseLineColor() {
        return mSpouseLineColor;
    }

    public void setSpouseLineColor(int spouseLineColor) {
        mSpouseLineColor = spouseLineColor;
    }


    public int getFamilyLineColor() {
        return mFamilyLineColor;
    }

    public void setFamilyLineColor(int familyLineColor) {
        mFamilyLineColor = familyLineColor;
    }


    public int getLifeLineColor() {
        return mLifeLineColor;
    }

    public void setLifeLineColor(int lifeLineColor) {
        mLifeLineColor = lifeLineColor;
    }

    public boolean isShowSpouseLine() {
        return mShowSpouseLine;
    }

    public void setShowSpouseLine(boolean showSpouseLine) {
        mShowSpouseLine = showSpouseLine;
    }

    public boolean isShowLifeLine() {
        return mShowLifeLine;
    }

    public void setShowLifeLine(boolean showLifeLine) {
        mShowLifeLine = showLifeLine;
    }

    public boolean isShowFamilyLine() {
        return mShowFamilyLine;
    }

    public void setShowFamilyLine(boolean showFamilyLine) {
        mShowFamilyLine = showFamilyLine;
    }

    public String getServerHost() {
        return mServerHost;
    }

    public void setServerHost(String serverHost) {
        mServerHost = serverHost;
    }

    public String getServerPort() {
        return mServerPort;
    }

    public void setServerPort(String serverPort) {
        mServerPort = serverPort;
    }

    public Map<String, List<Relative>> getFamilyMap() {
        return mFamilyMap;
    }

    public void setFamilyMap(Map<String, List<Relative>> familyMap) {
        mFamilyMap = familyMap;
    }

    public Map<String, List<Event>> getPersonEvents() {
        return mPersonEventsMap;
    }

    public void setPersonEvents(Map<String,List<Event>> personEvents) {
        mPersonEventsMap = personEvents;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public ArrayList<Event> getUserEvents() {
        return mUserEvents;
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        mUserEvents = userEvents;
    }

    public Person getUserPerson() {
        return mUserPerson;
    }

    public void setUserPerson(Person userPerson) {
        mUserPerson = userPerson;
    }

    public String getPersonID() {
        return mPersonID;
    }

    public void setPersonID(String personID) {
        mPersonID = personID;
    }

    public void setUserPersons(ArrayList<Person> userPersons) {
        mUserPersons = userPersons;
    }

    public ArrayList<Person> getUserPersons() {
        return mUserPersons;
    }

    // private constructor restricted to this class itself
    private Model() {
        s = "Hello I am a string part of Singleton class";
        mUserPersons = new ArrayList<>();
        mPersonID = "";
        mUserPerson = new Person();
        mUserEvents = new ArrayList<>();
        mEventsTypes = new String[] {"birth", "marriage", "death"};
        mEventMap = new HashMap<>();
        mMotherMap = new HashMap<>();
        mFatherMap = new HashMap<>();
        mFamilyMap = new HashMap<>();
        mPersonEventsMap = new HashMap<>();
        mShowLifeLine = true;
        mShowFamilyLine = true;
        mShowSpouseLine = true;
        mLifeLineColor = Color.GREEN;
        mFamilyLineColor = Color.BLACK;
        mSpouseLineColor = Color.RED;
        mLifeLineColorIndex = 0;
        mSpouseLineColorIndex = 0;
        mFamilyLineColorIndex = 0;
        mGoogleMapType = GoogleMap.MAP_TYPE_NORMAL;
        mGoogleMapTypeIndex = 0;

    }

    // static method to create instance of Singleton class
    public static Model getInstance() {
        if (single_instance == null) {
            single_instance = new Model();
        }

        return single_instance;
    }

    // static method to destroy current instance of Singleton class
    public static void killInstance() {
        if(single_instance != null)
            single_instance = null;
    }

    public void sortEventsByPerson(){
        if(mUserEvents.size() > 0 && mUserPersons.size() > 0){
            for(Event event : mUserEvents){
                List<Event> events = new ArrayList<>();
                    if(mPersonEventsMap.get(event.getPersonID()) == null){
                        events.add(event);
                        mPersonEventsMap.put(event.getPersonID(), events);
                    }
                    else{
                        events = mPersonEventsMap.get(event.getPersonID());
                        events.add(event);
                        mPersonEventsMap.put(event.getPersonID(), mPersonEventsMap.get(event.getPersonID()));
                    }
            }
        }
        for(String eventKey : mPersonEventsMap.keySet()){
            List<Event> eventList = mPersonEventsMap.get(eventKey);
            mPersonEventsMap.put(eventKey, sortEventsByDate(eventList));
        }
    }

    public void createFamilyMap(){
        if(mUserEvents.size() > 0 && mUserPersons.size() > 0) {
            String [] firstAndLastName;
            for (Person person : mUserPersons){
                ArrayList<Relative> relatives = new ArrayList<>();
                Person father = null;
                ArrayList<Relative> fatherChild = new ArrayList<>();
                Person mother = null;
                ArrayList<Relative> motherChild = new ArrayList<>();
                Relative relativeToAdd;
                if(person.getMother().length() > 0){
                    firstAndLastName = person.getMother().split("_");
                    Person personToAdd = findPerson(person.getDescendent(), firstAndLastName[0], firstAndLastName[1]);
                    relativeToAdd = new Relative(personToAdd, "Mother");
                    relatives.add(relativeToAdd);

                    mother = personToAdd;
                    relativeToAdd = new Relative(person, "Child");
                    motherChild.add(relativeToAdd);
                    mFamilyMap.put(mother.getPersonID(), motherChild);

                }
                if(person.getFather().length() > 0){
                    firstAndLastName = person.getFather().split("_");
                    Person personToAdd = findPerson(person.getDescendent(), firstAndLastName[0], firstAndLastName[1]);
                    relativeToAdd = new Relative(personToAdd, "Father");
                    relatives.add(relativeToAdd);

                    father = personToAdd;
                    relativeToAdd = new Relative(person, "Child");
                    fatherChild.add(relativeToAdd);
                    mFamilyMap.put(father.getPersonID(), fatherChild);
                }
                if(person.getSpouse().length() > 0){
                    firstAndLastName = person.getSpouse().split("_");
                    Person personToAdd = findPerson(person.getDescendent(), firstAndLastName[0], firstAndLastName[1]);
                    relativeToAdd = new Relative(personToAdd, "Spouse");
                    relatives.add(relativeToAdd);
                }
                if(mFamilyMap.get(person.getPersonID()) == null){
                    mFamilyMap.put(person.getPersonID(), relatives);
                }
                else{
                    List<Relative> currentRelatives = mFamilyMap.get(person.getPersonID());
                    for (Relative newRelative : relatives){
                        currentRelatives.add(newRelative);
                    }
                    mFamilyMap.put(person.getPersonID(), currentRelatives);
                }
            }
        }
    }

    public List<Person> searchPersons(String queryIn){
        queryIn = queryIn.toLowerCase();
        String [] queries = queryIn.split(" ");
        List<Person> searchResults = new ArrayList<>();
        for(String  query: queries){
            if(mUserPersons.size() > 0  && query.length() > 0){
                for(Person person : mUserPersons){
                    if(person.getFirstName().toLowerCase().contains(query) ||
                            person.getLastName().toLowerCase().contains(query)){
                        if(!searchResults.contains(person))
                            searchResults.add(person);
                    }
                }
            }
        }
        return searchResults;
    }

    public List<Event> searchEvents(String queryIn){
        List<Event> searchResults = new ArrayList<>();
        queryIn = queryIn.toLowerCase();
        String [] queries = queryIn.split(" ");
        for(String query : queries){
            if(mUserEvents.size() > 0 && query.length() > 0){
                for(Event event : mUserEvents){
                    String year = Integer.toString(event.getYear()).toLowerCase();
                    if(event.getCity().toLowerCase().contains(query) ||
                            event.getCountry().toLowerCase().contains(query) ||
                            year.contains(query)){
                        System.out.println("Matches query");
                        if(!searchResults.contains(event))
                            searchResults.add(event);
                    }
                }
            }
        }
        return searchResults;
    }

    public Person findPerson(String descendent, String firstName, String lastName){
        if(mUserPersons.size() > 0){
            for(Person person: mUserPersons){
                if(person.getDescendent().equals(descendent) &&
                        person.getFirstName().equals(firstName) &&
                        person.getLastName().equals(lastName)){
                    return person;
                }
            }
        }
        return new Person();
    }

    public Person findPersonByID(String personID){
        for(Person person : mUserPersons){
            if(person.getPersonID().equals(personID))
                return person;
        }
        return new Person();
    }

    public Event findEventByID(String eventID){
        for(Event event : mUserEvents){
            if(event.getEventID().equals(eventID))
                return event;
        }
        return new Event();
    }

    public String cleanUpName(String location){
            for(int i = 1; i < location.length(); i++){
                if(Character.isUpperCase(location.charAt(i))){
                    location = location.substring(0, i) + " " + location.substring(i);
                    Log.d("CLEANING", location);
                    return location;
                }
            }
          return location;
    }

    public boolean correctData(){
        for(Person person : mUserPersons){
            if(!person.getDescendent().equals(mUserPerson.getDescendent())){
                return false;
            }
        }
        for (Event event : mUserEvents){
            if(!event.getDescendent().equals(mUserPerson.getDescendent())){
                return false;
            }
        }
        return true;
    }

    public List<Event> sortEventsByDate(List<Event> events){

        int sortLength = events.size() - 1;
        boolean sorted = false;
        int count = 0;
        while(!sorted && count < 6){

            sorted = true;
            Event event1;
            Event event2;
            for(int i = 0; i < sortLength; i++){
                System.out.println(events.toString());
                event1 = events.get(i);
                event2 = events.get(i + 1);

                if(event1.getEventType().toLowerCase().equals("birth") && i > 0){
                    event2 = events.get(0);
                    events.set(0, event1);
                    events.set(i, event2);
                    System.out.println("eventType is birth and i > 0");
                    sorted = false;
                }
                else if(event1.getEventType().toLowerCase().equals("death") && i < sortLength){
                    event2 = events.get(sortLength);
                    events.set(sortLength, event1);
                    events.set(i, event2);
                    System.out.println("eventType is death and i < sortLength");
                    sorted = false;
                }
                else if(event1.getYear() > event2.getYear()){
                    events.set(i, event2);
                    events.set(i + 1, event1);
                    System.out.println("event 1 year > event 2 year");
                    sorted = false;
                }
                else if(event1.getYear() == event2.getYear()){
                    String eventType1 = event1.getEventType().toLowerCase();
                    String eventType2 = event2.getEventType().toLowerCase();

                    if(eventType1.compareTo(eventType2) > 0){
                        System.out.println("event 1 type compare to event 2 type > 0");
                        events.set(i, event2);
                        events.set(i + 1, event1);
                        sorted = false;
                    }
                }
            }
            count++;
        }
        return events;
    }
}