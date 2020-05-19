package com.riandev.erbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.riandev.erbook.adapter.BookAdapter;
import com.riandev.erbook.db.AppDatabase;
import com.riandev.erbook.model.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variable declaration
    FloatingActionButton fab;
    RecyclerView rv;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Id Initialization
        fab = findViewById(R.id.fab);
        rv = findViewById(R.id.rv_list);
        rv.addItemDecoration(new DividerItemDecoration(this,
                0));

        List<Book> books = AppDatabase.getInstance(this).bookDao().getAll();
        adapter = new BookAdapter(this, books);
        rv.setAdapter(adapter);

        //FAB onClick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });
    }
}
