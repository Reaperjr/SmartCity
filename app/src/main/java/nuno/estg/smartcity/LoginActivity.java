package nuno.estg.smartcity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText user, password;
    Button bt1, bt2;
    String email, pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        bt1 = (Button) findViewById(R.id.buttonLogin);
        bt2 = (Button) findViewById(R.id.buttonAnonimo);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user.getText().toString();
                pass = password.getText().toString();
                login(email,pass);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityNotes.class);
                startActivity(intent);
            }
        });
    }
    private void login(final String email, final String pass) {
        final String url = "http://192.168.1.66:3000/api/auth/login";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", pass);
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
                        JSONArray data = null;
                        try {
                            data = response.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int id ;
                        JSONObject object =
                                data.getJSONObject(0);
                        id = object.optInt("id_user");
                        Log.d("Tag", String.valueOf(id));
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id", id);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else if (success == false) {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d("Response", response.toString());
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
