package nuno.estg.smartcity.ui.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nuno.estg.smartcity.R;

public class RecyclerViewNotes extends RecyclerView.Adapter<RecyclerViewNotes.ViewHolder> {
    List<NotesModel> notes ;
    Context mContext;



    public RecyclerViewNotes(Context context, List<NotesModel> notes) {
        this.mContext = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_layout_notes, null);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       /* if (!mCursor.move(position)) {
            return;
        }





        String assunto = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_ASSUNTO));
        String rua = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_MORADA));
        String local = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_LOCAL));
        String codpostal = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_CODPOSTAL));
        String data = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DATA));
        String obs = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_OBSERVACOES));*/


        holder.assunto.setText(notes.get(position).getAssunto());
        holder.rua.setText(notes.get(position).getRua());
        holder.localidade.setText(notes.get(position).getLocal());
        holder.codpostal.setText(notes.get(position).getCodpostal());
        holder.data.setText(notes.get(position).getData());
        holder.obs.setText(notes.get(position).getObs());


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    /*public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView assunto, rua, localidade, codpostal, data, obs, lbrua, lblocal, lbcod, lbdata, lbobs;
        RelativeLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lbrua = itemView.findViewById(R.id.morada);
            lblocal = itemView.findViewById(R.id.localidade);
            lbcod = itemView.findViewById(R.id.codpostal);
            lbdata = itemView.findViewById(R.id.data);
            lbobs = itemView.findViewById(R.id.observacoes);
            assunto = itemView.findViewById(R.id.assunto);
            rua = itemView.findViewById(R.id.textMorada);
            localidade = itemView.findViewById(R.id.textLocalidade);
            codpostal = itemView.findViewById(R.id.textCodPostal);
            data = itemView.findViewById(R.id.textData);
            obs = itemView.findViewById(R.id.textObs);

            expandableLayout = itemView.findViewById(R.id.expandableLayout);

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
