package resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import javax.microedition.khronos.opengles.GL;

import michaelbriggs.familymapclient.EventActivity;
import michaelbriggs.familymapclient.Model;
import michaelbriggs.familymapclient.PersonActivity;
import michaelbriggs.familymapclient.R;
import model.Event;
import model.Person;
import model.SearchItem;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<SearchItem> searchItemList = null;
    private ArrayList<SearchItem> arraylist;
    private Model mModel;

    public ListViewAdapter(Context context,
                           List<SearchItem> searchItemList) {
        mContext = context;
        this.searchItemList = searchItemList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchItem>();
        this.arraylist.addAll(searchItemList);
        mModel = Model.getInstance();
    }

    public class ViewHolder {
        ImageView icon;
        TextView info;
    }

    @Override
    public int getCount() {
        return searchItemList.size();
    }

    @Override
    public SearchItem getItem(int position) {
        return searchItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.icon = (ImageView) view.findViewById(R.id.lblListIcon);
            holder.info = (TextView) view.findViewById(R.id.lblListItem);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        SearchItem currentSearchItem = searchItemList.get(position);
        final Person currentPerson = currentSearchItem.getPerson();
        final Event currentEvent = currentSearchItem.getEvent();
        if(currentPerson == null && currentEvent != null){
            if(currentEvent.getCountry().length() > 0 && currentEvent.getCity().length() > 0){
                String city = currentEvent.getCity();
                String country = currentEvent.getCountry();
                city = mModel.cleanUpName(city);
                country = mModel.cleanUpName(country);
                holder.info.setText(currentEvent.getEventType() + ": " + city + ", " + country +
                        "(" + currentEvent.getYear() + ")");
                Drawable androidIcon = new IconDrawable(mContext, FontAwesomeIcons.fa_map_marker).
                        colorRes(R.color.colorPrimary).sizeDp(18);
                holder.icon.setImageDrawable(androidIcon);
            }

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // Send single item click data to SingleItemView Class
                    Intent intent = new Intent(mContext, EventActivity.class);
                    intent.putExtra(Global.EVENT, currentEvent);
                    intent.putExtra(Global.PERSON, mModel.findPersonByID(currentEvent.getPersonID()));
                    mContext.startActivity(intent);
                }
            });
        }
        else if(currentPerson != null && currentEvent == null){
            if(currentPerson.getGender().equals("m")){
                Drawable androidIcon = new IconDrawable(mContext, FontAwesomeIcons.fa_male).
                        colorRes(R.color.colorPrimary).sizeDp(18);
                holder.icon.setImageDrawable(androidIcon);
            }
            else if(currentPerson.getGender().equals("f")) {
                Drawable androidIcon = new IconDrawable(mContext, FontAwesomeIcons.fa_female).
                        colorRes(R.color.colorPrimary).sizeDp(18);
                holder.icon.setImageDrawable(androidIcon);
            }
            holder.info.setText(currentPerson.getFirstName() + " " + currentPerson.getLastName());

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // Send single item click data to SingleItemView Class
                    Intent intent = new Intent(mContext, PersonActivity.class);
                    intent.putExtra(Global.PERSON, currentPerson);
                    mContext.startActivity(intent);
                }
            });

        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchItemList.clear();
        if (charText.length() == 0) {
            searchItemList.addAll(arraylist);
        } else {
                List<Person> searchPersons = mModel.searchPersons(charText);
                List<Event> searchEvents = mModel.searchEvents(charText);
            for(Person person : searchPersons){
                searchItemList.add(new SearchItem(person, null));
            }
            for(Event event : searchEvents){
                searchItemList.add(new SearchItem(null, event));
            }

        }
        notifyDataSetChanged();
    }

}
