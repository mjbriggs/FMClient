package michaelbriggs.familymapclient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.Person;
import model.SearchItem;
import resources.ListViewAdapter;

public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private String mQuery;
    private Model mModel;
    private List<Person> mPersonList;
    private List<Event> mEventList;
    private Context mContext;
    private List<SearchItem> mSearchItems;
    private ListViewAdapter mListViewAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mContext = this;

        mModel = Model.getInstance();

        mSearchItems = new ArrayList<>();

        mPersonList = mModel.getUserPersons();
        mEventList = mModel.getUserEvents();
        setSearchItems(mPersonList, mEventList);

        Log.d("SEARCH_ACTIVITY", "Persons : " + mPersonList.size() +
        "\nEvents : " + mEventList.size() +
        "\nSearchItems : " + mSearchItems.size());

        mSearchView = (SearchView) findViewById(R.id.search_bar);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();

        mListView = (ListView) findViewById(R.id.search_list_view);

        mListViewAdapter = new ListViewAdapter(this, mSearchItems);

        mListView.setAdapter(mListViewAdapter);
        mListViewAdapter.filter("!@#$%^&*()("); //crazy string to filter away all results

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = mSearchView.getQuery().toString();

                mListViewAdapter.filter(mQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void setSearchItems(List<Person> personList, List<Event> eventList){
        mSearchItems.clear();
        for(Person person : personList){
            Log.d("SEARCH_ACTIVITY", "Person being added  : " + person.toString() + "\n");
                    mSearchItems.add(new SearchItem(person, null));
        }
        for(Event event : eventList){
            Log.d("SEARCH_ACTIVITY", "Event being added  : " + event.toString() + "\n");
            mSearchItems.add(new SearchItem(null, event));
        }
    }
}
