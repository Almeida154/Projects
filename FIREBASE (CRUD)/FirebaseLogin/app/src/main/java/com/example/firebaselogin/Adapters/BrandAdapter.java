package com.example.firebaselogin.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaselogin.Models.Brand;
import com.example.firebaselogin.R;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    // Properties

    List<Brand> brands;
    Context context;
    DrinkAdapter drinkAdapter;

    // Constructor

    public BrandAdapter(List<Brand> brands, Context context) {
        this.brands = brands;
        this.context = context;
    }

    // ViewHolder

    public class BrandViewHolder extends RecyclerView.ViewHolder {

        TextView txvTitleBrand;
        RecyclerView recyclerViewRow;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            txvTitleBrand = itemView.findViewById(R.id.txvTitleBrand);
            recyclerViewRow = itemView.findViewById(R.id.recyclerViewItem);
        }

        public void bind(Brand brand, Context context){
            txvTitleBrand.setText(brand.getDesc());

            drinkAdapter = new DrinkAdapter(brand.getDrinkArrayList(), context);
            recyclerViewRow.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            recyclerViewRow.setAdapter(drinkAdapter);

        }

    }

    // Methods

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_row, parent, false);
        return new BrandViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brands.get(position);
        holder.bind(brand, context);
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

}
