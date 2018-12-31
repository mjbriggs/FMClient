package michaelbriggs.familymapclient;

import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AuthToken;
import model.Event;
import model.Person;
import model.Relative;
import model.User;
import proxy.ClearProxy;
import proxy.EventProxy;
import proxy.LoginProxy;
import proxy.PersonProxy;
import proxy.RegisterProxy;
import request.EventRequest;
import request.LoginRequest;
import request.PersonRequest;
import request.RegisterRequest;
import result.EventResult;
import result.LoginResult;
import result.PersonResult;
import result.RegisterResult;

import static org.junit.Assert.*;

public class ModelTest {
    private EventProxy mEventProxy;
    private EventRequest mEventRequest;
    private PersonProxy mPersonProxy;
    private PersonRequest mPersonRequest;
    private PersonResult mPersonResult;
    private RegisterProxy mRegisterProxy;
    private RegisterRequest mRegisterRequest;
    private RegisterResult mRegisterResult;
    private ClearProxy mClearProxy;
    private User mUser;
    private String mServerHost;
    private String mServerPort;
    private AuthToken mAuthToken;
    private Model mModel;

    @Before
    public void setUp(){
        mServerHost = "localhost";
        mServerPort = "8080";

        mClearProxy = new ClearProxy();
        mClearProxy.clear(mServerHost, mServerPort);

        mUser = new User("user8", "password8", "user@8mail.com",
                "use", "r8", "f", "");

        mRegisterRequest = new RegisterRequest(mUser);
        mRegisterProxy = new RegisterProxy();
        mRegisterResult = mRegisterProxy.register(mServerHost, mServerPort, mRegisterRequest);

        mAuthToken = new AuthToken(mRegisterResult.getUser(), mRegisterResult.getAuthToken());
        System.out.println("AuthToken : " + mAuthToken.toString());

        mPersonRequest = new PersonRequest("");
        mPersonResult = null;
        mPersonProxy = new PersonProxy();
        mPersonResult = mPersonProxy.requestPerson(mServerHost, mServerPort, mPersonRequest, mAuthToken);

        mEventProxy = new EventProxy();
        mEventRequest = new EventRequest("");
        mEventProxy.requestEvent(mServerHost, mServerPort, mEventRequest, mAuthToken);

        mModel = Model.getInstance();
        mModel.setPersonID(mRegisterResult.getPersonID());
    }

    @Test
    public void testSortEventsByPerson(){
        if (mModel.getUserEvents().size() > 0 && mModel.getUserPersons().size() > 0){
            mModel.sortEventsByPerson();
            Map<String, List<Event>> personIDEventHashMap = mModel.getPersonEvents();
            assertTrue(personIDEventHashMap.size() == 31);
            for(String personID : personIDEventHashMap.keySet()){
                if(personID.equals(mModel.getPersonID())){
                    assertTrue(personIDEventHashMap.get(personID).size() == 2);
                    for(Event event : personIDEventHashMap.get(personID)){
                        System.out.println(event.toString());
                        assertTrue(personID.equals(event.getPersonID()));
                    }
                }
                else {
                    for(Event event : personIDEventHashMap.get(personID)){
                        System.out.println(event.toString());
                        assertTrue(personID.equals(event.getPersonID()));
                    }
                    System.out.println("Size of list for " + personID + " == " + personIDEventHashMap.get(personID).size());
                    assertTrue(personIDEventHashMap.get(personID).size() == 3);
                }
            }
        }
        else{
            assertTrue(false);
        }
    }

    @Test
    public void testCreateFamilyMap() {
        if (mModel.getUserEvents().size() > 0 && mModel.getUserPersons().size() > 0) {
            mModel.createFamilyMap();
            Map<String, List<Relative>> familyMap = mModel.getFamilyMap();
            ArrayList<Person> userPersons = mModel.getUserPersons();
            for(Person person : userPersons){
                for(Relative relative : familyMap.get(person.getPersonID())){
                    assertTrue(relative.getRelative().getDescendent().equals(person.getDescendent()));
                    assertTrue(relative.getRelationship().equals("Mother") || relative.getRelationship().equals("Father")
                    || relative.getRelationship().equals("Spouse") || relative.getRelationship().equals("Child"));
                }
            }

        }
        else {
            assertTrue(false);
        }
    }

