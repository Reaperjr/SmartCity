package nuno.estg.smartcity.ui_main;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nuno.estg.smartcity.R;


public class MapHome extends Fragment{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap googleMap;
    MapView mMapView;
    private UiSettings mUiSettings;
    private List<SubmissionModel> mSubmssionModel = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.map_fragment_home, container, false);
        getData();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

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

                mUiSettings.setZoomControlsEnabled(true);
                enableMyLocation();
                mUiSettings.setMyLocationButtonEnabled(true);
                mUiSettings.setScrollGesturesEnabled(true);
                mUiSettings.setZoomGesturesEnabled(true);
                mUiSettings.setRotateGesturesEnabled(true);

                for (SubmissionModel subm: mSubmssionModel){
                    LatLng latLng = new LatLng(subm.getLat(), subm.getLng());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(subm.getAssunto()).snippet(subm.getObs()));
                }

                /*// For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
            }
        });
        return root;
    }
    public void getData() {
        final String url = "http://192.168.1.66:3000/api/submission";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        try {
            JsonObjectRequest rq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        final boolean success = response.getBoolean("status");
                        Log.d("ResponseList", String.valueOf(success));
                        if (success == true) {
                            JSONArray data = null;
                            try {
                                data = response.getJSONArray("data");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = null;
                                try {
                                    object = data.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SubmissionModel submissionModel = new SubmissionModel();
                                submissionModel.setId_submission(object.optInt("id_submissions"));
                                Log.d("Response", String.valueOf(submissionModel.getId_submission()));
                                submissionModel.setAssunto(object.optString("assunto"));
                                submissionModel.setLat(object.optDouble("lat"));
                                submissionModel.setLng(object.optDouble("lng"));
                                submissionModel.setObs(object.optString("obs"));
                                submissionModel.setId_user(object.optInt("id_user"));
                                submissionModel.setData(object.optString("data"));
                                mSubmssionModel.add(submissionModel);
                            }
                        } else if (success == false) {
                            Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            Log.d("Response", response.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                }
            });
            queue.add(rq);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
