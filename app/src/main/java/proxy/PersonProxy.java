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
import model.Person;
import request.PersonRequest;
import resources.Global;
import result.PersonResult;
import michaelbriggs.familymapclient.Model;

public class PersonProxy {

    private static Gson gson;
    private static PersonResult sPersonResult;
    private static PersonRequest sPersonRequest;

    public PersonProxy(){
        gson = new Gson();
        sPersonResult = new PersonResult();
        sPersonRequest = new PersonRequest();
    }

    public static PersonResult requestPerson(String serverHost, String serverPort, PersonRequest personRequest, AuthToken token) {

        String responseMessage = "login failed";
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            Log.d("CONNECTION INFO", serverHost + ":" + serverPort);
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/" + personRequest.getPersonID());

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

                if(personRequest.getPersonID().length() == 0){

                    ArrayList<Person> personArrayList =  new ArrayList<Person>();
                    Type personArrayListType = new TypeToken<ArrayList<Person>>() {}.getType();

                    try{
                        System.out.print("In Person Proxy\t");
                        Model model = Model.getInstance();
                        System.out.println("Person ID: " + model.getPersonID());
                        personArrayList = gson.fromJson(respData, personArrayListType);
                        model.setUserPersons(personArrayList);
                        findUserPerson();
                       // sPersonResult = new PersonResult(personArrayList,"");
                       // singleton = Singleton.getInstance();
                        sPersonResult = new PersonResult(model.getUserPerson(), "");
                        responseMessage = sPersonResult.toString(); // change
                    }
                    catch (JsonSyntaxException ex){
                        Log.d(Global.EXCEPTION, ex.getMessage());
                        errorMessage = gson.fromJson(respData, String.class);
                        sPersonResult = new PersonResult(personArrayList, errorMessage);
                        /*Model model = Model.getInstance();
                        model.setUserPersons(personArrayList);*/
                    }
                }
                else{

                    Person person = new Person();

                    try{
                        person = gson.fromJson(respData, Person.class);
                        sPersonResult = new PersonResult(person,"");
                        responseMessage = sPersonResult.toString(); // change
                    }
                    catch (JsonSyntaxException ex){
                        Log.d(Global.EXCEPTION, ex.getMessage());
                        errorMessage = gson.fromJson(respData, String.class);
                        sPersonResult = new PersonResult(person, errorMessage);
                    }
                }


            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                Log.d(Global.ERROR, http.getResponseMessage());
                Person nullPerson = new Person();
                sPersonResult = new PersonResult(nullPerson, http.getResponseMessage());
                /*Model model = Model.getInstance();
                model.setUserPersons(new ArrayList<Person>());*/
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return sPersonResult;
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

    private static void findUserPerson(){
        Model model = Model.getInstance();
        Person tmpPerson = null;
        if(model.getPersonID().length() != 0){
            ArrayList<Person> persons = model.getUserPersons();
           // for(Person person : singleton.getUserPersons()){
            for(int i = 0; i < persons.size(); i++){
                tmpPerson = (Person) persons.get(i);
                if(tmpPerson.getPersonID().equals(model.getPersonID())){
                    model.setUserPerson(tmpPerson);
                    return;
                }
            }
        }
        else{
            Log.d(Global.ERROR, "singeton.getPersonId().length() == 0");
        }
    }

}
