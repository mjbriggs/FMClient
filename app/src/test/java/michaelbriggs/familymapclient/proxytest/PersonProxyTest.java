package michaelbriggs.familymapclient.proxytest;

import org.junit.*;

import michaelbriggs.familymapclient.Model;
import model.AuthToken;
import model.Person;
import model.User;
import proxy.ClearProxy;
import proxy.PersonProxy;
import proxy.RegisterProxy;
import request.PersonRequest;
import request.RegisterRequest;
import result.PersonResult;
import result.RegisterResult;

import static org.junit.Assert.*;


public class PersonProxyTest {
    private PersonProxy mPersonProxy;
    private PersonResult mPersonResult;
    private PersonRequest mPersonRequest;
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

        mPersonProxy = new PersonProxy();
        mPersonRequest = new PersonRequest();
        mPersonResult = null;

        mAuthToken = new AuthToken(mRegisterResult.getUser(), mRegisterResult.getAuthToken());
        System.out.println("AuthToken : " + mAuthToken.toString());

        mModel = Model.getInstance();
        mModel.setPersonID(mRegisterResult.getPersonID());

    }

    @Test
    public void testValidPersonProxy(){
        System.out.println("beginning to test valid person proxy");

        mPersonResult = mPersonProxy.requestPerson(mServerHost, mServerPort, mPersonRequest, mAuthToken);
        System.out.println("PersonID : " + mPersonResult.getPersonID());
        assertTrue(mPersonResult.getPersonID().equals(mRegisterResult.getPersonID()));
        assertTrue(mPersonResult.errorMessage().length() == 0);
        assertTrue(hasDescendent());
    }
    @Test
    public void testInvalidPersonProxy(){
        mPersonRequest = new PersonRequest(mUser.getPersonID() + "%");
        mPersonResult = mPersonProxy.requestPerson(mServerHost, mServerPort, mPersonRequest, mAuthToken);
        assertTrue(mPersonResult.getPersonID().length() == 0);
        assertTrue(mPersonResult.errorMessage().length() != 0);
        assertFalse(personExists(mUser.getPersonID() + "%"));


    }
    public boolean hasDescendent(){
        for(Person person : mModel.getUserPersons()){
            if(!person.getDescendent().equals(mUser.getUsername()))
                return false;
        }
        return true;
    }
    public boolean personExists(String personID){
        for(Person person : mModel.getUserPersons()){
            if(person.getPersonID().equals(personID))
                return true;
        }
        return false;
    }
    @After
    public void tearDown(){
        assertEquals(mClearProxy.clear(mServerHost, mServerPort), "\"Clear succeeded\"");
        mModel.killInstance();
    }

}
