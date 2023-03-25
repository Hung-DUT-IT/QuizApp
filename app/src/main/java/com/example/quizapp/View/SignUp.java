package com.example.quizapp.View;

import android.app.ProgressDialog;
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

import com.example.quizapp.Model.User;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends Fragment {

    private FragmentSignUpBinding binding;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_sign_up, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.login_SignUp_Option);
            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(binding.editEmail.getText());
                String password = String.valueOf(binding.editPass.getText());
                String confirmpass = String.valueOf(binding.editConfirmPass.getText());
                mAuth = FirebaseAuth.getInstance();

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getContext(),"Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(getContext(),"Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6)
                    if(TextUtils.isEmpty(confirmpass)){

                        Toast.makeText(getContext(),"Password được dưới 6 kí tự", Toast.LENGTH_SHORT).show();
                        return;
                    }
                if(password.equals(confirmpass)){

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if(user == null)
                                            return;
                                        else {
                                            Toast.makeText(getContext(), "Loading data",
                                                    Toast.LENGTH_SHORT).show();
                                            String email2 = user.getEmail();
                                            String Uid = user.getUid();
                                            User u = new User(email2,password,Uid);

                                            DatabaseReference databaseReference = FirebaseDatabase.
                                                    getInstance().getReference("Users");
                                            databaseReference.push().setValue(u);

                                        }

                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getContext(), "Sign up successfully",
                                                Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(view).navigate(R.id.login );
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }else {
                    Toast.makeText(getContext(),"Xác thực password chưa chính xác", Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });
    }




}