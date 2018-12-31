package proxy;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import michaelbriggs.familymapclient.Model;
import request.LoginRequest;
import result.LoginResult;
import resources.Global;

public class LoginProxy {
    // The getGameList method calls the server's "/games/list" operation to
    // retrieve a list of games running in the server in JSON format
    private static final String JSON_RESPONSE = "json response";
    private static final String ERROR = "error";
    private static Gson gson;
    private static LoginResult loginResult;

    public LoginProxy(){
        gson = new Gson();
        loginResult = new LoginResult();

    }

    public static LoginResult login(String serverHost, String serverPort, LoginRequest loginRequest) {

        // This method shows how to send a GET request to a server
        String responseMessage = "login failed";
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            Log.d("CONNECTION INFO", serverHost + ":" + serverPort);
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");
            //URL url = new URL("http://localhost:8080/user/login");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            // Specify that we are sending an HTTP GET request
            http.setRequestMethod("POST");
            // Indicate that this request will not contain an HTTP request body
            http.setDoOutput(true);

            // Add an auth token to the request in the HTTP "Authorization" header
            // http.addRequestProperty("Content-Type", "application/json");
            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
           // http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
             http.connect();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.

           /* String reqData =
                    "{" +
                            "\"username\": \"username\"\n" +
                            "\"password\": \"password\"" +
                            "}";*/
            String reqData = gson.toJson(loginRequest);

            // Get the output stream containing the HTTP request body
            OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
           /* OutputStreamWriter sw = new OutputStreamWriter(reqBody);
            sw.write(reqData);
            sw.flush();
            sw.close();*/
            reqBody.close();



            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                Log.d(JSON_RESPONSE, respData);
               // System.out.println(respData);

                try{
                    loginResult = gson.fromJson(respData, LoginResult.class);
                    //responseMessage = loginResult.toString();
                }
                catch (JsonSyntaxException ex){
                    loginResult = new LoginResult();
                    Log.d(Global.EXCEPTION, ex.getMessage() + "\nLogin response message : " + loginResult.errorMessage());
                    //loginResult. = gson.fromJson(respData, String.class);
                }

            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                Log.d(ERROR, http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return loginResult;
    }

    /*
		The readString method shows how to read a String from an InputStream.
	*/
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
		The writeString method shows how to write a String to an OutputStream.
	*/
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
        sw.close();
    }

}
