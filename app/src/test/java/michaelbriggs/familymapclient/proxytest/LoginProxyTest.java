package michaelbriggs.familymapclient.proxytest;

import org.junit.*;

import michaelbriggs.familymapclient.Model;
import model.Person;
import model.User;
import proxy.ClearProxy;
import proxy.LoginProxy;
import proxy.RegisterProxy;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;

import static org.junit.Assert.*;

public class LoginProxyTest {
    private LoginProxy mLoginProxy;
    private RegisterProxy mRegisterProxy;
    private RegisterRequest mRegisterRequest;
    private ClearProxy mClearProxy;
    private String mServerHost;
    private String mServerPort;
    private User mUser;
    private LoginRequest mLoginRequest;
    private LoginResult mLoginResult;

   @Before
    public void setUp(){
       mServerHost = "localhost";
       mServerPort = "8080";

       mClearProxy = new ClearProxy();
       mClearProxy.clear(mServerHost, mServerPort);

       mUser = new User("user8", "password8", "user@8mail.com",
               "use", "r8", "f", "");
       Person personIDgenerator = new Person();
       mUser.setPersonID(personIDgenerator.generatePersonID());
       mRegisterRequest = new RegisterRequest(mUser);
       mRegisterProxy = new RegisterProxy();
       mRegisterProxy.register(mServerHost, mServerPort, mRegisterRequest);

       mLoginProxy = new LoginProxy();
       mLoginRequest = null;
       mLoginResult = null;
   }
   @Test
    public void testValidlogin(){
        mLoginRequest = new LoginRequest(mUser.getUsername(), mUser.getPassword());
        mLoginResult = mLoginProxy.login(mServerHost, mServerPort, mLoginRequest);
        assertTrue(mLoginResult.errorMessage().length() == 0);
   }
    @Test
    public void testInvalidlogin(){
        mLoginRequest = new LoginRequest(mUser.getUsername(), "");
        mLoginResult = mLoginProxy.login(mServerHost, mServerPort, mLoginRequest);
        assertTrue(mLoginResult.errorMessage().length() != 0);
    }
    @After
    public void tearDown(){
        assertEquals(mClearProxy.clear(mServerHost, mServerPort), "\"Clear succeeded\"");
        Model model = Model.getInstance();
        model.killInstance();
    }
}