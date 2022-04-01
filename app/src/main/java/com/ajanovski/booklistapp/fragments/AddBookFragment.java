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

import com.ajanovski.booklistapp.model.Book;
import com.ajanovski.booklistapp.util.AppHolder;
import com.ajanovski.booklistapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ajanovski.booklistapp.R;

public class AddBookFragment extends Fragment {

    EditText  etBookName,etAuthor;
    Button btnSaveBook;


    public AddBookFragment() {
        // Required empty public constructor
    }



    public static AddBookFragment newInstance(String param1, String param2) {
        AddBookFragment fragment = new AddBookFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etBookName = view.findViewById(R.id.etBookName);
        etAuthor = view.findViewById(R.id.etAuthor);
        btnSaveBook = view.findViewById(R.id.btnSaveBook);

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

        etBookName.addTextChangedListener(textWatcher);
        etAuthor.addTextChangedListener(textWatcher);


        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etBookName.getText().toString();
                String author = etAuthor.getText().toString();
                Gson gson = new Gson();
                SharedPreferences preferences = requireActivity()
                        .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);



                    int insertedByUser = 0;

                Book book = new Book(name, author, insertedByUser);



                String prefArray = preferences.getString(Constants.PREF_BOOKS, "");
                String jsonArray;
                if(prefArray.equals("")) {
                    AppHolder.books.add(book);
                    jsonArray = gson.toJson(AppHolder.books);
                } else {
                    Type type = new TypeToken<ArrayList<Book>>() {}.getType();
                    ArrayList<Book> outputList = gson.fromJson(prefArray, type);
                    AppHolder.books = outputList;
                    outputList.add(book);
                    jsonArray = gson.toJson(outputList);
                }

                SharedPreferences.Editor editor = preferences.edit();
                //editor.putInt(Constants.PREF_ID_COUNTER, counter);
                editor.putString(Constants.PREF_BOOKS, jsonArray);
                editor.apply();

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentCotainer, new MainFragment())
                        .commit();
            }
        });



    }


    private Boolean checkFields() {
        Boolean validFields = false;
        String name = etBookName.getText().toString();
        String author = etAuthor.getText().toString();


        if(name.length() > 2 && author.length() > 2) {

                validFields = true;
            }

        return validFields;
    }

    private void enableButton(Boolean enabled) {
        btnSaveBook.setEnabled(enabled);
    }

}