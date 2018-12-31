package michaelbriggs.familymapclient;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import model.Event;
import model.Person;
import resources.Global;

public class EventActivity extends AppCompatActivity {
    private Person mPerson;
    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            mPerson = (Person) bundle.getSerializable(Global.PERSON);
            mEvent = (Event) bundle.getSerializable(Global.EVENT);
            Log.d(Global.PERSON, "Person in event activity \n" + mPerson.toString());
            Log.d(Global.EVENT, "Event in event activity \n" + mEvent.toString());

        }
        else {
            mPerson = new Person();
            mEvent = new Event();
        }


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new MapFragment();
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
