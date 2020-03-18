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
        final EditText clubInput = (EditText) findViewById(R.id.clubInput);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Button button = (Button) findViewById(R.id.button);
        final Button viewAllButton = (Button) findViewById(R.id.viewAllButton);
        final TextView allClubs = (TextView) findViewById(R.id.allClubs);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://powerful-cove-79276.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ClubService service = retrofit.create(ClubService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Club club = new Club(clubInput.getText().toString());
                Call<Club> createCall = service.createClub(club);
                createCall.enqueue(new Callback<Club>() {
                    @Override
                    public void onResponse(Call<Club> call, Response<Club> resp) {
                        Club newClub = resp.body();
                        try {
                            System.out.println(resp.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        textView.setText("Created Club with ClubName: " + newClub.clubName);
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
                Call<List<Club>> createCall = service.allClubs();
                createCall.enqueue(new Callback<List<Club>>() {
                    @Override
                    public void onResponse(Call<List<Club>> call, Response<List<Club>> resp) {
                        allClubs.setText("ALL CLUBS by Name:\n");
                        for (Club b : resp.body()) {
                            allClubs.append(b.clubName + "\n");
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
