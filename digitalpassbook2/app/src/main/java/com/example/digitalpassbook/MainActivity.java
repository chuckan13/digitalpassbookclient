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
                .baseUrl("https://pure-river-68629.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final OrganizationService service = retrofit.create(OrganizationService.class);
        final EventService eventService = retrofit.create(EventService.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Organization organization = new Organization(nameInput.getText().toString());
                System.out.println("organization name and id" + organization.getId() + organization.getName());
                Call<Organization> createCall = service.create(organization);
//                System.out.println("create call:"+createCall.);
                createCall.enqueue(new Callback<Organization>() {
                    @Override
                    public void onResponse(Call<Organization> call, Response<Organization> resp) {
//                        System.out.println("2.0 getFeed > Full json res wrapped in gson => "+ new GsonBuilder().setPrettyPrinting().create().toJson(resp));
                        Organization newItem = resp.body();
                        System.out.println(newItem.getName());
                        textView.setText("Created Organization: " + newItem.getName());
                    }

                    @Override
                    public void onFailure(Call<Organization> call, Throwable t) {
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
                Call<List<Organization>> createCall = service.getall();
                createCall.enqueue(new Callback<List<Organization>>() {
                    @Override
                    public void onResponse(Call<List<Organization>> call, Response<List<Organization>> resp) {
                        System.out.println("2.0 getFeed > Full json res wrapped in gson => "+ new GsonBuilder().setPrettyPrinting().create().toJson(resp.body()));
                        allItems.setText("ALL OrganizationS by Name:\n");
                        for (Organization b : resp.body()) {
                            allItems.append(b.getName() + "\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Organization>> call, Throwable t) {
                        t.printStackTrace();
                        allItems.setText(t.getMessage());
                    }
                });
            }
        });
    }
}
