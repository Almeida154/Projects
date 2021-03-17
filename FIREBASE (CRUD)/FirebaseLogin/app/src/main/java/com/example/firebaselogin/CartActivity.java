package com.example.firebaselogin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.firebaselogin.Adapters.CartAdapter;
import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Models.ItemOrdered;
import java.util.ArrayList;
import java.util.List;
import maes.tech.intentanim.CustomIntent;

public class CartActivity extends AppCompatActivity {

    static List<ItemOrdered> itemsOrdered;
    CartAdapter cartAdapter;

    RecyclerView recyclerViewCartItem;
    TextView txvCartEmpty;
    TextView txvTotal;
    RelativeLayout rlContainerDiscount;

    double discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.white)));

        recyclerViewCartItem = findViewById(R.id.recyclerViewCartItem);
        txvCartEmpty = findViewById(R.id.txvCartEmpty);
        txvTotal = findViewById(R.id.txvTotal);
        rlContainerDiscount = findViewById(R.id.rlContainerDiscount);

        getExtras();
        handling();
        buildRecyclerView();
        onClickCoupons();
    }

    public void getExtras(){
        if(getIntent().hasExtra("discount")) discount = getIntent().getDoubleExtra("discount", 0);
    }

    public void btnCheckOut(View view) {
        if(itemsOrdered.size() != 0){
            Toast.makeText(this, "Da pra ta comprando kk", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Carrinho vazio irmão", Toast.LENGTH_SHORT).show();
        }
    }

    public void handling(){
        itemsOrdered = (itemsOrdered != null) ? itemsOrdered : new ArrayList<>();

        if(itemsOrdered.size() == 0){
            recyclerViewCartItem.setVisibility(View.INVISIBLE);
            txvCartEmpty.setVisibility(View.VISIBLE);
        } else {
            new FunctionController(this);
            recyclerViewCartItem.setVisibility(View.VISIBLE);
            txvCartEmpty.setVisibility(View.INVISIBLE);
            txvTotal.setText(FunctionController.toBrazilianCoin(sumCart(itemsOrdered) - discount));
        }
    }

    public void buildRecyclerView(){
        cartAdapter = new CartAdapter(itemsOrdered, this);
        recyclerViewCartItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCartItem.setHasFixedSize(true);
        recyclerViewCartItem.setAdapter(cartAdapter);

        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onSubtractItem(int position) {
                subtractItem(position);
            }

            @Override
            public void onAddItem(int position) {
                addItem(position);
            }
        });
    }

    public void removeItem(int position){
        itemsOrdered.remove(position);
        cartAdapter.notifyItemRemoved(position);
        txvTotal.setText(FunctionController.toBrazilianCoin(sumCart(itemsOrdered)));
        handling();
    }

    public void subtractItem(int position){
        if(itemsOrdered.get(position).getQuantity() > 1){
            itemsOrdered.get(position).setQuantity(
                    itemsOrdered.get(position).getQuantity() - 1
            );
            cartAdapter.notifyItemChanged(position);
            txvTotal.setText(FunctionController.toBrazilianCoin(sumCart(itemsOrdered)));
        }
    }

    public void addItem(int position){
        if(itemsOrdered.get(position).getQuantity() < 20){
            itemsOrdered.get(position).setQuantity(
                    itemsOrdered.get(position).getQuantity() + 1
            );
            cartAdapter.notifyItemChanged(position);
            txvTotal.setText(FunctionController.toBrazilianCoin(sumCart(itemsOrdered)));
        }
    }

    public double sumCart(List<ItemOrdered> itemsOrdered){
        double sum = 0;
        for(ItemOrdered itemOrdered : itemsOrdered){
            sum += itemOrdered.getSubTotal();
        }
        return sum;
    }

    public void onClickCoupons(){
        rlContainerDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CartActivity.this, CouponsActivity.class);
                it.putExtra("currentPrice", sumCart(itemsOrdered));
                startActivity(it);
                finish();
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