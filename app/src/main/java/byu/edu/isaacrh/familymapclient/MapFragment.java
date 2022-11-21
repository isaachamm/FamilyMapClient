package byu.edu.isaacrh.familymapclient;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.TextureStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Event;
import model.Person;

public class MapFragment extends Fragment  implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private TextView eventInfo;
    private ImageView genderImage;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        eventInfo = view.findViewById(R.id.mapEventInfo);
        genderImage = view.findViewById(R.id.mapGenderImage);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        float permColorCode = 0.0F;
        float tempColorCode = permColorCode;

        for(Map.Entry<String, Event> entry : DataCache.getEvents().entrySet()) {
            if(DataCache.getColorMap() == null) {
                DataCache.addEventColor(entry.getValue().getEventType(), permColorCode);
            }
            else if (DataCache.getColorMap().get(entry.getValue().getEventType()) != null) {
                tempColorCode = DataCache.getColorMap().get(entry.getValue().getEventType());
            }
            else {
                permColorCode += 30;
                if(permColorCode > 360) {
                    permColorCode %= 30;
                    permColorCode += 3;
                }
                tempColorCode = permColorCode;
                DataCache.addEventColor(entry.getValue().getEventType(), permColorCode);
            }

            Marker marker = mMap.addMarker(new MarkerOptions().
                    position(new LatLng(entry.getValue().getLatitude(), entry.getValue().getLongitude())).
                    icon(BitmapDescriptorFactory.defaultMarker(tempColorCode)));
            //todo does this work on the other end when we retrieve from the map?
            marker.setTag(entry.getValue());
        }

        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        Event clickedEvent = (Event) marker.getTag();
        Person associatedPerson = DataCache.getPersonById(clickedEvent.getPersonID());

        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(clickedEvent.getLatitude(),
                clickedEvent.getLongitude())));

        String eventInfoString = associatedPerson.getFirstName() + " " + associatedPerson.getLastName() +
                "\n" + clickedEvent.getEventType() + ": " + clickedEvent.getCity() + ", " +
                clickedEvent.getCountry() + " (" + clickedEvent.getYear() + ")";

        eventInfo.setText(eventInfoString);
        //todo set gender image

        if(DataCache.getPolylines() != null) {
            for (Polyline p : DataCache.getPolylines()) {
                p.remove();
            }
        }

        List<Polyline> polylineList = new ArrayList<>();
        // Code for the spouse line
        if(DataCache.getPersonById(associatedPerson.getSpouseID()) != null) {
            Person associatedSpouse = DataCache.getPersonById(associatedPerson.getSpouseID());
            Event spouseBirthday = DataCache.getPersonEvents().get(associatedSpouse).get(0);

            LatLng startPoint = new LatLng(clickedEvent.getLatitude(), clickedEvent.getLongitude());
            LatLng endPoint = new LatLng(spouseBirthday.getLatitude(), spouseBirthday.getLongitude());

            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint)
                    .color(Color.BLUE)
                    .geodesic(true)
                    .width(20);
            Polyline polyline = mMap.addPolyline(options);
            polylineList.add(polyline);
        }

        // Code for the family tree line
        familyTreeLineRecurse(associatedPerson, clickedEvent, 20, polylineList);

        // Code for the life story line
        Event birthEvent = DataCache.getPersonEvents().get(associatedPerson).get(0);
        lifeStoryLineRecurse(associatedPerson, birthEvent, 0, polylineList);



        DataCache.setPolylines(polylineList);

        return false;
    }

    public void familyTreeLineRecurse(Person person, Event event, int width, List<Polyline> polylineList) {
        if(DataCache.getPersonById(person.getFatherID()) != null) {
            Person father = DataCache.getPersonById(person.getFatherID());
            Person mother = DataCache.getPersonById(person.getMotherID());

            Event fatherBirth = DataCache.getPersonEvents().get(father).get(0);
            Event motherBirth = DataCache.getPersonEvents().get(mother).get(0);

            familyTreeLineRecurse(father, fatherBirth, width - 5, polylineList);
            familyTreeLineRecurse(mother, motherBirth, width - 5, polylineList);

            LatLng startPoint = new LatLng(event.getLatitude(), event.getLongitude());
            LatLng endPoint = new LatLng(fatherBirth.getLatitude(), fatherBirth.getLongitude());
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint)
                    .color(Color.BLACK)
                    .geodesic(true)
                    .width(width);
            Polyline polyline = mMap.addPolyline(options);
            polylineList.add(polyline);


            endPoint = new LatLng(motherBirth.getLatitude(), motherBirth.getLongitude());
            options = new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint)
                    .color(Color.BLACK)
                    .geodesic(true)
                    .width(width);
            polyline = mMap.addPolyline(options);
            polylineList.add(polyline);
        }
    }
    public void lifeStoryLineRecurse(Person person, Event prevEvent, int currIndex, List<Polyline> polylineList) {
        if((currIndex + 1) < DataCache.getPersonEvents().get(person).size()) {
            currIndex += 1;

            Event nextEvent = DataCache.getPersonEvents().get(person).get(currIndex);

            LatLng startPoint = new LatLng(prevEvent.getLatitude(), prevEvent.getLongitude());
            LatLng endPoint = new LatLng(nextEvent.getLatitude(), nextEvent.getLongitude());

            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint)
                    .color(Color.RED)
                    .geodesic(true)
                    .width(20);
            Polyline polyline = mMap.addPolyline(options);
            polylineList.add(polyline);

            lifeStoryLineRecurse(person, nextEvent, currIndex, polylineList);
        }
    }
}