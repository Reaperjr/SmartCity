package nuno.estg.smartcity.ui_main;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import nuno.estg.smartcity.R;
import nuno.estg.smartcity.db.NotesDBHelper;
import nuno.estg.smartcity.db.NotesManagerDB;
import nuno.estg.smartcity.ui_notes.notes.AddNoteFragment;
import nuno.estg.smartcity.ui_notes.notes.NotesModel;
import nuno.estg.smartcity.ui_notes.notes.RecyclerViewNotes;
import nuno.estg.smartcity.ui_notes.notes.UpdateNoteFragment;


public class ListFragment extends Fragment {
    SQLiteDatabase db;
    RecyclerView recyclerView;
    RecyclerViewNotes adapter;
    List<NotesModel> NotesModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        getActivity().setTitle("Notes");
        setHasOptionsMenu(true);
        NotesDBHelper dbhelper = new NotesDBHelper(getActivity());
        db = dbhelper.getWritableDatabase();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewNotes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        NotesModel = dbhelper.fill();

        adapter = new RecyclerViewNotes(getContext(), NotesModel);
        recyclerView.setAdapter(adapter);
        //adapter.swapCursor(getAllNotes());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return root;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            NotesModel note = NotesModel.get(position);

            switch (direction){
                case ItemTouchHelper.LEFT:
                    delete(note.getId());
                    NotesModel.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, NotesModel.size());
                    break;
                case ItemTouchHelper.RIGHT:
                    FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    UpdateNoteFragment frag = new UpdateNoteFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("note", note);
                    frag.setArguments(bundle);
                    ft.replace(R.id.fragment_container_notes, frag);
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
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark))
                    .addSwipeRightActionIcon(R.drawable.edit_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void delete(int id)
    {
        NotesManagerDB db=new NotesManagerDB(getContext());

        //OPEN DB
        db.openDB();

        //COMMIT
        int result=db.delete(id);

        if(result>0)
        {
            return;

        }else
        {
            //Toast.makeText(getContext(), "Error in deleting", Toast.LENGTH_SHORT).show();
        }

        db.closeDB();

    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.addNote) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_notes, new AddNoteFragment()).addToBackStack(null).commit();
        }

        return super.onOptionsItemSelected(item);
    }


}
