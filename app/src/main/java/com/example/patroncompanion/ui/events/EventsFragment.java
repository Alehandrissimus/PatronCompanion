package com.example.patroncompanion.ui.events;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
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
    private Iterable<DataSnapshot> mSnapshotChildren;
    private int mSnapshotChildrenCount;
    private List<EventsElement> eventsList;
    private String[] mDataDates;
    private String[] mDataTitles;
    private String[] mDataTexts;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        Log.d("TAG", "events fragment username = " + username);

        root = inflater.inflate(R.layout.fragment_events, container, false);

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
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("TAG", "/eventsdata/" + currentUser.getUid());
        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReferenceFromUrl("https://test-e3678.firebaseio.com/eventsData/B60fuVyM4tMbKtzbptrNP6AKs3t2");

        //Query query = FirebaseDatabase.getInstance().getReference("/eventsdata/");

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if(snapshot.exists()) {
                    if(!eventsList.isEmpty()) {
                        eventsList.clear();
                    }
                    Log.d("TSH", "on data change");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        EventsElement element = dataSnapshot.getValue(EventsElement.class);
                        eventsList.add(element);
                        Log.d("TAG", element.getEventDate());
                        Log.d("TAG", element.getEventText());
                        Log.d("TAG", element.getEventTitle());
                    }

                    mSnapshotChildren = snapshot.getChildren();
                    Log.d("TAG", "Count " + snapshot.getChildrenCount());
                    mSnapshotChildrenCount = (int) snapshot.getChildrenCount();
                    //GenericTypeIndicator<List<EventsElement>> t = new GenericTypeIndicator<List<EventsElement>>() {};
                    //eventsList = snapshot.getValue(t);
                    //Log.d("TAG", String.valueOf(eventsList));
                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERR", String.valueOf(error));
            }
        });

        mRecyclerView = root.findViewById(R.id.events_recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("TSH", "+ adapter");
        mAdapter = new EventsListAdapter(createMockListData(), username, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    private List<EventsElement> createMockListData() {

        List<EventsElement> data = new ArrayList<>();
        Log.d("TSH", "Mock list data");

        Log.d("TSH", eventsList.get(0).getEventTitle());
        if(eventsList.get(0).getEventText() != null) {
            for (int i = 0; i < eventsList.size(); i++) {
                data.add(new EventsElement());
                data.get(i).setEventTitle(eventsList.get(i).getEventTitle());
                data.get(i).setEventText(eventsList.get(i).getEventText());

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
                data.get(i).setEventDate("213123");
                Log.d("TSH", data.get(0).getEventTitle());
            }
            if (data.get(0).getEventTitle() == null) {
                Log.d("TSH", "pizda");
            }
        }

        return data;
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView = root.findViewById(R.id.events_recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new EventsListAdapter(createMockListData(), username, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}