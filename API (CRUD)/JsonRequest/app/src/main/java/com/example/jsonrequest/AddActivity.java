package com.example.jsonrequest;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.example.jsonrequest.Models.Drink;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    TextInputLayout edtProductLayout;
    TextInputLayout edtPriceLayout;
    TextInputEditText edtProduct;
    TextInputEditText edtPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtProductLayout = findViewById(R.id.edtProductLayoutAdd);
        edtPriceLayout = findViewById(R.id.edtPriceLayoutAdd);
        edtProduct = findViewById(R.id.edtProductAdd);
        edtPrice = findViewById(R.id.edtPriceAdd);

    }

    public void btnAdd(View view) {
        validateField(edtProduct, edtProductLayout);
        validateField(edtPrice, edtPriceLayout);
        if(validateField(edtProduct, edtProductLayout) && validateField(edtPrice, edtPriceLayout)){
            Drink drink = new Drink(
                0, Objects.requireNonNull(edtProduct.getText()).toString(),
                    Double.parseDouble(Objects.requireNonNull(edtPrice.getText()).toString())
            );
            add(drink);
            clean();
        }
    }

    public void add(Drink drink){
        String URL = "http://192.168.0.17/projects/WineHouse/public/api/drinks";
        Ion.with(this)
        .load("POST", URL)
        .setBodyParameter("product", drink.getProduct())
        .setBodyParameter("price", Double.toString(drink.getPrice()))
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                try {
                    Toast.makeText(AddActivity.this, result.get("Status").getAsString(), Toast.LENGTH_SHORT).show();
                }catch(Exception error){
                    Toast.makeText(AddActivity.this, "Error caught", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateField(TextInputEditText edtField, TextInputLayout edtLayout){
        if(TextUtils.isEmpty(edtField.getText().toString())) {
            edtLayout.setError("Campo obrigatório");
            return false;
        }
        return true;
    }

    private void clean(){
        edtProduct.setText(null);
        edtPrice.setText(null);
        edtProductLayout.setError(null);
        edtPriceLayout.setError(null);
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }

    public void ivBack(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}