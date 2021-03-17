package com.example.jsonrequest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jsonrequest.Adapters.DrinkAdapter;
import com.example.jsonrequest.Models.Drink;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Drink> drinks;
    DrinkAdapter drinkAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        drinks = new ArrayList<>();
        drinkAdapter = new DrinkAdapter(drinks, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(drinkAdapter);

        listAll();
    }

    public void btnNew(View view) {
        Intent it = new Intent(this, AddActivity.class);
        startActivity(it);
        finish();
    }

    private void listAll(){
        String URL = "http://192.168.0.17/projects/WineHouse/public/api/drinks";
        Ion.with(MainActivity.this)
        .load(URL)
        .asJsonArray()
        .setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                try {
                    for(int i = 0; i < result.size(); i++){
                        JsonObject drinkJson = result.get(i).getAsJsonObject();
                        Drink drinkObj = new Drink(
                            drinkJson.get("id").getAsInt(),
                            drinkJson.get("product").getAsString(),
                            drinkJson.get("price").getAsDouble()
                        );
                        drinks.add(drinkObj);
                    }
                    drinkAdapter.notifyDataSetChanged();
                }catch(Exception error){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}