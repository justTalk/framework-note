package com.example.framework_note.ui.home;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework_note.R;

import com.example.framework_note.ui.ScrollingActivity;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                HomeAdpater adpater = new HomeAdpater();
                adpater.mData = strings;
                recyclerView.setAdapter(adpater);
            }
        });
        homeViewModel.addFakerData();
        root.findViewById(R.id.myTestTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("LMM", "isHardwareAccelerated: " + v.isHardwareAccelerated());
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 300).setDuration(2000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //v.setAlpha((int) animation.getAnimatedValue() / 300.0f);
                        v.setTranslationY((int) animation.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });
        root.findViewById(R.id.scrollPage).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScrollingActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}