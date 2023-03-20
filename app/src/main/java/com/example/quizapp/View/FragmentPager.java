package com.example.quizapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.R;
import com.example.quizapp.ViewModel.PagerAdapter;
import com.example.quizapp.databinding.FragmentPagerBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentPager extends Fragment {


    private FragmentPagerBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_pager, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new SplashCreen());
        fragmentList.add(new Login_SignUp_Option());
        fragmentList.add(new Boarding_SignUp());

        PagerAdapter adapter = new PagerAdapter(requireActivity(), fragmentList);

        binding.viewpager.setAdapter(adapter);
    }
}