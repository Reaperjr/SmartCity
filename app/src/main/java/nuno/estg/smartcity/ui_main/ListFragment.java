package nuno.estg.smartcity.ui_main;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import nuno.estg.smartcity.R;
import nuno.estg.smartcity.ui_notes.notes.UpdateNoteFragment;


public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewSubm adapter;
    private List<SubmissionModel> mSubmssionModel = new ArrayList<>();;
    int id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info_map, container, false);
        getActivity().setTitle("Own Submissions");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        id = sharedPreferences.getInt("id", 0);
        Log.d("ResponseList", String.valueOf(id));
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewMap);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        getData(id);

        return root;
    }

    public void getData(int id) {
        final String url = "http://192.168.1.66:3000/api/submission/" + id;
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
                                SubmissionModel model = new SubmissionModel();
                                model.setId_submission(object.optInt("id_submissions"));
                                model.setAssunto(object.optString("assunto"));
                                model.setLat(object.optDouble("lat"));
                                model.setLng(object.optDouble("lng"));
                                model.setObs(object.optString("obs"));
                                model.setImg(object.optString("img"));
                                model.setId_user(object.optInt("id_user"));
                                model.setData(object.optString("data"));
                                mSubmssionModel.add(model);
                            }
                            adapter = new RecyclerViewSubm(getContext(), mSubmssionModel);
                            recyclerView.setAdapter(adapter);
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

    public void delete(int id) {
        final String url = "http://192.168.1.66:3000/api/submission/" + id;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final boolean success = response.getBoolean("status");
                    Log.d("ResponseList", String.valueOf(success));
                    if (success == true) {
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } else if (success == false) {
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d("Response", response.toString());
                    }
                } catch (JSONException e) {
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
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            SubmissionModel subm = mSubmssionModel.get(position);
            Log.d("tag", subm.toString());

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    delete(subm.getId_submission());
                    mSubmssionModel.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, mSubmssionModel.size());
                    break;
                case ItemTouchHelper.RIGHT:
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    UpdateMapInfoFragment frag = new UpdateMapInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info", subm);
                    frag.setArguments(bundle);
                    ft.replace(R.id.fragment_container, frag);
                    ft.addToBackStack(null);
                    ft.commit();
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(getActivity(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_sweep_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark))
                    .addSwipeRightActionIcon(R.drawable.edit_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

}
