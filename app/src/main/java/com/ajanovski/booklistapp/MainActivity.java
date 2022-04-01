package com.ajanovski.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.ajanovski.booklistapp.fragments.LoginFragment;
import com.ajanovski.booklistapp.fragments.MainFragment;
import com.ajanovski.booklistapp.util.Constants;

import ajanovski.booklistapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences =
                getSharedPreferences(getString(R.string.preferences_key), MODE_PRIVATE);

        String user = preferences.getString(Constants.PREF_USER, "");
        if(user.equals("")) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentCotainer, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentCotainer, new MainFragment())
                    .commit();
        }



    }
}