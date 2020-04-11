package nuno.estg.smartcity.ui_main;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import nuno.estg.smartcity.R;

public class UpdateMapInfoFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();
    EditText assunto, data, obs;
    Button saveBtn;
    String assuntos, obss, dates;
    private int id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_map_info, container, false);
        getActivity().setTitle("Add Submission");
        assunto = root.findViewById(R.id.editAssuntoMap);
        data = root.findViewById(R.id.editDataMap);
        obs = root.findViewById(R.id.editObsMap);
        saveBtn = root.findViewById(R.id.button2Map);
        Bundle bundle = getArguments();
        SubmissionModel info = (SubmissionModel) bundle.getSerializable("info");
        assunto.setText(info.getAssunto());
        data.setText(info.getData());
        obs.setText(info.getObs());
        id = info.getId_submission();
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
                assuntos = assunto.getText().toString();
                obss = obs.getText().toString();
                dates = data.getText().toString();
                update(assuntos, id, obss, dates);

            }
        });

        return root;

    }

    private void update(final String assunto, int id, final String obs, final String date) {
        final String url = "http://192.168.1.66:3000/api/submission/updates";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject params = new JSONObject();
        try {
            params.put("assunto", assunto);
            params.put("obs", obs);
            params.put("data", date);
            params.put("id_submissions", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final boolean success = response.getBoolean("status");
                    Log.d("Response", String.valueOf(success));
                    if (success == true) {
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new ListFragment()).addToBackStack(null).commit();
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
        };
        queue.add(rq);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        data.setText(sdf.format(myCalendar.getTime()));
    }


}