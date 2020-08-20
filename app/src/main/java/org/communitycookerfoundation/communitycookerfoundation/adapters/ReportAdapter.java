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


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<Map<String,Object>> mReports; //cached copy

    public ReportAdapter(Context context){

    }


    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    public void setReports(List<Map<String,Object>> reports){
        mReports = reports;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if(mReports!= null && mReports.size()>0){
            String tempHeading = "date: " +  mReports.get(position).get("report_date");


            holder.reportHeading.setText(tempHeading);
            holder.prompt1ItemView.setText((String) mReports.get(position).get("report_prompt"));
            holder.response1ItemView.setText((String) mReports.get(position).get("report_response"));


        }
        else
            holder.reportHeading.setText("No reports submitted! ");


    }
    @Override
    public int getItemCount() {
        if(mReports != null)
            return mReports.size();
        else
            return 1;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        public final TextView reportHeading;
        public final TextView prompt1ItemView;
        public final TextView response1ItemView;



        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reportHeading = itemView.findViewById(R.id.report_heading);
            this.prompt1ItemView = itemView.findViewById(R.id.prompt1_text);
            this.response1ItemView = itemView.findViewById(R.id.resp1_text);
        }
    }
}
