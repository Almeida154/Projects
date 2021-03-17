package com.example.firebaselogin.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaselogin.Models.ItemOrdered;
import com.example.firebaselogin.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    // Properties

    private List<ItemOrdered> itemsOrdered;
    private Context context;
    private OnItemClickListener clickListener;

    // Constructor

    public CartAdapter(List<ItemOrdered> itemsOrdered, Context context) {
        this.itemsOrdered = itemsOrdered;
        this.context = context;
    }

    // Interface

    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onSubtractItem(int position);
        void onAddItem(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    // View Holder

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductCart;
        TextView txvCartName;
        TextView txvCartQuantity;
        TextView txvCartPrice;
        TextView txvCartSubtotal;
        TextView txvCartSubtract;
        TextView txvCartAdd;
        LinearLayout llDiscard;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductCart = itemView.findViewById(R.id.ivProductCart);
            txvCartName = itemView.findViewById(R.id.txvCartName);
            txvCartQuantity = itemView.findViewById(R.id.txvCartQuantity);
            txvCartPrice = itemView.findViewById(R.id.txvCartPrice);
            txvCartSubtotal = itemView.findViewById(R.id.txvCartSubtotal);
        }

        public void bind(ItemOrdered itemOrdered, OnItemClickListener clickListener){
            txvCartName.setText(itemOrdered.getDrink().getName());
            txvCartQuantity.setText(String.format("Qnt: %s", itemOrdered.getQuantity()));
            txvCartPrice.setText(String.format("Un.: %s", toBrazilianCoin(itemOrdered.getDrink().getPrice())));
            txvCartSubtotal.setText(String.format("Subtotal: %s", toBrazilianCoin(itemOrdered.getSubTotal())));

            // Listeners

            (llDiscard = itemView.findViewById(R.id.llDiscard)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                            clickListener.onDeleteClick(getAdapterPosition());
                        }
                    }
                }
            });

            (txvCartSubtract = itemView.findViewById(R.id.txvCartSubtract)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                            clickListener.onSubtractItem(getAdapterPosition());
                        }
                    }
                }
            });

            (txvCartAdd = itemView.findViewById(R.id.txvCartAdd)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                            clickListener.onAddItem(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }

    // Methods

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ItemOrdered itemOrdered = itemsOrdered.get(position);
        holder.bind(itemOrdered, clickListener);
    }

    @Override
    public int getItemCount() {
        return itemsOrdered.size();
    }

    private static String toBrazilianCoin(double price){
        Locale ptBr = new Locale("pt", "BR");
        return NumberFormat.getCurrencyInstance(ptBr).format(price);
    }
}
