package com.example.proyectofinal.ui;

import com.example.proyectofinal.MyUtils;
import com.example.proyectofinal.ui.fragment.CursosProfesorFragment;
import com.example.proyectofinal.ui.fragment.NotifyProfesorFragment;
import com.example.proyectofinal.ui.fragment.HomeProfesorFragment;
import com.example.proyectofinal.R;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomeProfesor extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profesor);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setText(MyUtils.obtenerUsername(this));

//        FrameLayout layout = new FrameLayout(this);
//        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        layout.setId(R.id.layout);
//        setContentView(layout);
//
//        getSupportFragmentManager().beginTransaction().add(R.id.layout, new NotifyProfesorFragment()).commit();

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);

        fragment = new NotifyProfesorFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new NotifyProfesorFragment();
                        break;
                    case 1:
                        fragment = new CursosProfesorFragment();
                        break;
                    case 2:
                        fragment = new HomeProfesorFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}