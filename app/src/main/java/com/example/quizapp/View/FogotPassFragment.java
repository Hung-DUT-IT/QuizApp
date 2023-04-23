package com.example.quizapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentFogotPassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class FogotPassFragment extends Fragment {

    private FragmentFogotPassBinding binding;

    public FogotPassFragment() {
        // Required empty public constructor
    }

    public static FogotPassFragment newInstance() {
        FogotPassFragment fragment = new FogotPassFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_fogot_pass, container , false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new PagerFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =String.valueOf(binding.editEmail.getText()) ;
                String e = "duongvanchon18@gmail.com";
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getContext(),"Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseUsers.getInstance().resetPassword(email, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Reset pass successful!", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainerView, new SignInFragment())
                                    .addToBackStack(null)
                                    .commit();

                        } else {
                            Toast.makeText(getContext(), "Reset password failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
    }
}