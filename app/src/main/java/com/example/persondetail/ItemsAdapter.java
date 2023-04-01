package com.example.persondetail;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ItemModel> itemModelArrayList;

    CardView card;

    public ItemsAdapter(Context context, ArrayList<ItemModel> itemModelArrayList) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card, parent, false);

        card = view.findViewById(R.id.items_card);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView name = view.findViewById(R.id.item_name);
                TextView price = view.findViewById(R.id.item_price);
                TextView description = view.findViewById(R.id.itemDescription);
                String getName = name.getText().toString();
                String getPrice = price.getText().toString();
                String getDesc = description.getText().toString();

                Intent intent = new Intent(context.getApplicationContext(), Description.class);
                intent.putExtra("name", getName);
                intent.putExtra("price", getPrice);
                intent.putExtra("description", getDesc);
                context.startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        ItemModel model = itemModelArrayList.get(position);
        holder.name.setText(model.getItemName());
        holder.price.setText(model.getItemPrice());
        holder.description.setText(model.getItemDescription());
    }

    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;

        private final TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            description = itemView.findViewById(R.id.itemDescription);
        }
    }
}
