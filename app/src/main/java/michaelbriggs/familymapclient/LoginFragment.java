package michaelbriggs.familymapclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import model.AuthToken;
import model.Person;
import model.User;
import proxy.EventProxy;
import proxy.LoginProxy;
import proxy.PersonProxy;
import proxy.RegisterProxy;
import request.EventRequest;
import request.LoginRequest;
import request.PersonRequest;
import request.RegisterRequest;
import resources.Global;
import result.EventResult;
import result.LoginResult;
import result.PersonResult;
import result.RegisterResult;

public class LoginFragment extends Fragment {
    private Button mLoginButton;
    private Button mRegisterButton;
    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    private EditText mServerHost;
    private EditText mServerPort;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private String mGender;
    private User mUser;
    private LoginProxy proxy;
    private RegisterProxy mRegisterProxy;
    private String mResponseMessage;
    private Model mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        proxy = new LoginProxy();
        mRegisterProxy = new RegisterProxy();

        mServerHost = view.findViewById(R.id.serverHostText);
        mServerPort =  view.findViewById(R.id.serverPortText);
        mUserName = view.findViewById(R.id.userNameText);
        mPassword = view.findViewById(R.id.passwordText);
        mEmail = view.findViewById(R.id.emailText);
        mFirstName = view.findViewById(R.id.firstNameText);
        mLastName = view.findViewById(R.id.lastNameText);
        mGender = "";
        mUser = null;

        mServerHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginButton.setEnabled(canLogin());
                mRegisterButton.setEnabled(canRegister());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mServerPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginButton.setEnabled(canLogin());
                mRegisterButton.setEnabled(canRegister());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginButton.setEnabled(canLogin());
                mRegisterButton.setEnabled(canRegister());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginButton.setEnabled(canLogin());
                mRegisterButton.setEnabled(canRegister());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterButton.setEnabled(canRegister());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterButton.setEnabled(canRegister());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterButton.setEnabled(canRegister());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLoginButton = (Button) view.findViewById(R.id.login_button);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                String [] responseParams = {mServerHost.getText().toString(), mServerPort.getText().toString(),
                mUserName.getText().toString(), mPassword.getText().toString()};
                new LoginTask().execute(responseParams);
            }
        });

        mRegisterButton = (Button) view.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                String [] responseParams = {mServerHost.getText().toString(), mServerPort.getText().toString()};
                new RegisterTask().execute(responseParams);
            }
        });

        mMaleRadioButton = (RadioButton) view.findViewById(R.id.male_radio_button);
        mMaleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender = "m";
                mRegisterButton.setEnabled(canRegister());
            }
        });

        mFemaleRadioButton = (RadioButton) view.findViewById(R.id.female_radio_button);
        mFemaleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender = "f";
                mRegisterButton.setEnabled(canRegister());
            }
        });
        return view;
    }

    private void createUser(){
        mUser = new User(mUserName.getText().toString(), mPassword.getText().toString(), mEmail.getText().toString(), mFirstName.getText().toString()
                ,mLastName.getText().toString(), mGender ,"");
        Person personIDGenerator = new Person();
        personIDGenerator.setFirstName(mUser.getFirstName());
        personIDGenerator.setLastName(mUser.getLastName());
        mUser.setPersonID(personIDGenerator.generatePersonID());
    }

    private boolean canLogin(){
        if(canConnectToServer() && mUserName.getText().toString().length() > 0 &&
                mPassword.getText().toString().length() > 0){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean canRegister(){
        if(mFirstName.getText().toString().length() > 0
                && mLastName.getText().toString().length() > 0
                && mEmail.getText().toString().length() > 0
                && mGender.length() > 0 && canLogin()){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean canConnectToServer(){
        if(mServerHost.getText().toString().length() > 0
                && mServerPort.getText().toString().length() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public class RegisterTask extends AsyncTask<String, Void, RegisterResult>{

        RegisterResult mRegisterResult = new RegisterResult();

        @Override
        protected RegisterResult doInBackground(String... strings) {
            if(mUser != null){
                RegisterRequest mRegisterRequest = new RegisterRequest(mUser);
                mRegisterResult = mRegisterProxy.register(strings[0], strings[1], mRegisterRequest);
                return mRegisterResult;
            }
            else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(RegisterResult registerResult) {
            super.onPostExecute(registerResult);
            if(registerResult != null && registerResult.errorMessage().length() == 0){
                Global.sAuthToken = new AuthToken(mRegisterResult.getUser(), mRegisterResult.getAuthToken());
                mModel = Model.getInstance();
                mModel.killInstance();
                mModel = Model.getInstance();
                mModel.setPersonID(mRegisterResult.getPersonID());
                mModel.setUser(mUser);
                mModel.setServerHost(mServerHost.getText().toString());
                mModel.setServerPort(mServerPort.getText().toString());
                String [] responseParams = {mServerHost.getText().toString(), mServerPort.getText().toString(), ""};
                new PersonTask().execute(responseParams);
            }
            else{
                Global.sAuthToken = new AuthToken();
                mResponseMessage = mRegisterResult.errorMessage();
                Toast.makeText(getContext(), mResponseMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    public class LoginTask extends AsyncTask<String, Void, String> {

        private LoginResult mLoginResult;

        @Override
        protected String doInBackground(String... strings) {
            LoginRequest mLoginRequest = new LoginRequest(strings[2], strings[3]);
            mLoginResult = proxy.login(strings[0], strings[1], mLoginRequest);
            return mLoginResult.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(mLoginResult.errorMessage().length() == 0){
                Global.sAuthToken = new AuthToken(mLoginResult.getLoginUser(), mLoginResult.getLoginAuthToken());
                mModel = Model.getInstance();
                mModel.killInstance();
                mModel = Model.getInstance();
                mModel.setPersonID(mLoginResult.getLoginPersonID());
                mModel.setUser(mUser);
                mModel.setServerHost(mServerHost.getText().toString());
                mModel.setServerPort(mServerPort.getText().toString());
                String [] responseParams = {mServerHost.getText().toString(), mServerPort.getText().toString(),""};
                new PersonTask().execute(responseParams);
            }
            else{
                Global.sAuthToken = new AuthToken();
                mResponseMessage = mLoginResult.errorMessage();
                Toast.makeText(getContext(), mResponseMessage, Toast.LENGTH_LONG).show();
            }



        }
    }

    public class PersonTask extends AsyncTask<String, Void, PersonResult>{

        @Override
        protected PersonResult doInBackground(String... strings) {
            PersonProxy personProxy = new PersonProxy();
            PersonRequest personRequest = new PersonRequest(strings[2]);
            return personProxy.requestPerson(strings[0], strings[1], personRequest, Global.sAuthToken);
        }

        @Override
        protected void onPostExecute(PersonResult personResult) {
            super.onPostExecute(personResult);
            if(personResult.errorMessage().length() == 0){
                if(personResult.isSinglePerson()){
                    Person person = personResult.getOnePerson();
                    Toast.makeText(getContext(), "firstName : " + person.getFirstName() + "\n" +
                            "lastName : " + person.getLastName(), Toast.LENGTH_LONG).show();
                    String [] responseParams = {mServerHost.getText().toString(), mServerPort.getText().toString(),""};
                    new EventTask().execute(responseParams);
                }
                else{
                    Toast.makeText(getContext(), "Got all persons", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getContext(), personResult.errorMessage(), Toast.LENGTH_LONG).show();

            }
        }

    }

    public class EventTask extends AsyncTask<String, Void, EventResult>{

        @Override
        protected EventResult doInBackground(String... strings) {
            EventProxy eventProxy = new EventProxy();
            EventRequest eventRequest = new EventRequest(strings[2]);
            eventProxy.requestEvent(strings[0], strings[1], eventRequest, Global.sAuthToken);
            EventResult eventResult = new EventResult(mModel.getUserEvents(),"");
            return eventResult;
        }

        @Override
        protected void onPostExecute(EventResult eventResult) {
            super.onPostExecute(eventResult);
            if(eventResult.getAllEvents().size() == 0){
                Log.d(Global.ERROR, "Failed to get Events or Events for " + mUser.getUsername() + " do not exist");
            }
            else{
                Log.i(Global.SUCCESS,"Number of events for " + mUser.getUsername() + " " + mModel.getUserEvents().size());
                Log.i(Global.SUCCESS,"Event number " + (mModel.getUserEvents().size() - 1) + " for " + mUser.getUsername() + " is "
                        + mModel.getUserEvents().get(mModel.getUserEvents().size() - 1).toString());
                if(mModel.correctData()){
                    mModel.setShowLifeLine(true);
                    mModel.setShowFamilyLine(true);
                    mModel.setShowSpouseLine(true);
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent);
                }
            }
        }

    }



}

