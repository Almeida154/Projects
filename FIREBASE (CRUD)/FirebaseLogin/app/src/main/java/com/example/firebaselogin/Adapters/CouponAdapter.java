package com.example.firebaselogin.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaselogin.CartActivity;
import com.example.firebaselogin.Controllers.FunctionController;
import com.example.firebaselogin.CouponsActivity;
import com.example.firebaselogin.Models.Coupon;
import com.example.firebaselogin.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    // Properties

    private List<Coupon> coupons;
    private Context context;
    private double currentPrice;

    // Constructor

    public CouponAdapter(List<Coupon> coupons, Context context, double currentPrice) {
        this.coupons = coupons;
        this.context = context;
        this.currentPrice = currentPrice;
    }

    // ViewHolder

    public class CouponViewHolder extends RecyclerView.ViewHolder {

        TextView txvCouponName;
        TextView txvCouponDiscount;
        TextView txvCouponMinPrice;
        TextView txvCouponAvailability;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            txvCouponName = itemView.findViewById(R.id.txvCouponName);
            txvCouponDiscount = itemView.findViewById(R.id.txvCouponDiscount);
            txvCouponMinPrice = itemView.findViewById(R.id.txvCouponMinPrice);
            txvCouponAvailability = itemView.findViewById(R.id.txvCouponAvailability);
        }

        public void bind(Coupon coupon){
            txvCouponName.setText(coupon.getName());
            txvCouponDiscount.setText(String.format("Desconto: R$ %s", toBrazilianCoin(coupon.getDiscount())));
            txvCouponMinPrice.setText(String.format("Valor mínimo: R$ %s", toBrazilianCoin(coupon.getMinPrice())));

            if(coupon.getMinPrice() == 0){
                txvCouponMinPrice.setVisibility(View.INVISIBLE);
            }

            if(currentPrice >= coupon.getMinPrice()) {
                txvCouponAvailability.setText("Disponível");
                txvCouponAvailability.setTextColor(context.getColor(R.color.green));
            } else {
                txvCouponAvailability.setText("Indisponível");
                txvCouponAvailability.setTextColor(context.getColor(R.color.red));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currentPrice >= coupon.getMinPrice()) {
                        Intent it = new Intent(context.getApplicationContext(), CartActivity.class);
                        it.putExtra("discount", coupon.getDiscount());
                        context.startActivity(it);
                        ((AppCompatActivity)context).finish();
                    } else {
                        Toast.makeText(context, "Sua compra ainda não atingiu " + toBrazilianCoin(coupon.getMinPrice()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    // Methods

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_item, parent, false);
        return new CouponViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        Coupon coupon = coupons.get(position);
        holder.bind(coupon);
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    private static String toBrazilianCoin(double price){
        Locale ptBr = new Locale("pt", "BR");
        return NumberFormat.getCurrencyInstance(ptBr).format(price);
    }
}
