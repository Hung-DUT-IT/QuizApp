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

import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentChangePasswordBinding;
import com.example.quizapp.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_change_password, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.settingFragment);
            }
        });


        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = String.valueOf(binding.editPass.getText());
                String newpass = String.valueOf(binding.editNewpass.getText());
                String confirmpass = String.valueOf(binding.editConfirmPass.getText());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                String e = user.getEmail();
                mDatabase = FirebaseDatabase.getInstance().getReference("users");

                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getContext(),"Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(newpass)){
                    Toast.makeText(getContext(),"Please enter your new password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmpass)){
                    Toast.makeText(getContext(),"Please enter your confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpass.equals(pass)){
                    Toast.makeText(getContext(),"The new password must not be the same as the current password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(e, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Password đúng
                                if(newpass.equals(confirmpass)){
                                    user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                //                                    Toast.makeText(getContext(),"Change password successful",
                                                //                                            Toast.LENGTH_SHORT).show();
                                                mDatabase.child(uid).child("pass").
                                                        setValue(newpass, new DatabaseReference.CompletionListener() {
                                                            @Override
                                                            public void onComplete(@Nullable DatabaseError error,
                                                                                   @NonNull DatabaseReference ref) {
                                                                Toast.makeText(getContext(),"Update password successful",
                                                                        Toast.LENGTH_SHORT).show();
                                                                return;
                                                            }
                                                        });
                                                Navigation.findNavController(view).navigate(R.id.settingFragment);
                                            }

                                        }
                                    });
                                }

                            } else {
                                // Password sai
                                Toast.makeText(getContext(),"Error, wrong password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });
    }
}