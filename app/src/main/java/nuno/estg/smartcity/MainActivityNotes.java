package nuno.estg.smartcity;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import nuno.estg.smartcity.ui_notes.notes.NotesFragment;
import nuno.estg.smartcity.ui_notes.notifications.NotificationsFragment;

public class MainActivityNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        BottomNavigationView navView = findViewById(R.id.nav_view_notes);
        navView.setOnNavigationItemSelectedListener(navListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_notes, new NotesFragment()).commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new NotesFragment();
                    break;
               case R.id.navigation_dashboard:
                    selectedFragment = new NotificationsFragment();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new NotificationsFragment();
                    break;
            }
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_notes, selectedFragment).commit();
            return true;
        }
    };

}
