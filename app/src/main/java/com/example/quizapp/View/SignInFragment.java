package com.example.quizapp.View;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quizapp.Model.Helper.FirebaseUtils;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.MySharedPreferences;
import com.example.quizapp.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_sign_in, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.pagerFragment);
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(binding.editEmail.getText());
                String password = String.valueOf(binding.editPass.getText());

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getContext(),"Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(getContext(),"Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseUtils.getInstance().signIn(email, password, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Sign in successful!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.homeFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Authentication failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}