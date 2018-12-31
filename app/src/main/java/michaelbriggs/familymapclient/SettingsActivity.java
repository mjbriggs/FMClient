package michaelbriggs.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import model.Person;
import proxy.EventProxy;
import proxy.PersonProxy;
import request.EventRequest;
import request.PersonRequest;
import resources.Global;
import result.EventResult;
import result.PersonResult;

public class SettingsActivity extends AppCompatActivity {
    private Switch mLifeLineSwitch;
    private Switch mFamilyLineSwitch;
    private Switch mSpouseLineSwitch;
    private LinearLayout mLogOutLL;
    private LinearLayout mReSyncLL;
    private Model mModel;
    private Context mContext;
    private Spinner mLifeLineSpinner;
    private Spinner mFamilyLineSpinner;
    private Spinner mSpouseLineSpinner;
    private Spinner mMapTypeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = this;

        mModel = Model.getInstance();

        mLogOutLL = (LinearLayout) findViewById(R.id.logout_ll);
        mLogOutLL.setClickable(true);
        mLogOutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModel.killInstance();
                Global.sAuthToken = null;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mReSyncLL = (LinearLayout) findViewById(R.id.resync_ll);
        mReSyncLL.setClickable(true);
        mReSyncLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] responseParams = {mModel.getServerHost(), mModel.getServerPort(),""};
                new SettingsActivity.PersonTask().execute(responseParams);

            }
        });

        mLifeLineSwitch = (Switch) findViewById(R.id.life_story_switch);
        mLifeLineSwitch.setChecked(mModel.isShowLifeLine());
        mLifeLineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mModel.setShowLifeLine(isChecked);
            }
        });

        mFamilyLineSwitch = (Switch) findViewById(R.id.family_tree_switch);
        mFamilyLineSwitch.setChecked(mModel.isShowFamilyLine());
        mFamilyLineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mModel.setShowFamilyLine(isChecked);
            }
        });

        mSpouseLineSwitch = (Switch) findViewById(R.id.spouse_line_switch);
        mSpouseLineSwitch.setChecked(mModel.isShowSpouseLine());
        mSpouseLineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mModel.setShowSpouseLine(isChecked);
            }
        });

        mLifeLineSpinner = (Spinner) findViewById(R.id.life_story_spinner);
        mLifeLineSpinner.setSelection(mModel.getLifeLineColorIndex());
        mLifeLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mModel.setLifeLineColor(Color.GREEN);
                        mModel.setLifeLineColorIndex(0);
                        break;
                    case 1:
                        mModel.setLifeLineColor(Color.BLACK);
                        mModel.setLifeLineColorIndex(1);
                        break;
                    case 2:
                        mModel.setLifeLineColor(Color.RED);
                        mModel.setLifeLineColorIndex(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFamilyLineSpinner = (Spinner) findViewById(R.id.family_tree_spinner);
        mFamilyLineSpinner.setSelection(mModel.getFamilyLineColorIndex());
        mFamilyLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mModel.setFamilyLineColor(Color.BLACK);
                        mModel.setFamilyLineColorIndex(0);
                        break;
                    case 1:
                        mModel.setFamilyLineColor(Color.RED);
                        mModel.setFamilyLineColorIndex(1);
                        break;
                    case 2:
                        mModel.setFamilyLineColor(Color.GREEN);
                        mModel.setFamilyLineColorIndex(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpouseLineSpinner = (Spinner) findViewById(R.id.spouse__line_spinner);
        mSpouseLineSpinner.setSelection(mModel.getSpouseLineColorIndex());
        mSpouseLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mModel.setSpouseLineColor(Color.RED);
                        mModel.setSpouseLineColorIndex(0);
                        break;
                    case 1:
                        mModel.setSpouseLineColor(Color.GREEN);
                        mModel.setSpouseLineColorIndex(1);
                        break;
                    case 2:
                        mModel.setSpouseLineColor(Color.BLACK);
                        mModel.setSpouseLineColorIndex(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);
        mMapTypeSpinner.setSelection(mModel.getGoogleMapTypeIndex());
        mMapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mModel.setGoogleMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mModel.setGoogleMapTypeIndex(0);
                        break;
                    case 1:
                        mModel.setGoogleMapType(GoogleMap.MAP_TYPE_HYBRID);
                        mModel.setGoogleMapTypeIndex(1);
                        break;
                    case 2:
                        mModel.setGoogleMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        mModel.setGoogleMapTypeIndex(2);
                        break;
                    case 3:
                        mModel.setGoogleMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        mModel.setGoogleMapTypeIndex(3);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class PersonTask extends AsyncTask<String, Void, PersonResult> {

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
                    String [] responseParams = {mModel.getServerHost(), mModel.getServerPort(),""};
                    new SettingsActivity.EventTask().execute(responseParams);
                }
            }
            else{
                Toast.makeText(mContext, "Resync Failed", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mContext, "Resync Failed", Toast.LENGTH_SHORT).show();
                Log.d(Global.ERROR, "Failed to get Events or Events for " + mModel.getUser().getUsername() + " do not exist");
            }
            else{
                Log.i(Global.SUCCESS,"Number of events for " + mModel.getUser().getUsername() + " " + mModel.getUserEvents().size());
                Log.i(Global.SUCCESS,"Event number " + (mModel.getUserEvents().size() - 1) + " for " + mModel.getUser().getUsername() + " is "
                        + mModel.getUserEvents().get(mModel.getUserEvents().size() - 1).toString());
                if(mModel.correctData()){
                    Intent intent = new Intent(mContext, MapActivity.class);
                    startActivity(intent);
                }
            }
        }

    }
}
