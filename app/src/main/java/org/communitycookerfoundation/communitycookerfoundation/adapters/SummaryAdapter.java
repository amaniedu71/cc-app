package org.communitycookerfoundation.communitycookerfoundation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.BasicReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportEntity;
import org.communitycookerfoundation.communitycookerfoundation.db.Entity.ReportListEntity;

import java.util.List;


public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    private List<BasicReportEntity> mFields; //cached copy
    private OnSummaryListener mOnSummaryListener;

    public SummaryAdapter(OnSummaryListener onSummaryListener){
        mOnSummaryListener = onSummaryListener;

    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_summary_item, parent, false);
        return new SummaryViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        if(mFields!= null){
            if(mFields.get(position) instanceof ReportEntity) {
                holder.prompt1ItemView.setText(( ((ReportEntity) mFields.get(position)).getPrompt()));
                holder.response1ItemView.setText(((ReportEntity) mFields.get(position)).getResponse());
            }
            else if(mFields.get(position) instanceof ReportListEntity){
                holder.prompt1ItemView.setText(((ReportListEntity) mFields.get(position)).getPrompt());
                String responseToPut = "";
                for(String resp:((ReportListEntity)mFields.get(position)).getResponses()){
                    responseToPut += resp+",";

                }
                holder.response1ItemView.setText(responseToPut);
            }

        }
        else
            holder.prompt1ItemView.setText("No reports submitted! ");
    }
    @Override
    public int getItemCount() {
        return mFields.size();
    }

    public void setFields(List<BasicReportEntity> reportEntity) {
        mFields = reportEntity;
        notifyDataSetChanged();
    }

    public class SummaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView prompt1ItemView;
        public final TextView response1ItemView;
        View mParentGroup;

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.prompt1ItemView = itemView.findViewById(R.id.prompt1_summary);
            this.response1ItemView = itemView.findViewById(R.id.response1_summary);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
        mOnSummaryListener.onReportListClick(getAdapterPosition());


        }
    }
    public interface OnSummaryListener {

        void onReportListClick(int reportId);

    }
}
