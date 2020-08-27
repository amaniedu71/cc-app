package org.communitycookerfoundation.communitycookerfoundation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.communitycookerfoundation.communitycookerfoundation.R;
import org.communitycookerfoundation.communitycookerfoundation.ui.prompt.PromptFragment;

public class UserPromptAdapter extends FragmentStateAdapter {


    public UserPromptAdapter(Fragment Promptfragment) {
        super(Promptfragment);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new PromptFragment();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
