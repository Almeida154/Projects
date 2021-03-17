package com.example.firebaselogin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.firebaselogin.Adapters.BrandAdapter;
import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Models.Brand;
import com.example.firebaselogin.Models.Drink;
import com.example.firebaselogin.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import maes.tech.intentanim.CustomIntent;

public class HomeActivity extends AppCompatActivity {

    TextView txvUsernameWelcome;
    ImageButton ibCart;

    DatabaseReference drinksFIREBASE = FirebaseDatabase.getInstance().getReference("Drinks");
    DatabaseReference usersFIREBASE = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference brandsFIREBASE = FirebaseDatabase.getInstance().getReference("Brands");

    BrandAdapter brandAdapter;
    RecyclerView recyclerView;
    List<Drink> drinks;
    List<Brand> brands;

    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.pink)));

        txvUsernameWelcome = findViewById(R.id.txvUsernameWelcome);
        recyclerView = findViewById(R.id.recyclerViewRow);

        drinks = new ArrayList<>();
        brands = new ArrayList<>();

        onClickCart();
        getExtras();
        defineUserNameText();
    }

    public void btnNew(View view) {
        Intent it = new Intent(this, AddActivity.class);
        startActivity(it);
    }

    public void onClickCart(){
        (ibCart = findViewById(R.id.ibCart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void getExtras(){
        if(getIntent().hasExtra("key")) id = getIntent().getStringExtra("key");
    }

    public void defineUserNameText(){
        usersFIREBASE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(id).getValue(User.class);
                txvUsernameWelcome.setText("Welcome, " + Objects.requireNonNull(user).getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        drinksFIREBASE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                drinks.clear();
                for(DataSnapshot drinkSnap : snapshot.getChildren()){
                    drinks.add(drinkSnap.getValue(Drink.class));
                }
                brandsFIREBASE.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        brands.clear();
                        for(DataSnapshot brandSnap : snapshot.getChildren()){
                            Brand brand = brandSnap.getValue(Brand.class);
                            List<Drink> drinksFiltered = new ArrayList<>();
                            for(Drink drink : drinks){
                                if(drink.getFk_brand().equals(Objects.requireNonNull(brand).getId())) drinksFiltered.add(drink);
                            }
                            if(drinksFiltered.size() > 0){
                                Objects.requireNonNull(brand).setDrinkArrayList(drinksFiltered);
                                brands.add(brand);
                            }
                        }
                        brandAdapter = new BrandAdapter(brands, HomeActivity.this);
                        recyclerView.setAdapter(brandAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }
}