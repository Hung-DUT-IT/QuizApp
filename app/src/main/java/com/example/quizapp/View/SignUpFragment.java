package com.example.quizapp.View;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quizapp.Model.Entity.User;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_sign_up, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.pagerFragment);
            }
        });


        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = String.valueOf(binding.editEmail.getText());
                String password = String.valueOf(binding.editPass.getText());
                String confirmPass = String.valueOf(binding.editConfirmPass.getText());

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getContext(),"Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(getContext(),"Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6)
                    if(TextUtils.isEmpty(confirmPass)){

                        Toast.makeText(getContext(),"Password must not be less than 6 characters!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                if(password.equals(confirmPass)){
                    FirebaseUsers.getInstance().signUp(email, password, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = FirebaseUsers.getInstance().getCurrentUser();
                            if( user == null ){
                                return;
                            }
                            else {
                                Toast.makeText(getContext(), "Loading data", Toast.LENGTH_SHORT).show();
                                String email2 = user.getEmail();
                                String Uid = user.getUid();
                                User u = new User(email2, password);

                                FirebaseUsers.getInstance().addUser(Uid, u);
                            }
                            Toast.makeText(getContext(), "Sign up successfully", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.signInFragment );
                        }
                    });
                }else {
                    Toast.makeText(getContext(),"Incorrect password authentication !!", Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });
    }
}