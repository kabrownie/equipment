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

import java.text.BreakIterator;
import java.util.ArrayList;

public class ItemsAdapter2 extends RecyclerView.Adapter<ItemsAdapter2.ViewHolder> {

    private final Context context;
    private final ArrayList<ItemModel> itemModelArrayList;

    CardView card;

    public ItemsAdapter2(Context context, ArrayList<ItemModel> itemModelArrayList) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
    }

    @NonNull
    @Override
    public ItemsAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card, parent, false);

        card = view.findViewById(R.id.items_card);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView name = view.findViewById(R.id.item_name);
                TextView price = view.findViewById(R.id.item_price);
                TextView description = view.findViewById(R.id.itemDescription);
                TextView phone = view.findViewById(R.id.item_phone);
                TextView email = view.findViewById(R.id.item_email);
                TextView itemid = view.findViewById(R.id.item_Id);


                String getName = name.getText().toString();
                String getPrice = price.getText().toString();
                String getDesc = description.getText().toString();
                String getPhone =phone.getText().toString();
                String getEmail =email.getText().toString();
                String getId =itemid.getText().toString();




                Intent intent = new Intent(context.getApplicationContext(), Return.class);
                intent.putExtra("Item Name", getName);
                intent.putExtra("Item Price", getPrice);
                intent.putExtra("Item Description", getDesc);
                intent.putExtra("Phone", getPhone);
                intent.putExtra("email", getEmail);
                intent.putExtra("Item Id", getId);

                context.startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter2.ViewHolder holder, int position) {

        ItemModel model = itemModelArrayList.get(position);
        holder.name.setText(model.getItemName());
        holder.price.setText(model.getItemPrice());
        holder.description.setText(model.getItemDescription());
        holder.itemid.setText(model.getItem_id());
        holder.email.setText(model.getItemEmail());
        holder.phone.setText(model.getItemPhone());




    }

    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name ,price,itemid,email,phone;


        private final TextView description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            description = itemView.findViewById(R.id.itemDescription);
            itemid = itemView.findViewById(R.id.item_Id);
            email = itemView.findViewById(R.id.item_email);
            phone = itemView.findViewById(R.id.item_phone);

        }
    }
}
