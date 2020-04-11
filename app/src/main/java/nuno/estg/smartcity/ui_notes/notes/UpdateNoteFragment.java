package nuno.estg.smartcity.ui_notes.notes;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nuno.estg.smartcity.R;
import nuno.estg.smartcity.db.NotesManagerDB;

public class UpdateNoteFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();
    EditText assunto, rua, local, codpostal, data, obs;
    Button saveBtn;
    private SQLiteDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_note_fragment, container, false);
        getActivity().setTitle("Update Note");
        setHasOptionsMenu(true);


        assunto = root.findViewById(R.id.editAssunto);
        rua = root.findViewById(R.id.editRua);
        local = root.findViewById(R.id.editLocal);
        codpostal = root.findViewById(R.id.editCodPostal);
        data = root.findViewById(R.id.editData);
        obs = root.findViewById(R.id.editObs);
        saveBtn = root.findViewById(R.id.button2);

        Bundle bundle = getArguments();
        NotesModel note = (NotesModel) bundle.getSerializable("note");
        assunto.setText(note.getAssunto());
        rua.setText(note.getRua());
        local.setText(note.getLocal());
        codpostal.setText(note.getCodpostal());
        data.setText(note.getData());
        obs.setText(note.getObs());
        final int id = note.getId();
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
                update(id, assunto.getText().toString(), rua.getText().toString(), local.getText().toString(), codpostal.getText().toString(), data.getText().toString(), obs.getText().toString());
            }
        });

        return root;

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        data.setText(sdf.format(myCalendar.getTime()));
    }

    private void update(int id, String assunto, String rua, String local, String codpostal, String data, String obs) {
        NotesManagerDB db = new NotesManagerDB(getContext());

        //OPEN DB
        db.openDB();

        //COMMIT
        long result = db.update(id, assunto, rua, local, codpostal, data, obs);

        if (result > 0) {
            Toast.makeText(getContext(), "Updated with success", Toast.LENGTH_SHORT).show();
            return;

        } else {
            Toast.makeText(getContext(), "Unable to save", Toast.LENGTH_SHORT).show();
        }

        db.closeDB();

    }


}