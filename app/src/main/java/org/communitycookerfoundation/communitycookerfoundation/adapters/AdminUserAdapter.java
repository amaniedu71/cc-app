package org.communitycookerfoundation.communitycookerfoundation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.communitycookerfoundation.communitycookerfoundation.R;

import java.util.List;
import java.util.Map;


public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.UserViewHolder> {

    private List<Map<String, Object>> mUsers; //cached copy
    private OnUserListener mOnUserListener;

    public AdminUserAdapter( OnUserListener onUserListener){
        this.mOnUserListener = onUserListener;


    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new UserViewHolder(itemView, mOnUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if(mUsers != null){

            String currentUser = (String) mUsers.get(position).get("name");
            holder.userItemView.setText(currentUser);

        }
        else
            holder.userItemView.setText("No users added yet");

    }

    public void setUsers(List<Map<String, Object>> users){


        mUsers = users;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mUsers != null)
            return mUsers.size();
        else
            return 1;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView userItemView;
        View mParentGroup;
        OnUserListener mOnUserListener;


        public UserViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            this.mOnUserListener = onUserListener;
            this.mParentGroup = itemView;
            this.userItemView = mParentGroup.findViewById(R.id.recycler_text1);
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
