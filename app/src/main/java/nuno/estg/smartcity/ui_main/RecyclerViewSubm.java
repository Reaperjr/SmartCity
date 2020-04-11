package nuno.estg.smartcity.ui_main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nuno.estg.smartcity.R;
import nuno.estg.smartcity.ui_notes.notes.NotesModel;

public class RecyclerViewSubm extends RecyclerView.Adapter<RecyclerViewSubm.ViewHolder> {
    List<SubmissionModel> submissionModel;
    Context mContext;



    public RecyclerViewSubm(Context context, List<SubmissionModel> submissionModel) {
        this.mContext = context;
        this.submissionModel = submissionModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_layout_subm, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.assunto.setText(submissionModel.get(position).getAssunto());
        holder.lat.setText(String.valueOf(submissionModel.get(position).getLat()));
        holder.lng.setText(String.valueOf(submissionModel.get(position).getLng()));
        holder.data.setText(submissionModel.get(position).getData());
        holder.obs.setText(submissionModel.get(position).getObs());
    }

    @Override
    public int getItemCount() {
        return submissionModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView assunto, lat, lng, data, obs, lblat, lblng, lbdata, lbobs;
        RelativeLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lblat = itemView.findViewById(R.id.Latitude);
            lblng = itemView.findViewById(R.id.Longitude);
            lbdata = itemView.findViewById(R.id.dataMap);
            lbobs = itemView.findViewById(R.id.observacoesMap);
            assunto = itemView.findViewById(R.id.assuntoMap);
            lat = itemView.findViewById(R.id.textLat);
            lng = itemView.findViewById(R.id.textLong);
            data = itemView.findViewById(R.id.textDataMap);
            obs = itemView.findViewById(R.id.textObsMap);

            expandableLayout = itemView.findViewById(R.id.expandableLayoutMap);

            assunto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout.getVisibility() == View.GONE) {
                        expandableLayout.setVisibility(View.VISIBLE);
                    } else {
                        expandableLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
