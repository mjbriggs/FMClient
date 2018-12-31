package michaelbriggs.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Event;
import model.Person;
import model.Relative;
import resources.ExpandableListAdapter;
import resources.Global;

public class PersonActivity extends AppCompatActivity {
    private Person mPerson;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private Model mModel;

    //expandable list
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    Map<String, List<Event>> listDataChild;
    Map<String, List<Relative>> listFamilyDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        mModel = Model.getInstance();

        mContext = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mPerson = (Person) bundle.getSerializable(Global.PERSON);
        }
        else {
            mPerson = new Person();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.person_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mViewAdapter = new PersonAdapter(mPerson);
        mRecyclerView.setAdapter(mViewAdapter);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.person_info_list);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, listFamilyDataChild, mPerson);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                if(listDataHeader.get(groupPosition).equals("Events")){
                    Intent intent = new Intent(mContext, EventActivity.class);

                    Event eventToPut = listDataChild.get(mPerson.getPersonID()).get(childPosition);

                    intent.putExtra(Global.PERSON, mPerson);
                    intent.putExtra(Global.EVENT, eventToPut);
                    startActivity(intent);
                }
                else if(listDataHeader.get(groupPosition).equals("Family")) {
                    Intent intent = new Intent(mContext, PersonActivity.class);

                    Relative relativeToPut = listFamilyDataChild.get(mPerson.getPersonID()).get(childPosition);
                    Person personToPut = relativeToPut.getRelative();

                    intent.putExtra(Global.PERSON, personToPut);
                    startActivity(intent);
                }
                return false;
            }

        });

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        if(mModel.getFamilyMap().size() == 0)
            mModel.createFamilyMap();

        if(mModel.getPersonEvents().size() == 0)
            mModel.sortEventsByPerson();

        listDataHeader = new ArrayList<String>();
        listDataChild = mModel.getPersonEvents();
        listFamilyDataChild = mModel.getFamilyMap();

        // Adding child data
        listDataHeader.add("Events");
        listDataHeader.add("Family");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.person_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private class PersonHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mPersonAttr;
        private TextView mPersonInfo;


        public PersonHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.person_textview, parent, false));
            itemView.setOnClickListener(this);

            mPersonAttr = (TextView) itemView.findViewById(R.id.person_attribute);
            mPersonInfo = (TextView) itemView.findViewById(R.id.person_info);
        }

        public void bind(String personInfo, String personAttribute) {
            mPersonAttr.setText(personAttribute);
            mPersonInfo.setText(personInfo);
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

        private Person mPerson;
        private String [] mPersonAttrs;
        private String [] mPersonInfo;

        public PersonAdapter(Person person) {
            mPerson = person;
            mPersonAttrs = new String [] {"First Name", "Last Name", "Gender"};
            String gender = "";
            if(mPerson.getGender().equals("m"))
                gender = "Male";
            else
                gender = "Female";
            mPersonInfo = new String [] {mPerson.getFirstName(), mPerson.getLastName(), gender};
        }

        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            return new PersonHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            holder.bind(mPersonInfo[position], mPersonAttrs[position]);
        }

        @Override
        public int getItemCount() {
            return mPersonAttrs.length;
        }
    }
}
