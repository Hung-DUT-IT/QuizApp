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
import com.example.quizapp.databinding.FragmentChangePassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassFragment extends Fragment {
    private FragmentChangePassBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_change_pass, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String lpass = String.valueOf(binding.editLastPass.getText());
                String newpass = String.valueOf(binding.editNewPass.getText());
                String cfpass = String.valueOf(binding.editConfirmPass.getText());



                if(TextUtils.isEmpty(lpass)){
                    Toast.makeText(getContext(),"Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(newpass)){
                    Toast.makeText(getContext(),"Please enter your new password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(cfpass)){
                    Toast.makeText(getContext(),"Please enter your confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpass.equals(lpass)){
                    Toast.makeText(getContext(),"The new password must not be the same as the current password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newpass.equals(cfpass)){
                    Toast.makeText(getContext(),"The cofirm password must be the same as the new password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUsers.getInstance().getUserByID(FirebaseUsers.getInstance().getIdUserCurrent()).addOnCompleteListener(new OnCompleteListener<User>() {
                    @Override
                    public void onComplete(@NonNull Task<User> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult();
                            String fpass = String.valueOf(user.getPass());
                            String Uid = FirebaseUsers.getInstance().getIdUserCurrent();


                            if(lpass.equals(fpass)){

                                FirebaseUsers.getInstance().changePass(newpass, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            FirebaseUsers.getInstance().setPass(Uid,newpass);
                                            Toast.makeText(getContext(),"Change password successfull !",
                                                    Toast.LENGTH_SHORT).show();
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragmentContainerView, new SettingsFragment())
                                                .addToBackStack(null)
                                                .commit();
//                                            Navigation.findNavController(view).navigate(R.id.settingsFragment);
                                        }
                                        else {
                                            Toast.makeText(getContext(),"Change pass failed",
                                                    Toast.LENGTH_SHORT).show();
                                            binding.editLastPass.setText("");
                                            binding.editNewPass.setText("");
                                            binding.editConfirmPass.setText("");
                                        }

                                    }
                                });


                            }
                        }
                        else {
                            Exception ex = task.getException();
                        }
                    }
                });







            }
        });
    }
}