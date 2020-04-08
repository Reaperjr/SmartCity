package nuno.estg.smartcity.ui_main;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import nuno.estg.smartcity.R;
import nuno.estg.smartcity.ui_notes.notes.AddNoteFragment;


public class AddMap extends Fragment{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap googleMap;
    MapView mMapView;
    private UiSettings mUiSettings;
    private double lat, lng;
    private boolean mPermissionDenied = false;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.map_fragment_home, container, false);
        setHasOptionsMenu(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
       /* try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        mMapView.getMapAsync(new OnMapReadyCallback() {

            private void enableMyLocation() {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission to access the location is missing.
                    PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                            Manifest.permission.ACCESS_FINE_LOCATION, true);
                } else if (googleMap != null) {
                    // Access to the location has been granted to the app.
                    googleMap.setMyLocationEnabled(true);
                }
            }


            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                mUiSettings = mMap.getUiSettings();

                // Keep the UI Settings state in sync with the checkboxes.
                mUiSettings.setZoomControlsEnabled(true);
                enableMyLocation();
                mUiSettings.setMyLocationButtonEnabled(true);
                mUiSettings.setScrollGesturesEnabled(true);
                mUiSettings.setZoomGesturesEnabled(true);
                mUiSettings.setRotateGesturesEnabled(true);

                /* For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng latLng) {

                        // Creating a marker
                        MarkerOptions markerOptions = new MarkerOptions();

                        // Setting the position for the marker
                        markerOptions.position(latLng);

                        // Setting the title for the marker.
                        // This will be displayed on taping the marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                        // Clears the previously touched position
                        googleMap.clear();

                        // Animating to the touched position
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                        // Placing a marker on the touched position
                        googleMap.addMarker(markerOptions);
                        lat = latLng.latitude;
                        lng = latLng.longitude;
                    }
                });
            }
        });
        return root;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_map_info, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        MarkerOptions markerOptions = new MarkerOptions();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.addMapInfo) {
            FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            AddMapInfoFragment frag = new AddMapInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble("lat", lat);
            bundle.putDouble("lng", lng);
            frag.setArguments(bundle);
            ft.replace(R.id.fragment_container, frag);
            ft.addToBackStack(null);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }

}
