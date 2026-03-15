package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

/**
 * MainActivity — WhatsApp Home Screen
 *
 * ViewPager2 + TabLayout use kare che to show 3 tabs:
 *   Tab 0 → ChatsFragment  (Firebase users)
 *   Tab 1 → StatusFragment (dummy status items)
 *   Tab 2 → CallsFragment  (dummy call history)
 *
 * TabLayoutMediator banne TabLayout ane ViewPager2 ne sync kare che.
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Firebase Auth check ---
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // --- Toolbar ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // --- ViewPager2 setup ---
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // --- TabLayout + ViewPager2 sync ---
        // TabLayoutMediator banne ne connect kare che ane tab names set kare
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("CHATS");  break;
                case 1: tab.setText("STATUS"); break;
                case 2: tab.setText("CALLS");  break;
            }
        }).attach();

        // --- FAB ---
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v ->
            Toast.makeText(this, "Tap any user in Chats to start chatting!", Toast.LENGTH_SHORT).show()
        );
    }

    // --- Toolbar Menu (Logout) ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}