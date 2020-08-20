package org.communitycookerfoundation.communitycookerfoundation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;

import java.util.List;
import java.util.Map;


public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    private ReportEntity mFields; //cached copy

    public SummaryAdapter(Context context){

    }


    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new SummaryViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        if(mFields!= null){
            holder.prompt1ItemView.setText(mFields.getPrompt1());
            holder.response1ItemView.setText(mFields.getLiters());


        }
        else
            holder.prompt1ItemView.setText("No reports submitted! ");


    }
    @Override
    public int getItemCount() {
        return 1;
    }

    public void setFields(ReportEntity reportEntity) {
        mFields = reportEntity;
        notifyDataSetChanged();
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        public final TextView prompt1ItemView;
        public final TextView response1ItemView;




        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.prompt1ItemView = itemView.findViewById(R.id.prompt1_summary);
            this.response1ItemView = itemView.findViewById(R.id.response1_summary);

        }
    }
}
