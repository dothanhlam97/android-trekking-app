package com.app.trekking;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.trekking.controller.Profile;
import com.app.trekking.controller.popupController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class MapLocationOnTour extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    PlaceAutocompleteFragment placeAutoComplete;
    private SupportMapFragment mapFragment;
    private MarkerOptions marker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_location_on_tour);
        mapFragment =
                (SupportMapFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.map_in_choose_tour);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.d("map fragment", "null");
        }
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_tour);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("autocomplete", "Place: " + place.getName());
//                Log.i("autocomplete", "Place: " + place.);
                marker = new MarkerOptions().position(place.getLatLng());
                if (mMap != null) {
                    mMap.addMarker(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
                } else {
                    Log.d("mMap", "null");
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error autocomplete", "An error occurred: " + status);
            }

        });

        onClickAcceptLocation();
    }

    public void onClickAcceptLocation() {
        Button accept = (Button) findViewById(R.id.accept_location);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (marker == null) {
                    return;
                } else {
                    LatLng position = marker.getPosition();
                    Log.d("position", position.toString());
                    finish();
                }
            }
        });
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
    public void onMapReady(GoogleMap map) {
        this.mMap = map;
    }
}
