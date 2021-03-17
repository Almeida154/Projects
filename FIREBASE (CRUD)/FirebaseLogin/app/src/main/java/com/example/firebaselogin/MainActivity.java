package com.example.firebaselogin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.firebaselogin.Adapters.SlidePagerAdapter;
import com.example.firebaselogin.Controllers.FunctionController;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PagerAdapter pagerAdapter;
    ViewPager vpContent;
    TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verify internet connection

        if(!new FunctionController(this).isConnected()) new FunctionController(this).dialog(
                "<h1><font>No Internet</font></h1>",
                "Connect to a <b>network</b> to continue",
                R.drawable.ic_wifi_off,
                true
        );

        // Status bar color

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.white)));

        // Slide fragments (login and sign up)

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new SignUpFragment());

        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), fragments);
        vpContent = findViewById(R.id.vpContent);
        tab = findViewById(R.id.tab);

        vpContent.setAdapter(pagerAdapter);
        tab.setupWithViewPager(vpContent);

        slideBetweenFragments(vpContent, tab);

    }

    private void slideBetweenFragments(ViewPager vpContent, TabLayout tab){
        vpContent.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}