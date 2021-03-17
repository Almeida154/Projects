package com.example.firebaselogin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaselogin.Models.Drink;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import maes.tech.intentanim.CustomIntent;

public class UpdateActivity extends AppCompatActivity {

    TextInputEditText edtIdUpdt;
    TextInputEditText edtNameUpdt;
    TextInputEditText edtPriceUpdt;

    DatabaseReference drinksFIREBASE = FirebaseDatabase.getInstance().getReference("Drinks");
    Drink drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtIdUpdt = findViewById(R.id.edtIdUpdt);
        edtNameUpdt = findViewById(R.id.edtNameUpdt);
        edtPriceUpdt = findViewById(R.id.edtPriceUpdt);

        if(getIntent().hasExtra("drinkSerializable")){
            drink = (Drink) getIntent().getSerializableExtra("drinkSerializable");
            edtIdUpdt.setText(drink.getId());
            edtNameUpdt.setText(drink.getName());
            edtPriceUpdt.setText(String.format("%s", drink.getPrice()));
        }
    }

    public void btnUpdate(View view) {
        Drink newDrink = new Drink(
                drink.getId(),
                Objects.requireNonNull(edtNameUpdt.getText()).toString(),
                Double.parseDouble(Objects.requireNonNull(edtPriceUpdt.getText()).toString()),
                drink.getFk_brand()
        );
        drinksFIREBASE.child(Objects.requireNonNull(drink.getId())).setValue(newDrink)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent it = new Intent(UpdateActivity.this, HomeActivity.class);
                        startActivity(it);
                        CustomIntent.customType(UpdateActivity.this, "right-to-left");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateActivity.this, "Algo deu errado :/", Toast.LENGTH_SHORT).show();
                    }
                });
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