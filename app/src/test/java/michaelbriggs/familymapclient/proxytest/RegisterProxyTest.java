package michaelbriggs.familymapclient.proxytest;

import org.junit.*;

import michaelbriggs.familymapclient.Model;
import model.Person;
import model.User;
import proxy.ClearProxy;
import proxy.RegisterProxy;
import request.RegisterRequest;
import result.RegisterResult;

import static org.junit.Assert.*;

//run serverSide tests before this test

public class RegisterProxyTest {
    private RegisterProxy mRegisterProxy;
    private String mServerHost;
    private String mServerPort;
    private User mUser;
    private RegisterRequest mRegisterRequest;
    private RegisterResult mRegisterResult;
    private ClearProxy mClearProxy;

    @Before
    public void setUp(){
        mRegisterProxy = new RegisterProxy();
        mUser = new User("user8", "password8", "user@8mail.com",
                "use", "r8", "f", "");
        Person personIDgenerator = new Person();
        mUser.setPersonID(personIDgenerator.generatePersonID());
        mServerHost = "localhost";
        mServerPort = "8080";
        mRegisterRequest = new RegisterRequest(mUser);
        mRegisterResult = null;

        mClearProxy = new ClearProxy();
        assertEquals(mClearProxy.clear(mServerHost, mServerPort), "\"Clear succeeded\"");
    }
    @Test
    public void testRegister(){
       mRegisterResult = mRegisterProxy.register(mServerHost, mServerPort, mRegisterRequest);
       assertEquals(mRegisterResult.errorMessage(),"");
       this.testInvalidRegister();
    }

    public void testInvalidRegister(){
        mRegisterResult = mRegisterProxy.register(mServerHost, mServerPort, mRegisterRequest);
        assertTrue(mRegisterResult.errorMessage().length() > 0);
    }

    @After
    public void tearDown(){
        assertEquals(mClearProxy.clear(mServerHost, mServerPort), "\"Clear succeeded\"");
        Model model = Model.getInstance();
        model.killInstance();
    }


}
