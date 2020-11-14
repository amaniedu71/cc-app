package org.communitycookerfoundation.communitycookerfoundation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportViewHolder> {

    private final OnReportClickListener mOnReportClickListener;
    private List<Map<String,Object>> mReports; //cached copy
    private static final String TAG = "ReportListAdapter"  ;
    public ReportListAdapter(OnReportClickListener onReportClickListener){
        mOnReportClickListener = onReportClickListener;
    }


    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.user_report_date, parent, false);
        return new ReportViewHolder(itemView);
    }

    public void setReports(List<Map<String,Object>> reports){
        mReports = reports;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if(mReports!= null && mReports.size()>0){
            String tempHeading = (String) mReports.get(position).get("report_date");

            holder.reportHeading.setText(tempHeading);


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

    public class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView reportHeading;




        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reportHeading = itemView.findViewById(R.id.report_heading);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "clicked Report!");
            mOnReportClickListener.onReportClick(mReports.get(getAdapterPosition()));
        }
    }
    public interface OnReportClickListener {

        void onReportClick(Map<String,Object> chosenReport);


    }
}
