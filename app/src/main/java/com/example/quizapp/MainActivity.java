package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.quizapp.Adapter.CategoryAdapter;
import com.example.quizapp.Model.Question;
import com.example.quizapp.View.HomeFragment;
import com.example.quizapp.View.PlayGameFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo fragmentManager
        fragmentManager = getSupportFragmentManager();

        // Thêm HomeFragment vào MainActivity
        fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.fragmentContainerView, homeFragment, "home_fragment");
        fragmentTransaction.commit();
    }

//    public void goToDetailFragment(Question question) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//        PlayGameFragment playGameFragment = new PlayGameFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("", question);
//        playGameFragment.setArguments(bundle);
//        fragmentTransaction.replace(R.id.fragmentContainerView, playGameFragment);
//        fragmentTransaction.commit();
//    }
}