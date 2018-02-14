package com.example.olive.musicapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.olive.musicapplication.rock_music.RockMusicFragment;

public class MainActivity extends AppCompatActivity{
    private static FragmentManager fragmentManager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        // Switch between fragments with navigation view
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rock:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, new RockMusicFragment())
                            .disallowAddToBackStack()
                            .commit();
                    return true;
                case R.id.navigation_classic:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, new ClassicMusicFragment())
                            .disallowAddToBackStack()
                            .commit();
                    return true;
                case R.id.navigation_pop:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, new PopMusicFragment())
                            .disallowAddToBackStack()
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Load RockMusic Fragment
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new RockMusicFragment())
                    .commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
