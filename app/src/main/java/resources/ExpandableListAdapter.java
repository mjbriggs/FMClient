package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import michaelbriggs.familymapclient.Model;
import michaelbriggs.familymapclient.R;
import model.Event;
import model.Person;
import model.Relative;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private Map<String, List<Event>> _listDataChild; // event data
    private Map<String, List<Relative>> _listFamilyDataChild;
    private String iconInfo;
    private Person mPerson;
    private Model mModel;
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 Map<String, List<Event>> listChildData,
                                 Map<String, List<Relative>> listFamilyDataChild,
                                 Person person) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.iconInfo = "";
        this.mPerson = person;
        this._listFamilyDataChild = listFamilyDataChild;
        this.mModel = Model.getInstance();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        if(_listDataHeader.get(groupPosition).equals("Events"))
            return this._listDataChild.get(mPerson.getPersonID())
                .get(childPosititon);
        else
            return this._listFamilyDataChild.get(mPerson.getPersonID())
                    .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Object childObject = getChild(groupPosition, childPosition);
        Person childPerson = null;
        Relative childRelative = null;
        Event childEvent = null;
        String  childText = "";
        if(childObject.getClass() == Relative.class){
            childRelative = (Relative) childObject;
            childPerson = childRelative.getRelative();
            childText = childPerson.getFirstName() + " " + childPerson.getLastName() + "\n" +
            childRelative.getRelationship();
        }
        else if(childObject.getClass() == Event.class){
            childEvent = (Event) childObject;
            String city = childEvent.getCity();
            String country = childEvent.getCountry();
            city = mModel.cleanUpName(city);
            country = mModel.cleanUpName(country);
            childText = childEvent.getEventType() + ": " + city + ", " + country +
                    "(" + childEvent.getYear() + ")";
        }
        else {
            childText = "No data";
        }
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        ImageView iconListChild = (ImageView) convertView.findViewById(R.id.lblListIcon);
        if(childEvent != null){
            Drawable androidIcon = new IconDrawable(this._context, FontAwesomeIcons.fa_map_marker).
                    colorRes(R.color.colorPrimary).sizeDp(18);
            iconListChild.setImageDrawable(androidIcon);
        }
        else if(childPerson != null){
            if(childPerson.getGender().equals("m")){
                Drawable androidIcon = new IconDrawable(this._context, FontAwesomeIcons.fa_male).
                        colorRes(R.color.colorPrimary).sizeDp(18);
                iconListChild.setImageDrawable(androidIcon);
            }
            else if(childPerson.getGender().equals("f")){
                Drawable androidIcon = new IconDrawable(this._context, FontAwesomeIcons.fa_female).
                        colorRes(R.color.colorPrimary).sizeDp(18);
                iconListChild.setImageDrawable(androidIcon);
            }
        }


        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(_listDataHeader.get(groupPosition).equals("Events"))
            return this._listDataChild.get(mPerson.getPersonID())
                    .size();
        else
            return this._listFamilyDataChild.get(mPerson.getPersonID())
                    .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
