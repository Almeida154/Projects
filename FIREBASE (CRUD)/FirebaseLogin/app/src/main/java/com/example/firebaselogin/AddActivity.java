package com.example.firebaselogin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Models.Brand;
import com.example.firebaselogin.Models.Drink;
import com.example.firebaselogin.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Objects;
import maes.tech.intentanim.CustomIntent;

public class AddActivity extends AppCompatActivity {

    DatabaseReference drinksFIREBASE = FirebaseDatabase.getInstance().getReference("Drinks");
    DatabaseReference brandsFIREBASE = FirebaseDatabase.getInstance().getReference("Brands");
    TextInputEditText edtNameAdd;
    TextInputEditText edtPriceAdd;

    Spinner spinnerBrand;
    ArrayList<Brand> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.white)));

        edtNameAdd = findViewById(R.id.edtNameAdd);
        edtPriceAdd = findViewById(R.id.edtPriceAdd);
        spinnerBrand = findViewById(R.id.spinnerBrand);

        values.add(new Brand("Marca"));
        spinnerPopulate();

    }

    public void btnAdd(View view) {
        String key = drinksFIREBASE.push().getKey();
        Brand brand = (Brand) spinnerBrand.getSelectedItem();
        Drink drink = new Drink(
                key,
                Objects.requireNonNull(edtNameAdd.getText()).toString(),
                Double.parseDouble(Objects.requireNonNull(edtPriceAdd.getText()).toString()),
                brand.getId()
        );
        drinksFIREBASE.child(Objects.requireNonNull(key)).setValue(drink)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent it = new Intent(AddActivity.this, HomeActivity.class);
                        startActivity(it);
                        CustomIntent.customType(AddActivity.this, "right-to-left");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Algo deu errado :/", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void spinnerPopulate(){
        brandsFIREBASE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                values.clear();
                values.add(new Brand("Marca"));
                for(DataSnapshot brandSnap : snapshot.getChildren()){
                    Brand brand = brandSnap.getValue(Brand.class);
                    values.add(brand);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });
        ArrayAdapter<Brand> adapter = new ArrayAdapter<Brand>(this, android.R.layout.simple_spinner_dropdown_item, values);
        spinnerBrand.setAdapter(adapter);
    }

    public void imBack(View view) {
        finish();
        Intent it = new Intent(this, HomeActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(this, HomeActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }
}