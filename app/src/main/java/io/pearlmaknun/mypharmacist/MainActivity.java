package io.pearlmaknun.mypharmacist;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist.fragment.HomeFragment;
import io.pearlmaknun.mypharmacist.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    BottomNavigationView bottomNavigationView;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            fragment = new HomeFragment();
            callFragment(fragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            /*case R.id.navigation_notification:
                fragment = new KonsultasiFragment();
                callFragment(fragment);
                break;
            case R.id.navigation_discover:
                fragment = new DiscoverFragment();
                callFragment(fragment);
                break;*/
            case R.id.navigation_home:
                fragment = new HomeFragment();
                callFragment(fragment);
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                callFragment(fragment);
                break;
        }
        return true;
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
