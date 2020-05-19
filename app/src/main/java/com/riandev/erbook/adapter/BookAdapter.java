package com.riandev.erbook.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riandev.erbook.FormActivity;
import com.riandev.erbook.MainActivity;
import com.riandev.erbook.R;
import com.riandev.erbook.db.AppDatabase;
import com.riandev.erbook.model.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    Context context;
    List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_book, parent, false);
        return new ViewHolder(view);
    }

    private void delete(Book book){
        AppDatabase.getInstance(context).bookDao().delete(book);
        Intent intent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(0,0);
        ((Activity) context).finish();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = books.get(position);
        holder.txtTitle.setText(book.title);
        holder.txtCategory.setText(book.category);
        holder.txtIsbn.setText(book.isbn);
        holder.txtReleasedate.setText(book.releasedate);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("book", book);
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Yakin hapus data buku '"+ book.title + "'?");
                builder.setPositiveButton("Hapus",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                delete(book);
                            }
                        });
                builder.setNegativeButton("Batal", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (books != null) ? books.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //Variable declaration
        TextView txtTitle, txtCategory, txtIsbn, txtReleasedate;
        LinearLayout layout;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_title);
            txtCategory = itemView.findViewById(R.id.tv_category);
            txtIsbn = itemView.findViewById(R.id.tv_isbn);
            txtReleasedate = itemView.findViewById(R.id.tv_release_date);
            layout = itemView.findViewById(R.id.lin_layout);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
