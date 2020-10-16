package com.example.patroncompanion.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.patroncompanion.R;
import com.example.patroncompanion.utilities.UploadWorker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeFragment extends Fragment {

    private TextView mTextView;
    private Button mButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mTextView = (TextView) root.findViewById(R.id.text_home);
        mButton = (Button) root.findViewById(R.id.button1);

        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(request);
            }
        });

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId()).observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                String status = workInfo.getState().name();

                mTextView.append(status + "\n");
            }
        });

        Log.d("TAG", "HomeCalled");
        return root;
    }
}