package com.example.quizapp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.quizapp.Model.Entity.User;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentUpdateProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class UpdateProfileFragment extends Fragment {
    private FragmentUpdateProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_update_profile, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUsers.getInstance().getUserByID(FirebaseUsers.getInstance().getIdUserCurrent(), new FirebaseUsers.UserCallback() {
            @Override
            public void onUserReceived(User user) {
                binding.tvRank.setText(String.valueOf(user.getPrev_score()));
                binding.tvCountQuestion.setText(String.valueOf(user.getScore()));
                binding.edtMail.setText(String.valueOf(user.getUsername()));
                binding.edtNameUser.setText(String.valueOf(user.getName()));
            }
        });

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = FirebaseUsers.getInstance().getIdUserCurrent();
                String name = String.valueOf(binding.edtNameUser.getText());
                FirebaseUsers.getInstance().setUpdate(id,name);
                Toast.makeText(getContext(),"Change name successfull !",
                        Toast.LENGTH_SHORT).show();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentContainerView, new SettingsFragment())
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}