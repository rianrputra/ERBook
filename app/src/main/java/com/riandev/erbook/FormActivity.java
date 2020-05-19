package com.riandev.erbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.riandev.erbook.db.AppDatabase;
import com.riandev.erbook.model.Book;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private EditText txtTitle, txtIsbn, txtReleasedate;
    private Spinner spCategory;
    private int mYear, mMonth, mDay;
    private Button btnSave, btnCancel;
    String relDateDb = "";
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        this.setFinishOnTouchOutside(false);
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        if (book == null){
            setTitle("Tambah Buku");
        } else {
            setTitle("Edit Buku");
        }

        //setTitle("Tambah/Ubah Buku");
        txtTitle = findViewById(R.id.ed_title);
        spCategory = findViewById(R.id.sp_category);
        txtIsbn = findViewById(R.id.ed_isbn);
        txtReleasedate = findViewById(R.id.ed_release_date);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        String[] category = {"Pendidikan","Agama","Teknologi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormActivity.this,
                R.layout.spinner_item, category);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        txtReleasedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int day) {
                                txtReleasedate.setText(day + "-" + (month + 1) + "-" + year);
                                relDateDb = year + "-" + (month + 1) + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        if (book != null){
            txtTitle.setText(book.title);
            txtIsbn.setText(book.isbn);
            txtReleasedate.setText(book.releasedate);
            relDateDb = book.releasedate;
            if (book.category.equals("Pendidikan")) {
                spCategory.setSelection(0);
            }
            else if (book.category.equals("Agama")) {
                spCategory.setSelection(1);
            }
            else {
                spCategory.setSelection(2);
            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTitle.getText().toString().equals("")){
                    txtTitle.setError("Judul harus diisi");
                    return;
                }
                if (txtIsbn.getText().toString().equals("")){
                    txtIsbn.setError("Nomor ISBN harus diisi");
                    return;
                }

                if (book == null){
                    book = new Book();
                    book.title = txtTitle.getText().toString();
                    book.isbn = txtIsbn.getText().toString();
                    String selecteditem = (String) spCategory.getSelectedItem();
                    book.category = selecteditem;
                    book.releasedate = relDateDb;
                    AppDatabase.getInstance(getApplicationContext()).bookDao().insert(book);
                    Toast.makeText(FormActivity.this, "Data buku '" + txtTitle.getText().toString() + "' berhasil disimpan",
                            Toast.LENGTH_SHORT).show();
                }else {
                    book.title = txtTitle.getText().toString();
                    book.isbn = txtIsbn.getText().toString();
                    String selecteditem = (String) spCategory.getSelectedItem();
                    book.category = selecteditem;
                    book.releasedate = relDateDb;

                    AppDatabase.getInstance(getApplicationContext()).bookDao().update(book);
                    Toast.makeText(FormActivity.this,"Data berhasil diubah",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
