package com.quentin.tplbc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.quentin.tplbc.models.AdModel;

import java.util.ArrayList;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.AdViewHolder> {

    private final Context context;
    private final ArrayList<AdModel> adModelArrayList;

    public AdAdapter(Context context, ArrayList<AdModel> adModelArrayList) {
        this.context = context;
        this.adModelArrayList = adModelArrayList;
    }



    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.ad_fragment, parent, false);
        AdViewHolder viewHolder = new AdViewHolder(listItem);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {

        final AdModel data = adModelArrayList.get(position);
        holder.title.setText(data.getTitle());
        holder.address.setText(data.getAddress());
        if(data.isInvalidImage())
        {
            holder.image.setImageResource(R.drawable.image1);

        }
        /*holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AdViewActivity.class);
                intent.putExtra("ad", data);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adModelArrayList.size();
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView address;
        public ShapeableImageView image;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            address = itemView.findViewById(R.id.txt_address);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
