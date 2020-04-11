package nuno.estg.smartcity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nuno.estg.smartcity.ui_notes.notes.NotesFragment;

public class MainActivityNotes extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new NotesFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_notes, selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        BottomNavigationView navView = findViewById(R.id.nav_view_notes);
        navView.setOnNavigationItemSelectedListener(navListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_notes, new NotesFragment()).commit();


    }

}
