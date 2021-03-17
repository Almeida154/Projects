package com.example.firebaselogin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.firebaselogin.Adapters.CouponAdapter;
import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.Models.Coupon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import maes.tech.intentanim.CustomIntent;

public class CouponsActivity extends AppCompatActivity {

    DatabaseReference couponsFIREBASE = FirebaseDatabase.getInstance().getReference("Coupons");

    List<Coupon> coupons;
    CouponAdapter couponAdapter;

    double currentPrice;

    RecyclerView recyclerViewCoupon;
    TextView txvCouponsEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        new FunctionController(this).changeStatusBar(Integer.toHexString(getColor(R.color.white)));

        recyclerViewCoupon = findViewById(R.id.RecyclerViewCoupons);
        txvCouponsEmpty = findViewById(R.id.txvCouponsEmpty);

        coupons = new ArrayList<>();

        getExtras();
        populate();
    }

    public void getExtras(){
        if(getIntent().hasExtra("currentPrice")){
            currentPrice = getIntent().getDoubleExtra("currentPrice", 0);
        }
    }

    public void populate(){
        couponsFIREBASE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                coupons.clear();
                for(DataSnapshot couponSnap : snapshot.getChildren()){
                    coupons.add(couponSnap.getValue(Coupon.class));
                }
                buildRecyclerView();
                handling();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", "loadPost:onCancelled", error.toException());
            }

        });
    }

    public void buildRecyclerView(){
        couponAdapter = new CouponAdapter(coupons, this, currentPrice);
        recyclerViewCoupon.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCoupon.setHasFixedSize(true);
        recyclerViewCoupon.setAdapter(couponAdapter);
    }

    public void handling(){
        if(coupons.size() == 0){
            recyclerViewCoupon.setVisibility(View.INVISIBLE);
            txvCouponsEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerViewCoupon.setVisibility(View.VISIBLE);
            txvCouponsEmpty.setVisibility(View.INVISIBLE);
        }
    }

    public void imBack(View view) {
        finish();
        Intent it = new Intent(this, CartActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(this, CartActivity.class);
        startActivity(it);
        CustomIntent.customType(this, "right-to-left");
    }
}