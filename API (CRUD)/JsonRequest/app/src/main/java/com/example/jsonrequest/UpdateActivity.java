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

public class UpdateActivity extends AppCompatActivity {

    TextInputLayout edtIdLayout;
    TextInputLayout edtProductLayout;
    TextInputLayout edtPriceLayout;
    TextInputEditText edtId;
    TextInputEditText edtProduct;
    TextInputEditText edtPrice;
    Drink drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtIdLayout = findViewById(R.id.edtIdLayoutUpdt);
        edtProductLayout = findViewById(R.id.edtProductLayoutUpdt);
        edtPriceLayout = findViewById(R.id.edtPriceLayoutUpdt);
        edtId = findViewById(R.id.edtIdUpdt);
        edtProduct = findViewById(R.id.edtProductUpdt);
        edtPrice = findViewById(R.id.edtPriceUpdt);

        if(getIntent().getExtras() != null){
            drink = (Drink) getIntent().getSerializableExtra("drinkSerializable");
            edtId.setText(String.format("%d", drink.getId()));
            edtProduct.setText(drink.getProduct());
            edtPrice.setText(String.format("%.2f", drink.getPrice()));
        }
    }

    public void btnUpdate(View view) {
        validateField(edtId, edtIdLayout);
        validateField(edtProduct, edtProductLayout);
        validateField(edtPrice, edtPriceLayout);
        if(validateField(edtProduct, edtProductLayout) && validateField(edtPrice, edtPriceLayout)
            && validateField(edtId, edtIdLayout)) {
            Drink drink = new Drink(
                    Integer.parseInt(Objects.requireNonNull(edtId.getText()).toString()),
                    Objects.requireNonNull(edtProduct.getText()).toString(),
                    Double.parseDouble(Objects.requireNonNull(edtPrice.getText()).toString())
            );
            update(drink);
        }
    }

    private void update(Drink drink) {
        String URL = "http://192.168.0.17/projects/WineHouse/public/api/drinks/" + drink.getId();
        Ion.with(this)
        .load("PUT", URL)
        .setBodyParameter("product", drink.getProduct())
        .setBodyParameter("price", Double.toString(drink.getPrice()))
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                try {
                    Toast.makeText(UpdateActivity.this, result.get("Status").getAsString(), Toast.LENGTH_SHORT).show();
                }catch(Exception error){
                    Toast.makeText(UpdateActivity.this, "Error caught", Toast.LENGTH_SHORT).show();
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