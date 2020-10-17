package com.example.patroncompanion.ui.events;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patroncompanion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsFragment extends Fragment {
    int rowsCount;

    private EventsListAdapter mAdapter;
    private View root;
    private String username;

    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar pbar;
    private List<EventsElement> eventsList;

    private FirebaseDatabase mFirebaseDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            eventsList = getArguments().getParcelable("eventsList");
        }

        root = inflater.inflate(R.layout.fragment_events, container, false);
        pbar = (ProgressBar) root.findViewById(R.id.progress_bar);

        eventsList = new ArrayList<>();

        EventsElement element = new EventsElement();
        element.setEventTitle("null");
        element.setEventText("null");
        element.setEventDate("null");
        eventsList.add(element);

        //DialogFragment dialog = new DBConnectionAlertDialogFragment();
        //dialog.show(requireActivity().getSupportFragmentManager(), "No connection");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("TAG", "/eventsdata/" + currentUser.getUid());

        updateList();

        return root;
    }

    private List<EventsElement> createMockListData() {

        List<EventsElement> data = new ArrayList<>();
        Log.d("TSH", "Mock list data");
        if(eventsList.get(0).getEventText() != null) {
            for (int i = 0; i < eventsList.size(); i++) {
                data.add(new EventsElement());
                data.get(i).setEventTitle(eventsList.get(i).getEventTitle());
                data.get(i).setEventText(eventsList.get(i).getEventText());
                data.get(i).setEventDate(eventsList.get(i).getEventDate());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                Date date = null;
                /*
                try {
                    date = formatter.parse(mDataDates[i]);
                    Log.d("TAM", String.valueOf(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                */
            }
        }
        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updateList() {
        pbar.setVisibility(View.VISIBLE);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReferenceFromUrl("https://test-e3678.firebaseio.com/eventsData/" + auth.getUid());
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    if(!eventsList.isEmpty()) {
                        eventsList.clear();
                    }
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        EventsElement element = dataSnapshot.getValue(EventsElement.class);
                        eventsList.add(element);
                    }
                }

                pbar.setVisibility(View.INVISIBLE);
                mRecyclerView = root.findViewById(R.id.events_recycleView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mAdapter = new EventsListAdapter(createMockListData(), username, getActivity());
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERR", String.valueOf(error));
            }
        });
    }

}