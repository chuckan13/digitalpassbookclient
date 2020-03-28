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
        final EditText nameInput = (EditText) findViewById(R.id.clubInput);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Button button = (Button) findViewById(R.id.button);
        final Button viewAllButton = (Button) findViewById(R.id.viewAllButton);
        final TextView allItems = (TextView) findViewById(R.id.allClubs);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://stark-castle-00086.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final EventService service = retrofit.create(EventService.class);
//        final BookService service = retrofit.create(BookService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int orgid = 3;
                String eventname = "semis";
                String description = "formals";
                String date = "2/3/13";
                String starttime = "8pm";
                String endtime = "2am";
                String location = "cap and gown";

                Event event = new Event(orgid, nameInput.getText().toString(), description, date, starttime, endtime, location);
                System.out.println("event name and id" + event.id + event.eventname);
                Call<Event> createCall = service.create(event);
//                System.out.println("create call:"+createCall.);
                createCall.enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> resp) {
//                        System.out.println("2.0 getFeed > Full json res wrapped in gson => "+ new GsonBuilder().setPrettyPrinting().create().toJson(resp));
                        Event newItem = resp.body();
                        System.out.println(newItem.eventname);
                        textView.setText("Created Event: " + newItem.eventname);
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
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
                Call<List<Event>> createCall = service.getall();
                createCall.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> resp) {
                        System.out.println("2.0 getFeed > Full json res wrapped in gson => "+ new GsonBuilder().setPrettyPrinting().create().toJson(resp.body()));
                        allItems.setText("ALL EventS by Name:\n");
                        for (Event b : resp.body()) {
                            allItems.append(b.eventname + "\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        t.printStackTrace();
                        allItems.setText(t.getMessage());
                    }
                });
            }
        });
    }
}
