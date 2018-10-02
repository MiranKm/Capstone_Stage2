package com.example.miranpc.knowmore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miranpc.knowmore.Model.Fact;
import com.example.miranpc.knowmore.R;

import java.util.ArrayList;
import java.util.List;

public class SavedFactsAdapter extends RecyclerView.Adapter<SavedFactsAdapter.ViewHolder> {

    private static final String TAG = "SavedFactsAdapter";
    private Context context;
    private List<Fact> factList;
    private OnItemClicked itemClicked;

    public SavedFactsAdapter(Context context, OnItemClicked itemClicked) {
        this.context = context;
        this.itemClicked = itemClicked;

    }
    public List<Fact> getFact(){
        return factList;
    }


    public interface OnItemClicked {
        void ItemClicked(int pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_saved, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.linkTV.setText(factList.get(position).getResource());
        holder.factsTV.setText(factList.get(position).getFact());
        Log.e("FACT", factList.get(position).getFact());
    }

    @Override
    public int getItemCount() {
        return factList == null ? 0 : factList.size();
    }

    public void setFacts(List<Fact> fact) {
        this.factList = fact;
        notifyDataSetChanged();
    }

    public void clearList() {
        if (factList == null) {
            factList = new ArrayList<>();
        } else {
            int itemCount = factList.size();
            factList.clear();
            notifyItemRangeRemoved(0, itemCount);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView factsTV, linkTV;

        ViewHolder(View itemView) {
            super(itemView);
            factsTV = itemView.findViewById(R.id.fact_tv);
            linkTV = itemView.findViewById(R.id.link_tv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int elementId = factList.get(getAdapterPosition()).getId();
            itemClicked.ItemClicked(elementId);
        }
    }
}
