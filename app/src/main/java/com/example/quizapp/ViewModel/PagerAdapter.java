package com.example.quizapp.ViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;


public class PagerAdapter  extends FragmentStateAdapter {

    List<Fragment> fragmentList;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return fragmentList.get(0);
        } else if (position == 1) {
            return fragmentList.get(1);
        } else {
            return fragmentList.get(2);
        }
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
