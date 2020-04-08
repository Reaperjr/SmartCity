package nuno.estg.smartcity.ui_main;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import nuno.estg.smartcity.MainActivity;
import nuno.estg.smartcity.R;
import nuno.estg.smartcity.db.NotesManagerDB;

import static android.app.Activity.RESULT_OK;

public class AddMapInfoFragment extends Fragment {

    EditText assunto, data, obs;
    Button saveBtn, chooseImg;
    ImageView img;
    private double lat,lng;
    final Calendar myCalendar = Calendar.getInstance();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_map_info, container, false);
        getActivity().setTitle("Add Submission");
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        lat = bundle.getDouble("lat");
        lng = bundle.getDouble("lng");
        Log.d("ADebugTag", "Lat: " + Double.toString(lat) + "Lng:" + Double.toString(lng) );
        assunto = root.findViewById(R.id.editAssuntoMap);
        data = root.findViewById(R.id.editDataMap);
        obs = root.findViewById(R.id.editObsMap);
        img = root.findViewById(R.id.imageViewMap);
        saveBtn = root.findViewById(R.id.button2Map);
        chooseImg = root.findViewById(R.id.button3Map);
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                else {
                    startGallery();
                }
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;

    }
    private void submit(final String email, final String pass) {
        final String url = "http://192.168.1.66:3000/api/submission/submit";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject params = new JSONObject();
        try {
            params.put("assunto", email);
            params.put("lat", pass);
            params.put("lng", email);
            params.put("obs", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final boolean success = response.getBoolean("status");
                    Log.d("Response", String.valueOf(success));
                    if (success == true) {
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } else if (success == false) {
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d("Response", response.toString());
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
/*
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", uname);
                params.put("password", upass);
                return params;
            }*/
        };
        //MySingleton.getInctance(getActivity().getApplicationContext()).addToRequestQueue(rq);
        queue.add(rq);
    }
    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                img.setImageBitmap(bitmapImage);
            }
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        data.setText(sdf.format(myCalendar.getTime()));
    }
}