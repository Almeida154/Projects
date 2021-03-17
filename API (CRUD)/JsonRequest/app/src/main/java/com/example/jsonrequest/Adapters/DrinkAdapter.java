package com.example.jsonrequest.Adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jsonrequest.Models.Drink;
import com.example.jsonrequest.R;
import com.example.jsonrequest.UpdateActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    // Properties

    private List<Drink> drinks;
    private Context context;

    // Constructor

    public DrinkAdapter(List<Drink> drinks, Context context) {
        this.drinks = drinks;
        this.context = context;
    }

    // View Holder

    public class DrinkViewHolder extends RecyclerView.ViewHolder {

        TextView txvProduct;
        TextView txvPrice;
        ImageView ivUpdate;
        ImageView ivDelete;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            txvProduct = itemView.findViewById(R.id.txvProduct);
            txvPrice = itemView.findViewById(R.id.txvPrice);
            ivUpdate = itemView.findViewById(R.id.ivUpdate);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

        public void bind(Drink drink, Context context, int position){
            txvProduct.setText(drink.getProduct());
            txvPrice.setText(toBrazilianCoin(drink.getPrice()));

            ivUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(context, UpdateActivity.class);
                    it.putExtra("drinkSerializable", drink);
                    context.startActivity(it);
                    ((AppCompatActivity)context).finish();
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    dialog(drink, position);
                }
            });

        }

        private void dialog(Drink drink, int position){
            String title = "<h1><font>DELETAR?</font></h1>";
            String message = "<big><font><b>" + drink.getProduct() + "</b> será deletado. Certeza?</font></big>";
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context, R.style.dialog);
            dialogBuilder.setTitle(Html.fromHtml(title));
            dialogBuilder.setMessage(Html.fromHtml(message));
            dialogBuilder.setIcon(R.drawable.ic_delete);
            dialogBuilder.setBackground(context.getResources().getDrawable(R.drawable.dialog_bg));
            dialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    delete(drink, context, position);
                }
            });
            dialogBuilder.setNegativeButton("Não", null);
            dialogBuilder.show();
        }

        private void delete(Drink drink, Context context, int i){
            String URL = "http://192.168.0.17/projects/WineHouse/public/api/drinks/" + drink.getId();
            Ion.with(context)
            .load("DELETE", URL)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    try {
                        Toast.makeText(context, result.get("Status").getAsString(), Toast.LENGTH_SHORT).show();
                        drinks.remove(i);
                        notifyDataSetChanged();
                    }catch(Exception error){
                        Toast.makeText(context, "Error caught", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Methods

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_item, parent, false);
        return new DrinkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Drink drink = drinks.get(position);
        holder.bind(drink, context, position);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    private static String toBrazilianCoin(double price){
        Locale ptBr = new Locale("pt", "BR");
        return NumberFormat.getCurrencyInstance(ptBr).format(price);
    }

}
