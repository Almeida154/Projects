package com.example.firebaselogin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Models.Brand;
import com.example.firebaselogin.Models.Drink;
import com.example.firebaselogin.Models.ItemOrdered;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModelDrinkActivity extends AppCompatActivity {

    TextView txvModelName;
    TextView txvModelId;
    TextView txvModelBrand;
    TextView txvModelPrice;
    TextView txvModelSubtotal;
    TextView txvModelQuantity;
    TextView txvSubtract;
    TextView txvAdd;

    MaterialButton btnAddCart;

    DatabaseReference brandsFIREBASE =
            FirebaseDatabase.getInstance().getReference("Brands");

    DatabaseReference itemsOrderedFIREBASE =
            FirebaseDatabase.getInstance().getReference("ItemsOrdered");

    Drink drink;

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_drink);

        txvModelName = findViewById(R.id.txvModelName);
        txvModelId = findViewById(R.id.txvModelId);
        txvModelBrand = findViewById(R.id.txvModelBrand);
        txvModelPrice = findViewById(R.id.txvModelPrice);
        txvModelSubtotal = findViewById(R.id.txvModelSubtotal);
        txvModelQuantity = findViewById(R.id.txvModelQuantity);
        txvSubtract = findViewById(R.id.txvSubtract);
        txvAdd = findViewById(R.id.txvAdd);
        btnAddCart = findViewById(R.id.btnAddCart);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        if(getIntent().hasExtra("drink")){
            new FunctionController(this);
            drink = (Drink) getIntent().getSerializableExtra("drink");

            brandsFIREBASE.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Brand brand = snapshot.child(drink.getFk_brand()).getValue(Brand.class);
                    txvModelBrand.setText(String.format("Marca: %s", brand.getDesc()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            txvModelName.setText(drink.getName());
            txvModelId.setText(String.format("(%s)", drink.getId()));
            txvModelPrice.setText(String.format("Un.: %s",
                    FunctionController.toBrazilianCoin(drink.getPrice())));
            txvModelSubtotal.setText(String.format("Subtotal: %s",
                    FunctionController.toBrazilianCoin(quantity * drink.getPrice())));
            btnAddCart.setText(String.format("Adicionar ao carrinho (%s)",
                    FunctionController.toBrazilianCoin(quantity * drink.getPrice())));
        }

        buttons();

    }

    private void buttons(){
        new FunctionController(this);
        txvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity < 20){
                    quantity++;
                    txvModelQuantity.setText(String.valueOf(quantity));
                    txvModelSubtotal.setText(String.format("Subtotal: %s",
                            FunctionController.toBrazilianCoin(quantity * drink.getPrice())));
                    btnAddCart.setText(String.format("Adicionar ao carrinho (%s)",
                            FunctionController.toBrazilianCoin(quantity * drink.getPrice())));
                }
            }
        });
        txvSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity > 1){
                    quantity--;
                    txvModelQuantity.setText(String.valueOf(quantity));
                    txvModelSubtotal.setText(String.format("Subtotal: %s",
                            FunctionController.toBrazilianCoin(quantity * drink.getPrice())));
                    btnAddCart.setText(String.format("Adicionar ao carrinho (%s)",
                            FunctionController.toBrazilianCoin(quantity * drink.getPrice())));
                }
            }
        });
    }

    public void btnAddToCart(View view) {

        AtomicBoolean alreadyOrdered = new AtomicBoolean(false);

        CartActivity.itemsOrdered = (CartActivity.itemsOrdered != null) ? CartActivity.itemsOrdered : new ArrayList<>();

        for(ItemOrdered itemOrdered : CartActivity.itemsOrdered){
            if(itemOrdered.getDrink().getId().equals(drink.getId())) {
                itemOrdered.setQuantity(itemOrdered.getQuantity() + quantity);
                alreadyOrdered.set(true);
                finish();
                Toast.makeText(this, "Quantidade adicionada", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        if(!alreadyOrdered.get()){
            String key = itemsOrderedFIREBASE.push().getKey();
            CartActivity.itemsOrdered.add(new ItemOrdered(key, quantity, drink));
            finish();
            Toast.makeText(this, "Adicionado ao carrrinho", Toast.LENGTH_SHORT).show();
        }

    }
}