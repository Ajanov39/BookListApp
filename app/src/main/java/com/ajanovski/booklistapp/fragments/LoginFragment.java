package com.ajanovski.booklistapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ajanovski.booklistapp.model.User;
import com.ajanovski.booklistapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ajanovski.booklistapp.R;

public class LoginFragment extends Fragment {

    Button btnLogin;
    TextView tvRegister;
    EditText etUsername, etPassword;

    public LoginFragment() {

    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRegister = view.findViewById(R.id.tvRegister);
        btnLogin = view.findViewById(R.id.btnLogin);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentCotainer, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = requireActivity()
                        .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

                String userListJson = preferences.getString(Constants.PREF_USERS, "");
                if(userListJson.equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                    dialog.setMessage("No active users on this app, please create one!")
                            .setTitle("No Users")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                    dialog.show();
                } else {
                    Gson gson = new Gson();

                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    Type type = new TypeToken<ArrayList<User>>() {}.getType();
                    ArrayList<User> outputList = gson.fromJson(userListJson, type);
                    Boolean userExists = false;
                    User userLogin = null;
                    for(int i = 0; i < outputList.size(); i++) {
                        User user = outputList.get(i);
                        if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                            userExists = true;
                            userLogin = user;
                            break;
                        }
                    }

                    if(userExists) {
                        SharedPreferences.Editor editor = preferences.edit();
                        String jsonUser = gson.toJson(userLogin);
                        editor.putString(Constants.PREF_USER, jsonUser);
                        editor.apply();

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentCotainer, new MainFragment())
                                .commit();

                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                        dialog.setMessage("Wrong username or password")
                                .setTitle("Wrong credentials")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        dialog.show();
                    }
                }
            }
        });

    }
}