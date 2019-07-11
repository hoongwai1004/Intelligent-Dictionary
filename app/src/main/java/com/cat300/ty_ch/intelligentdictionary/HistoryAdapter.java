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


class HistoryRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView historyItemView;
    private ItemClickListener historyItemClickListener;

    public HistoryRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        historyItemView = (TextView)itemView.findViewById(R.id.historyItem);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setHistoryItemClickListener(ItemClickListener historyItemClickListener) {
        this.historyItemClickListener = historyItemClickListener;
    }

    @Override
    public void onClick(View view) {
        historyItemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        historyItemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }
}

public class HistoryAdapter extends RecyclerView.Adapter<HistoryRecyclerViewHolder> {

    private List<String> historyData = new ArrayList<>();
    private Context context;
    HistoryDBHelper myDb;
    public HistoryAdapter(List<String> historyData, Context context) {
        this.historyData = historyData;
        this.context = context;
        myDb = new HistoryDBHelper(context);
    }

    @NonNull
    @Override
    public HistoryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater historyLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        View historyView = historyLayoutInflater.inflate(R.layout.history_list_item, viewGroup, false);
        Toast.makeText(context, "Long click to delete", Toast.LENGTH_SHORT).show();
        return new HistoryRecyclerViewHolder(historyView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryRecyclerViewHolder historyRecyclerViewHolder, int historyItemPosition) {
        historyRecyclerViewHolder.historyItemView.setText(historyData.get(historyItemPosition));
        historyRecyclerViewHolder.setHistoryItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int historyItemPosition, boolean isLongClick) {
                if(isLongClick) {
                    Toast.makeText(context, "Word: " + historyData.get(historyItemPosition) + " is deleted. ", Toast.LENGTH_SHORT).show();
                    myDb.deleteData(historyData.get(historyItemPosition));
                    historyData.remove(historyItemPosition);
                    notifyItemRemoved(historyItemPosition);
                    notifyItemRangeChanged(historyItemPosition, historyData.size());
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, "Short Click: " + historyData.get(historyItemPosition), Toast.LENGTH_SHORT).show();
                    String textText = historyData.get(historyItemPosition);
                    Intent intent = new Intent(view.getContext(), LemmatronActivity.class);
                    intent.putExtra("Term",textText);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }
}