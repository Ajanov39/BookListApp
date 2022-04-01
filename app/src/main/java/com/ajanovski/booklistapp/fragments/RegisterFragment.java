package com.ajanovski.booklistapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ajanovski.booklistapp.model.User;
import com.ajanovski.booklistapp.util.AppHolder;
import com.ajanovski.booklistapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ajanovski.booklistapp.R;


public class RegisterFragment extends Fragment {

    EditText etUsername, etPassword, etRepeatPassword;
    Button btnRegister;


    public RegisterFragment() {

    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etRepeatPassword = view.findViewById(R.id.etRepeatPassword);
        btnRegister = view.findViewById(R.id.btnRegister);

        enableButton(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(checkFields()) {
                    enableButton(true);
                } else {
                    enableButton(false);
                }
            }
        };

        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etRepeatPassword.addTextChangedListener(textWatcher);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                SharedPreferences preferences = requireActivity()
                        .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
                int counter = preferences.getInt(Constants.PREF_ID_COUNTER, 0);
                counter++;

                User user = new User(counter, username, password);

                Gson gson = new Gson();

                String prefArray = preferences.getString(Constants.PREF_USERS, "");
                String jsonArray;
                if(prefArray.equals("")) {
                    AppHolder.users.add(user);
                    jsonArray = gson.toJson(AppHolder.users);
                } else {
                    Type type = new TypeToken<ArrayList<User>>() {}.getType();
                    ArrayList<User> outputList = gson.fromJson(prefArray, type);
                    AppHolder.users = outputList;
                    outputList.add(user);
                    jsonArray = gson.toJson(outputList);
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(Constants.PREF_ID_COUNTER, counter);
                editor.putString(Constants.PREF_USERS, jsonArray);
                editor.apply();
            }
        });

    }


    private Boolean checkFields() {
        Boolean validFields = false;
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if(username.length() > 4 && password.length() > 4 && repeatPassword.length() > 4) {
            if(password.equals(repeatPassword)){
                validFields = true;
            }
        }

        return validFields;
    }

    private void enableButton(Boolean enabled) {
        btnRegister.setEnabled(enabled);
    }

}