package com.example.digitalpassbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText isbnInput = (EditText) findViewById(R.id.isbnInput);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Button button = (Button) findViewById(R.id.button);
        final Button viewAllButton = (Button) findViewById(R.id.viewAllButton);
        final TextView allBooks = (TextView) findViewById(R.id.allBooks);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://peaceful-hollows-01379.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final BookService service = retrofit.create(BookService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(isbnInput.getText().toString());
                Call<Book> createCall = service.create(book);
                createCall.enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> resp) {
                        Book newBook = resp.body();
                        System.out.println(newBook.isbn);
                        textView.setText("Created Book with ISBN: " + newBook.isbn);
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        System.out.println("failure");
                        t.printStackTrace();
                        textView.setText(t.getMessage());
                    }
                });
            }
        });
        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Book>> createCall = service.getall();
                createCall.enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> resp) {
                        allBooks.setText("ALL BOOKS by ISBN:\n");
                        for (Book b : resp.body()) {
                            allBooks.append(b.isbn + "\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        t.printStackTrace();
                        allBooks.setText(t.getMessage());
                    }
                });
            }
        });
    }
}
