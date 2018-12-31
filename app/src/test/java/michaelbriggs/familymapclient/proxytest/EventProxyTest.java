package michaelbriggs.familymapclient.proxytest;

import org.junit.*;

import michaelbriggs.familymapclient.Model;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import proxy.ClearProxy;
import proxy.EventProxy;
import proxy.LoginProxy;
import proxy.RegisterProxy;
import request.EventRequest;
import request.LoginRequest;
import request.PersonRequest;
import request.RegisterRequest;
import result.EventResult;
import result.LoginResult;
import result.RegisterResult;

import static org.junit.Assert.*;


public class EventProxyTest {
    private EventProxy mEventProxy;
    private EventResult mEventResult;
    private EventRequest mEventRequest;
    private RegisterProxy mRegisterProxy;
    private RegisterRequest mRegisterRequest;
    private RegisterResult mRegisterResult;
    private LoginProxy mLoginProxy;
    private LoginRequest mLoginRequest;
    private LoginResult mLoginResult;
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

        mLoginProxy = new LoginProxy();
        mLoginRequest = new LoginRequest();
        mLoginResult = null;

        mEventProxy = new EventProxy();
        mEventRequest = new EventRequest("");
        mEventResult = null;

        mAuthToken = new AuthToken(mRegisterResult.getUser(), mRegisterResult.getAuthToken());
        System.out.println("AuthToken : " + mAuthToken.toString());

        mModel = Model.getInstance();
        mModel.setPersonID(mRegisterResult.getPersonID());

    }

    @Test
    public void testValidEventProxy(){
        System.out.println("beginning to test valid event proxy");

        mEventProxy.requestEvent(mServerHost, mServerPort, mEventRequest, mAuthToken);
        assertTrue(hasDescendent(mUser));
        assertTrue(personEventExists());
    }
    @Test
    public void testInvalidEventProxy(){
        User badUser = new User("badUser", "password8", "user@8mail.com",
                "use", "r8", "f", "");
        mUser = badUser;
        mAuthToken = new AuthToken(mUser.getUsername(), mRegisterResult.getAuthToken() + "*&");
        mEventProxy.requestEvent(mServerHost, mServerPort, mEventRequest, mAuthToken);
        assertFalse(hasDescendent(badUser));
        assertFalse(personEventExists());


    }
    public boolean hasDescendent(User user){
        if(mModel.getUserEvents().size() == 0)
            return false;
        for(Event event : mModel.getUserEvents()){
            if(!event.getDescendent().equals(user.getUsername()))
                return false;
        }
        return true;
    }
    public boolean personEventExists(){
        int eventCount = 0;
        for(Event event : mModel.getUserEvents()){
            if(event.getDescendent().equals(mUser.getUsername())){
                eventCount++;
                if(eventCount >= 2){
                    return true;
                }
            }
        }
        return false;
    }
    @After
    public void tearDown(){
        assertEquals(mClearProxy.clear(mServerHost, mServerPort), "\"Clear succeeded\"");
        mModel.killInstance();
    }

}
