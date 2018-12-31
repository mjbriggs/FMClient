package michaelbriggs.familymapclient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Event;
import model.Person;
import model.Relative;
import resources.Global;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Model mModel;
    private ImageView mImageView;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private boolean mMarkerClicked;
    private Person mMarkerPerson;
    private Event mMarkerEvent;
    private List<Polyline> mPolylines;
    private List<Marker> mMarkers;

    public static MapFragment newInstance(){
        return new MapFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View v = layoutInflater.inflate(R.layout.fragment_maps, viewGroup, false);

        mMap = null;

        mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Drawable androidIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).
                colorRes(R.color.colorPrimary).sizeDp(24);
        mImageView = (ImageView) v.findViewById(R.id.icon);
        mImageView.setImageDrawable(androidIcon);

        mTextView = (TextView) v.findViewById(R.id.info);

        mMarkerClicked = false;

        mLinearLayout = (LinearLayout) v.findViewById(R.id.MapEventInfo);
        mLinearLayout.setClickable(true);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMarkerClicked){
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra(Global.PERSON, mMarkerPerson);
                    intent.putExtra(Global.EVENT, mMarkerEvent);
                    startActivity(intent);
                }
            }
        });

        Bundle eventBundle = getArguments();

        if(eventBundle != null){
            mMarkerPerson = (Person) eventBundle.getSerializable(Global.PERSON);
            mMarkerEvent = (Event) eventBundle.getSerializable(Global.EVENT);
        }
        else{
            mMarkerPerson = new Person();
            mMarkerEvent = new Event();
        }

        Log.d(Global.PERSON, "Person in Map activity \n" + mMarkerPerson.toString());
        Log.d(Global.EVENT, "Event in Map activity \n" + mMarkerEvent.toString());

        mPolylines = new ArrayList<>();
        mMarkers = new ArrayList<>();
        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mModel = Model.getInstance();

        mMap = googleMap;
        mMap.setMapType(mModel.getGoogleMapType());


        addMarkers();

        if(mMarkerEvent.getPersonID().length() > 0 && mMarkerPerson.getPersonID().length() > 0){
            clearLines();
            setEventDisplay(true);
            drawSpouseLines(mMarkerEvent, mMarkerPerson, mModel.isShowSpouseLine());
            drawLifeStoryLines(mMarkerPerson, mModel.isShowLifeLine());
            drawFamilyMapLines(mMarkerPerson, mMarkerEvent,mModel.isShowFamilyLine());
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String eventID = (String) (marker.getTag());
                mMarkerEvent = mModel.findEventByID(eventID);
                mMarkerPerson = mModel.findPersonByID(mMarkerEvent.getPersonID());
                clearLines();
                setEventDisplay(true);
                drawSpouseLines(mMarkerEvent, mMarkerPerson, mModel.isShowSpouseLine());
                drawLifeStoryLines(mMarkerPerson, mModel.isShowLifeLine());
                drawFamilyMapLines(mMarkerPerson, mMarkerEvent,mModel.isShowFamilyLine());
                return false;
            }
        });

    }

    private Marker findMarker(String eventTag){
        for(Marker marker : mMarkers){
            String tag = (String) marker.getTag();
            if(tag.equals(eventTag))
                return marker;
        }
        return null;
    }
    private void clearLines() {
        for(Polyline line : mPolylines){
            line.remove();
        }
        mPolylines.clear();
    }

    private void setEventDisplay(boolean clicked){
        if(mMarkerPerson.getGender().equals("m")){
            Drawable androidIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                    colorRes(R.color.colorPrimary).sizeDp(24);
            mImageView.setImageDrawable(androidIcon);
        }
        else{
            Drawable androidIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                    colorRes(R.color.colorPrimary).sizeDp(24);
            mImageView.setImageDrawable(androidIcon);
        }

        String country = mMarkerEvent.getCountry();
        String city = mMarkerEvent.getCity();
        country = mModel.cleanUpName(country);
        city = mModel.cleanUpName(city);
        mTextView.setText(mMarkerPerson.getFirstName() + " " + mMarkerPerson.getLastName() + "\n"
                + mMarkerEvent.getEventType() + ": " + city + ", " + country +
                " (" + mMarkerEvent.getYear() + ")");
        mMarkerClicked = clicked;

        LatLng location = new LatLng(mMarkerEvent.getLatitude(), mMarkerEvent.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

    }

    /**
     * birth is yellow, marriage is green, death is red, unkown events are blue
     */
    private void addMarkers(){
        ArrayList<Event> events = mModel.getUserEvents();
        Marker markerToAdd;
        for(Event event : events){
            LatLng location = new LatLng(event.getLatitude(), event.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            Person person = mModel.findPersonByID(event.getPersonID());
            String name = person.getFirstName() + " " + person.getLastName();
            if(event.getEventType().equals("birth")){
                Log.d("EVENT", event.toString());

                markerToAdd =  mMap.addMarker(new MarkerOptions().
                        position(location).
                        title(event.getEventType())
                        .snippet(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                markerToAdd.setTag(event.getEventID());

                mMarkers.add(markerToAdd);
            }
            else if(event.getEventType().equals("marriage")){
                Log.d("EVENT", event.toString());

                markerToAdd =  mMap.addMarker(new MarkerOptions().
                        position(location).
                        title(event.getEventType())
                        .snippet(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                markerToAdd.setTag(event.getEventID());

                mMarkers.add(markerToAdd);

            }
            else if(event.getEventType().equals("death")){
                Log.d("EVENT", event.toString());

                markerToAdd =  mMap.addMarker(new MarkerOptions().
                        position(location).
                        title(event.getEventType())
                        .snippet(name));
                markerToAdd.setTag(event.getEventID());

                mMarkers.add(markerToAdd);

            }
            else {
                Log.d("EVENT", event.toString());

                markerToAdd =  mMap.addMarker(new MarkerOptions().
                        position(location).
                        title(event.getEventType())
                        .snippet(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                markerToAdd.setTag(event.getEventID());

                mMarkers.add(markerToAdd);
            }
        }
    }

    //Spouse line is red
    private void drawSpouseLines(Event eventClicked, Person eventClickedPerson, boolean canDraw){
        if(canDraw){
            if(eventClickedPerson.getSpouse().length() > 0){
                if(mModel.getPersonEvents().size() == 0)
                    mModel.sortEventsByPerson();
                String [] spouseName = eventClickedPerson.getSpouse().split("_");
                Person spouse = mModel.findPerson(eventClickedPerson.getDescendent(), spouseName[0], spouseName[1]);
                List<Event> spouseEvents = mModel.getPersonEvents().get(spouse.getPersonID());
                Event birthEvent = spouseEvents.get(0);
                if(birthEvent.getEventType().toLowerCase().equals("birth")){
                    mPolylines.add(mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(eventClicked.getLatitude(), eventClicked.getLongitude()),
                                    new LatLng(birthEvent.getLatitude(), birthEvent.getLongitude()))
                            .width(8)
                            .color(mModel.getSpouseLineColor())));
                }

            }
        }
    }

    private void drawLifeStoryLines(Person personClicked, boolean canDraw){
        if(canDraw){
            if(mModel.getPersonEvents().size() == 0)
                mModel.sortEventsByPerson();
            List<Event> lifeEvents = mModel.getPersonEvents().get(personClicked.getPersonID());
            int storyLength = lifeEvents.size() - 1;
            for(int i = 0; i < storyLength; i++){
                Event lifeEvent1 = lifeEvents.get(i);
                Event lifeEvent2 = lifeEvents.get(i + 1);

                mPolylines.add(mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(lifeEvent1.getLatitude(), lifeEvent1.getLongitude()),
                                new LatLng(lifeEvent2.getLatitude(), lifeEvent2.getLongitude()))
                        .width(8)
                        .color(mModel.getLifeLineColor())));
            }
        }
    }

    private void drawFamilyMapLines(Person personClicked, Event eventClicked, boolean canDraw){
        if(canDraw){
            Log.d("FAMILY", "called drawFamilyMapLines");
            if(mModel.getFamilyMap().size() == 0)
                mModel.createFamilyMap();

            List<Relative> familyMembers = mModel.getFamilyMap().get(personClicked.getPersonID());
            int lineWidth = 21;
            Relative relativeClicked = new Relative(personClicked, "root");

            for(Relative relative : familyMembers){
                if(relative.getRelationship().toLowerCase().equals("father")){
                    buildFamilyMap(eventClicked, relativeClicked, relative, lineWidth);
                }
                else if(relative.getRelationship().toLowerCase().equals("mother")){
                    buildFamilyMap(eventClicked, relativeClicked, relative, lineWidth);
                }
            }


        }
    }

    private void buildFamilyMap(Event childEvent, Relative child, Relative parent, int width){ //child is previeous family member, parent is next
        if(parent == null)
            return;
        else {
            Log.d("FAMILY", "in recursion");
            Relative nextParent = null;
            Event birthEvent = null;
            if(width > 1)
                width -= 5;
            List<Event> parentEvents = mModel.getPersonEvents().get(parent.getRelative().getPersonID());

            birthEvent = parentEvents.get(0);
                mPolylines.add(mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(birthEvent.getLatitude(), birthEvent.getLongitude()),
                                new LatLng(childEvent.getLatitude(), childEvent.getLongitude()))
                        .width(width)
                        .color(mModel.getFamilyLineColor())));

            List<Relative> parentRelatives = mModel.getFamilyMap().get(parent.getRelative().getPersonID());
            for(Relative relative : parentRelatives){
                if(relative.getRelationship().toLowerCase().equals("father")){
                    nextParent = relative;
                    buildFamilyMap(birthEvent, parent, relative, width);
                }
                else if(relative.getRelationship().toLowerCase().equals("mother")){
                    nextParent = relative;
                    buildFamilyMap(birthEvent, parent, relative, width);
                }
            }

            if(nextParent == null){
                buildFamilyMap(null, null, null, width);
            }

        }
    }
}