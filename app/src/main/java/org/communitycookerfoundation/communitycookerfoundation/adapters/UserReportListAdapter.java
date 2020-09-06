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


public class UserReportListAdapter extends RecyclerView.Adapter<UserReportListAdapter.ReportViewHolder> {

    private List<ReportEntity> mReports; //cached copy

    public UserReportListAdapter(Context context){


    }


    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if(mReports!= null){

            ReportEntity current = mReports.get(position);
            holder.reportItemView.setText(current.getResponse());

        }
        else
            holder.reportItemView.setText("No reports added yet");

    }

    public void setReports(List<ReportEntity> reports){

        mReports = reports;
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        if(mReports != null)
            return mReports.size();
        else
            return 1;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        public final TextView reportItemView;



        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reportItemView = itemView.findViewById(R.id.recycler_text1);
        }
    }
}
