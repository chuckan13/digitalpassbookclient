package com.example.digitalpassbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText isbnInput = (EditText) findViewById(R.id.clubInput);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Button button = (Button) findViewById(R.id.button);
        final Button viewAllButton = (Button) findViewById(R.id.viewAllButton);
        final TextView allClubs = (TextView) findViewById(R.id.allClubs);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://powerful-cove-79276.herokuapp.com/")
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ClubService service = retrofit.create(ClubService.class);
//        final BookService service = retrofit.create(BookService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Club club = new Club(isbnInput.getText().toString());
                System.out.println("club name and id" + club.id + club.clubname);
                Call<Club> createCall = service.create(club);
//                System.out.println("create call:"+createCall.);
                createCall.enqueue(new Callback<Club>() {
                    @Override
                    public void onResponse(Call<Club> call, Response<Club> resp) {
//                        System.out.println("2.0 getFeed > Full json res wrapped in gson => "+ new GsonBuilder().setPrettyPrinting().create().toJson(resp));
                        Club newBook = resp.body();
                        System.out.println(newBook.clubname);
                        textView.setText("Created Book with ISBN: " + newBook.clubname);
                    }

                    @Override
                    public void onFailure(Call<Club> call, Throwable t) {
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
                Call<List<Club>> createCall = service.getall();
                createCall.enqueue(new Callback<List<Club>>() {
                    @Override
                    public void onResponse(Call<List<Club>> call, Response<List<Club>> resp) {
                        System.out.println("2.0 getFeed > Full json res wrapped in gson => "+ new GsonBuilder().setPrettyPrinting().create().toJson(resp.body()));
                        allClubs.setText("ALL CLUBS by Name:\n");
                        for (Club b : resp.body()) {
                            allClubs.append(b.clubname + "\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Club>> call, Throwable t) {
                        t.printStackTrace();
                        allClubs.setText(t.getMessage());
                    }
                });
            }
        });
    }
}