    @Test
    public void testValidPersonSearch() {
        if (mModel.getUserPersons().size() > 0) {
            String query = "r8";
            List<Person> searchResults = mModel.searchPersons(query);
            assertTrue(searchResults.size() == 31);

            query = "r8";
            searchResults = mModel.searchPersons(query);
            assertTrue(searchResults.size() == 31);
        }
        else {
            assertTrue(false);
        }
    }
    @Test
    public void testInValidPersonSearch() {
        if (mModel.getUserPersons().size() > 0) {
            String query = "@$#52634125";
            List<Person> searchResults = mModel.searchPersons(query);
            assertTrue(searchResults.size() == 0);
        }
        else {
            assertTrue(false);
        }
    }

    @Test
    public void testValidEventSearch() {
        if (mModel.getUserEvents().size() > 0) {
            String query = "1";
            List<Event> searchResults = mModel.searchEvents(query);
            assertTrue(searchResults.size() > 0);

            query = "a";
            searchResults = mModel.searchEvents(query);
            assertTrue(searchResults.size() > 0);
        }
        else {
            assertTrue(false);
        }
    }
    @Test
    public void testInValidEventSearch() {
        if (mModel.getUserEvents().size() > 0) {
            String query = "9999999";
            List<Event> searchResults = mModel.searchEvents(query);
            assertTrue(searchResults.size() == 0);
        }
        else {
            assertTrue(false);
        }
    }

    @Test
    public void testValidSearchByEventID(){
        for(Event event : mModel.getUserEvents()){
            assertTrue(mModel.findEventByID(event.getEventID()).getEventID().equals(event.getEventID()));
        }
    }

    @Test
    public void testInValidSearchByEventID(){
        assertTrue(mModel.findEventByID("").getEventID().equals(""));
        assertTrue(mModel.findEventByID("(*@@(#!@)&#)!(@&#)@)(").getEventID().equals(""));
        assertTrue(mModel.findEventByID("   ").getEventID().equals(""));
    }
    @Test

    public void testValidSearchByPersonID(){
        for(Person person : mModel.getUserPersons()){
            assertTrue(mModel.findPersonByID(person.getPersonID()).getPersonID().equals(person.getPersonID()));
        }
    }
    @Test
    public void testInValidSearchByPersonID(){
        assertTrue(mModel.findPersonByID("").getPersonID().equals(""));
        assertTrue(mModel.findPersonByID("(*@@(#!@)&#)!(@&#)@)(").getPersonID().equals(""));
        assertTrue(mModel.findPersonByID("   ").getPersonID().equals(""));
    }

    @Test
    public void testSortEventsByDate(){
        List<Event> testEvents = new ArrayList<>();
        Event event = new Event("1", "user", "user", 0, 0,
                "country", "city","death" , 1980);
        testEvents.add(event);

        Event event2 = new Event("1", "user", "user", 0, 0,
                "country", "city","birth" , 1900);
        testEvents.add(event2);

        Event event3 = new Event("1", "user", "user", 0, 0,
                "country", "city","big event" , 1930);
        testEvents.add(event3);

        System.out.println(testEvents.toString());
        List<Event> sortedEvents = new ArrayList<Event>();

        sortedEvents = mModel.sortEventsByDate(testEvents);
        int testLength = sortedEvents.size() - 1;
        for(int i = 0; i < testLength; i++){

            Event tmp1 = sortedEvents.get(i);
            Event tmp2 = sortedEvents.get(i + 1);
            assertTrue(tmp1.getYear() < tmp2.getYear() || tmp1.getEventType().compareTo(tmp2.getEventType()) <= 0);
        }
        System.out.println("\n\n\n");

        Event event4 = new Event("1", "user", "user", 0, 0,
                "country", "city","another event" , 1930);
        testEvents.add(event4);

        sortedEvents = mModel.sortEventsByDate(testEvents);
        for(int i = 0; i < testLength; i++){
            Event tmp1 = sortedEvents.get(i);
            Event tmp2 = sortedEvents.get(i + 1);
            assertTrue(tmp1.getYear() < tmp2.getYear() || tmp1.getEventType().compareTo(tmp2.getEventType()) <= 0);
        }

    }

    @After
    public void tearDown(){
        assertEquals(mClearProxy.clear(mServerHost, mServerPort), "\"Clear succeeded\"");
        mModel.killInstance();
    }

}
