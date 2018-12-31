package proxy;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.AuthToken;
import model.Event;
import request.EventRequest;
import resources.Global;
import result.EventResult;
import michaelbriggs.familymapclient.Model;

public class EventProxy {

    private static Gson gson;
    private static EventResult sEventResult;
   // private static EventRequest sEventRequest;

    public EventProxy(){
        gson = new Gson();
        sEventResult = new EventResult();
     //   sEventRequest = new EventRequest();
    }

    public static void requestEvent(String serverHost, String serverPort, EventRequest eventRequest, AuthToken token) {

        String responseMessage = "login failed";
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            Log.d("CONNECTION INFO", serverHost + ":" + serverPort);
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event/" + eventRequest.getEventID());

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");

            http.addRequestProperty("Authorization", token.getToken());

            http.setDoOutput(false);

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                Log.d(Global.JSON_RESPONSE, respData);
                // System.out.println(respData);

                String errorMessage = "";

                if(eventRequest.getEventID().length() == 0){

                    ArrayList<Event> eventArrayList =  new ArrayList<Event>();
                    Type personArrayListType = new TypeToken<ArrayList<Event>>() {}.getType();

                    try{
                        Model model = Model.getInstance();
                        eventArrayList = gson.fromJson(respData, personArrayListType);
                        model.setUserEvents(eventArrayList);
                        //model.cleanUpEventLocationNames();
                       // Log.i(Global.SUCCESS, model.getUserEvents().toString());
                        //findUserPerson();
                        // sPersonResult = new PersonResult(personArrayList,"");
                        // singleton = Singleton.getInstance();
                        //sEventResult = new EventResult(singleton.getUserPerson(), "");
                        responseMessage = sEventResult.toString(); // change
                    }
                    catch (JsonSyntaxException ex){
                        Log.d(Global.EXCEPTION, ex.getMessage());
                        //errorMessage = gson.fromJson(respData, String.class);
                        //sEventResult = new EventResult(eventArrayList, errorMessage);
                    }
                }
                else{

                   /* Person person = new Person();

                    try{
                        person = gson.fromJson(respData, Person.class);
                        sEventResult = new PersonResult(person,"");
                        responseMessage = sEventResult.toString(); // change
                    }
                    catch (JsonSyntaxException ex){
                        Log.d(Global.EXCEPTION, ex.getMessage());
                        errorMessage = gson.fromJson(respData, String.class);
                        sEventResult = new PersonResult(person, errorMessage);
                    }*/
                   Log.d(Global.ERROR, "Tried to get one event");
                }




            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                Log.d(Global.ERROR, http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
       // return sEventResult;
    }

    /*
		The readString method shows how to read a String from an InputStream.
	*/
    @NonNull
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
    private static void findUserPerson(){
        Singleton singleton = Singleton.getInstance();
        Person tmpPerson = null;
        if(singleton.getPersonID().length() != 0){
            ArrayList<Person> persons = singleton.getUserPersons();
            // for(Person person : singleton.getUserPersons()){
            for(int i = 0; i < persons.size(); i++){
                tmpPerson = (Person) persons.get(i);
                if(tmpPerson.getPersonID().equals(singleton.getPersonID())){
                    singleton.setUserPerson(tmpPerson);
                    return;
                }
            }
        }
        else{
            Log.d(Global.ERROR, "singeton.getPersonId().length() == 0");
        }
    }
    */

}
