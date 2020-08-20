package org.communitycookerfoundation.communitycookerfoundation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.util.AdminHomeChoicesData;

import java.util.List;
import java.util.Map;


public class AdminHomeChoiceAdapter extends RecyclerView.Adapter<AdminHomeChoiceAdapter.ChoiceViewHolder> {


    private List<String> mChoices;
    private OnUserListener mOnUserListener;
    private AdminHomeChoicesData mAdminHomeChoicesData;
    public AdminHomeChoiceAdapter( OnUserListener onUserListener){
        this.mOnUserListener = onUserListener;
        mAdminHomeChoicesData = new AdminHomeChoicesData();
        mChoices = mAdminHomeChoicesData.getOptions();


    }


    @NonNull
    @Override
    public ChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_home_layout, parent, false);
        return new ChoiceViewHolder(itemView, mOnUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceViewHolder holder, int position) {
        if(mChoices != null){

            String currentChoice = mChoices.get(position);
            holder.userItemView.setText(currentChoice);

        }
    }

    /*public void setChoice(List<Map<String, Object>> users){


        mUsers = users;
        notifyDataSetChanged();
    }*/
    @Override
    public int getItemCount() {
        return mChoices.size();
    }

    public static class ChoiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView userItemView;
        View mParentGroup;
        OnUserListener mOnUserListener;


        public ChoiceViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            this.mOnUserListener = onUserListener;
            this.mParentGroup = itemView;
            this.userItemView = mParentGroup.findViewById(R.id.home_recycler_text);
            mParentGroup.setOnClickListener( this);

        }

        @Override
        public void onClick(View v) {
            mOnUserListener.onUserClick(getAdapterPosition());

        }
    }

    public interface OnUserListener{
        void onUserClick(int position);
    }
}
