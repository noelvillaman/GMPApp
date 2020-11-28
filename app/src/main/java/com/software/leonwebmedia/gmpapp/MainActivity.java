package com.software.leonwebmedia.gmpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.software.leonwebmedia.gmpapp.ui.main.ListFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .commitNow();
        }
    }
}