package com.ajanovski.booklistapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ajanovski.booklistapp.interfaces.BooksInterface;
import com.ajanovski.booklistapp.model.Book;
import com.ajanovski.booklistapp.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ajanovski.booklistapp.R;

public class MainFragment extends Fragment implements BooksInterface {

    FloatingActionButton btnAddBook;
    RecyclerView rvBooks;
    TextView tvNoBooks;
    Button btnLogout;


    public MainFragment() {

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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBooks = view.findViewById(R.id.rvBooks);
        btnAddBook = view.findViewById(R.id.btnAddBook);
        tvNoBooks = view.findViewById(R.id.tvNoBooks);
        btnLogout = view.findViewById(R.id.btnLogout);

        setupBooks();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = requireActivity()
                        .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.PREF_USER, "");
                editor.apply();

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentCotainer, new LoginFragment())
                        .commit();
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentCotainer, new AddBookFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    @Override
    public void deleteBook(Book book) {

    }

    private void setupBooks() {
        SharedPreferences preferences = requireActivity()
                .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

        String jsonArrayBooks = preferences.getString(Constants.PREF_BOOKS, "");
        if(jsonArrayBooks.equals("")) {
            rvBooks.setVisibility(View.GONE);
            tvNoBooks.setVisibility(View.VISIBLE);
        } else {
            rvBooks.setVisibility(View.VISIBLE);
            tvNoBooks.setVisibility(View.GONE);
        }
    }

}