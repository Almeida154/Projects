package com.example.firebaselogin.Adapters;
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
import com.example.firebaselogin.ModelDrinkActivity;
import com.example.firebaselogin.Models.Drink;
import com.example.firebaselogin.R;
import com.example.firebaselogin.UpdateActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    // Properties

    DatabaseReference drinksFIREBASE = FirebaseDatabase.getInstance().getReference("Drinks");
    List<Drink> drinks;
    Context context;

    // Constructor

    public DrinkAdapter(List<Drink> drinks, Context context) {
        this.drinks = drinks;
        this.context = context;
    }

    // View Holder

    public class DrinkViewHolder extends RecyclerView.ViewHolder {

        TextView txvProduct;
        TextView txvPrice;
        TextView txvId;
        ImageView ivUpdate;
        ImageView ivDelete;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            txvProduct = itemView.findViewById(R.id.txvProduct);
            txvPrice = itemView.findViewById(R.id.txvCartPrice);
            txvId = itemView.findViewById(R.id.txvId);
            ivUpdate = itemView.findViewById(R.id.ivUpdate);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

        public void bind(Drink drink, Context context, int position){
            txvProduct.setText(drink.getName());
            txvPrice.setText(toBrazilianCoin(drink.getPrice()));
            txvId.setText(drink.getId());
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(context, ModelDrinkActivity.class);
                    it.putExtra("drink", drink);
                    context.startActivity(it);
                }
            });

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
            String message = "<big><font><b>" + drink.getName() + "</b> será deletado. Certeza?</font></big>";
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
            drinksFIREBASE.child(drink.getId()).removeValue()
                .addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Deletado com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show();
                    }
                });

            drinks.remove(i);
            notifyDataSetChanged();

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
