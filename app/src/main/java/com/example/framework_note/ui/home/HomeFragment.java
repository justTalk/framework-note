package com.example.framework_note.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.example.GlideOptions;
import com.example.framework_note.FullscreenActivity;
import com.example.framework_note.MemUtils;
import com.example.framework_note.R;
import com.example.framework_note.oom.OomTest;
import com.example.framework_note.ui.FaceDetectorTest;
import com.example.framework_note.ui.motion.MotionLayoutTestActivity;
import com.example.framework_note.util.UriUtils;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final int REQUEST_CODE_SELECT_PIC = 382;

    private HomeViewModel homeViewModel;
    private String url = "https://rc.vccresource.com/15-1/20210910/c5de0e_1219219867569262592.png";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                HomeAdpater adpater = new HomeAdpater();
                adpater.setOnItemClickListener(position -> doHandleClick(position));
                adpater.mData = strings;
                recyclerView.setAdapter(adpater);
            }
        });
        homeViewModel.addFakerData();
        root.findViewById(R.id.scrollPage).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                textGlide(root);
            }
        });
        requestPermissions(new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 100);
        return root;
    }

    private void doHandleClick(int position){
        switch (position){
            case 0:
                startActivity(new Intent(requireActivity(), MotionLayoutTestActivity.class));
                break;
            case 1:
                startActivity(new Intent(requireActivity(), FullscreenActivity.class));
                break;
            default:
                break;
        }
    }

    private void printMemInfo(String tag){
        float[] memoryData = MemUtils.getMemoryData(getActivity());
        Log.d("LMM", tag + " total: " + memoryData[0] + " nativePss: " +memoryData[1]);
    }

    private void textGlide(View root){
        //mRequestOptions.format(DecodeFormat.PREFER_RGB_565);
        printMemInfo("before");
        mRequestOptions.format(DecodeFormat.PREFER_RGB_565);
        Glide.with(getActivity()).load(url).apply(mRequestOptions).into(
            (ImageView) root.findViewById(R.id.imageView3));
        printMemInfo("after");
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                printMemInfo("affter");
            }
        }, 3000);
    }
    private RequestOptions mRequestOptions = new GlideOptions();

    @Override public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_PIC) {
            Uri url = data.getData();
            if (url != null){
               checkFace(UriUtils.getPath(getContext(), url));
            }
        }
    }

    private void checkFace(String path){
        long l = System.currentTimeMillis();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
        FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 3);
        FaceDetector.Face[] faces = new FaceDetector.Face[3];
        int findFace = faceDetector.findFaces(bitmap, faces);
        Log.d("LMM", "findFace: " + findFace + " cost: " + (System.currentTimeMillis() - l));
    }
}