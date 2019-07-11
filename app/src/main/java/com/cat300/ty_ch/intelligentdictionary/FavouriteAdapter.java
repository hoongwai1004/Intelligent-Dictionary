package com.cat300.ty_ch.intelligentdictionary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class FavouriteRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView favouriteItemView;
    private ItemClickListener favouriteItemClickListener;

    public FavouriteRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        favouriteItemView  =(TextView)itemView.findViewById(R.id.favouriteItem);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setFavouriteItemClickListener(ItemClickListener favouriteItemClickListener){
        this.favouriteItemClickListener = favouriteItemClickListener;
    }

    @Override
    public void onClick(View view) {
        favouriteItemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        favouriteItemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }
}

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteRecyclerViewHolder> {

    private List<String> favouriteData = new ArrayList<>();
    private Context context;
    FavoriteDBHelper myDb;

    public FavouriteAdapter(List<String> favouriteData, Context context){
        this.favouriteData = favouriteData;
        this.context = context;
        myDb = new FavoriteDBHelper(context);
    }

    @NonNull
    @Override
    public FavouriteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater favouriteLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        View favouriteView = favouriteLayoutInflater.inflate(R.layout.favourite_list_item, viewGroup, false);
        Toast.makeText(context, "Long click to delete", Toast.LENGTH_SHORT).show();
        return new FavouriteRecyclerViewHolder(favouriteView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteRecyclerViewHolder favouriteRecyclerViewHolder, int favouritePosition) {
        favouriteRecyclerViewHolder.favouriteItemView.setText(favouriteData.get(favouritePosition));
        favouriteRecyclerViewHolder.setFavouriteItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int favouritePosition, boolean isLongClick) {
                if(isLongClick) {
                    Toast.makeText(context, "Word: " + favouriteData.get(favouritePosition) + " is deleted. ", Toast.LENGTH_SHORT).show();
                    myDb.deleteData(favouriteData.get(favouritePosition));
                    favouriteData.remove(favouritePosition);
                    notifyItemRemoved(favouritePosition);
                    notifyItemRangeChanged(favouritePosition, favouriteData.size());
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, "Short Click: " + favouriteData.get(favouritePosition), Toast.LENGTH_SHORT).show();
                    String textText = favouriteData.get(favouritePosition);
                    Intent intent = new Intent(view.getContext(), LemmatronActivity.class);
                    intent.putExtra("Term",textText);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteData.size();
    }
}