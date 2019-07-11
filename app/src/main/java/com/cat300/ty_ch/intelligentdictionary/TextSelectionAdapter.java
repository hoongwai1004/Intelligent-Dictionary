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

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView itemSelectionView;
    private ItemClickListener itemClickListener;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        itemSelectionView = (TextView)itemView.findViewById(R.id.text_selection_item);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }
}

public class TextSelectionAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<String> textSelectionData = new ArrayList<>();
    private Context context;
    HistoryDBHelper myDb;
    public TextSelectionAdapter(List<String> textSelectionData, Context context) {
        this.textSelectionData = textSelectionData;
        this.context = context;
        myDb = new HistoryDBHelper(context);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater textSelectionLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = textSelectionLayoutInflater.inflate(R.layout.text_selection_item, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int itemPosition) {
        recyclerViewHolder.itemSelectionView.setText(textSelectionData.get(itemPosition));
        recyclerViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int itemPosition, boolean isLongClick) {
                if(isLongClick) {
                    Toast.makeText(context, "Long Click: " + textSelectionData.get(itemPosition), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Short Click: " + textSelectionData.get(itemPosition), Toast.LENGTH_SHORT).show();
                    myDb.insertData(textSelectionData.get(itemPosition));
                    String textText = textSelectionData.get(itemPosition);
                    Intent intent = new Intent(view.getContext(), LemmatronActivity.class);
                    intent.putExtra("Term",textText);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return textSelectionData.size();
    }
}