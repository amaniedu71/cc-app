package org.communitycookerfoundation.communitycookerfoundation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserReportListAdapter extends RecyclerView.Adapter<UserReportListAdapter.ReportViewHolder> {


    private HashMap<String,Object> mCurrentReport;

    public UserReportListAdapter(Context context){
    }


    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    public void setReports(HashMap<String, Object> currentReport){
        if(currentReport != null) currentReport.remove("report_date");
        mCurrentReport = currentReport;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if(mCurrentReport!= null && mCurrentReport.keySet().size()>0){

            List<String> key = new ArrayList<>(mCurrentReport.keySet());
            int questionNumber = position+1;
            String heading = "Question " + questionNumber;
            String prompt = key.get(position);
            String response = (String) mCurrentReport.get(prompt);
            holder.reportHeading.setText(heading);
            holder.responseText.setText(response);
            holder.promptText.setText(prompt);


        }
        else
            holder.reportHeading.setText("No reports submitted! ");


    }
    @Override
    public int getItemCount() {
        if(mCurrentReport != null)
            return mCurrentReport.keySet().size();
        else
            return 1;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        public final TextView reportHeading;
        public final TextView promptText;
        public final TextView responseText;


        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reportHeading = itemView.findViewById(R.id.report_heading);
            this.promptText = itemView.findViewById(R.id.prompt1_text);
            this.responseText = itemView.findViewById(R.id.resp1_text);
        }

    }

}
