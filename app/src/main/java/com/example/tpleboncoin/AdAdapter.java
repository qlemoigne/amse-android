package com.example.tpleboncoin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.example.tpleboncoin.AdModel;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;
import java.util.logging.Logger;

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

        // partie load image
        if(data.isInvalidImage())
        {
            holder.image.setImageResource(R.drawable.image0);

        } else {
            if(data.isImageLoaded())
            {
                holder.image.setImageBitmap(data.getCachedImage());
            } else {
                if(data.isLocal()) {
                    holder.image.setImageURI(Uri.parse(MainActivity.CACHE_DIR + "/" + data.getImage()));
                } else {

                    DownloadImageTask task = new DownloadImageTask() {
                        @Override
                        protected Bitmap doInBackground(String... urls) {
                            return super.doInBackground(urls);
                        }

                        @Override
                        protected void onPostExecute(Bitmap result) {

                            data.setCachedImage(result);
                            holder.image.setImageBitmap(result);


                        }
                    };

                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data.getImage());
                }
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AdViewActivity.class);

                data.invalidateCache();

                intent.putExtra("ad", data);

                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Supprimer");
                builder.setMessage("Voulez-vous supprimer l'annonce ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Annonce supprimé !", Toast.LENGTH_LONG).show();
                        // Suppression de l'annonce
                        DBManager.getDBManager(context).delete(data.getID());
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return true;
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

