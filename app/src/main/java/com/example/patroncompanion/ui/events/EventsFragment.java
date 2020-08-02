package com.example.patroncompanion.ui.events;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patroncompanion.R;
import com.example.patroncompanion.database.DBConnectionAlertDialogFragment;
import com.example.patroncompanion.database.DBGetEventsDate;
import com.example.patroncompanion.database.DBGetEventsText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EventsFragment extends Fragment {
    int rowsCount;

    private EventsListAdapter mAdapter;
    private View root;
    private String username;

    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private String[] mDataDates;
    private String[] mDataTexts;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        //Log.d("TAG", "events fragment username = " + username);

        root = inflater.inflate(R.layout.fragment_events, container, false);

        //-----------------------------------------------------------------------------------------


        Bundle DBDates;
        DBGetEventsDate dbd = new DBGetEventsDate();
        dbd.execute(username);

        try {
            DBDates = dbd.get(10000, TimeUnit.MILLISECONDS);
            mDataDates = DBDates.getStringArray("KEY_STARR");
            rowsCount = DBDates.getInt("KEY_INT");
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();

        }

        DBGetEventsText dbt = new DBGetEventsText();
        Bundle DBText = new Bundle();
        DBText.putString("username", username);
        DBText.putInt("rowsCount", rowsCount);
        dbt.execute(DBText);

        try {
            mDataTexts = dbt.get(10000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            dbt.cancel(true);
            DialogFragment dialog = new DBConnectionAlertDialogFragment();
            dialog.show(requireActivity().getSupportFragmentManager(), "No connection");
        }

        //-----------------------------------------------------------------------------------------

        mRecyclerView = root.findViewById(R.id.events_recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new EventsListAdapter(createMockListData(), username, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //Log.d("TAG", "EventsCalled");
        return root;
    }

    private List<EventsElement> createMockListData() {
        List<EventsElement> data = new ArrayList<>();
        if(mDataTexts != null && mDataDates != null) {
            for (int i = 0; i < rowsCount; i++) {
                data.add(new EventsElement());
                data.get(i).setText(mDataTexts[i]);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                Date date = null;
                try {
                    date = formatter.parse(mDataDates[i]);
                    Log.d("TAM", String.valueOf(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                data.get(i).setDate(date);
            }
        }

        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d("TAD", "onResume called");

        Bundle DBDates;
        DBGetEventsDate dbd = new DBGetEventsDate();
        dbd.execute(username);

        try {
            DBDates = dbd.get(10000, TimeUnit.MILLISECONDS);
            mDataDates = DBDates.getStringArray("KEY_STARR");
            rowsCount = DBDates.getInt("KEY_INT");
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        DBGetEventsText dbt = new DBGetEventsText();
        Bundle DBText = new Bundle();
        DBText.putString("username", username);
        DBText.putInt("rowsCount", rowsCount);
        dbt.execute(DBText);

        try {
            mDataTexts = dbt.get(10000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            dbt.cancel(true);
            DialogFragment dialog = new DBConnectionAlertDialogFragment();
            dialog.show(requireActivity().getSupportFragmentManager(), "No connection");
        }

        mRecyclerView = root.findViewById(R.id.events_recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new EventsListAdapter(createMockListData(), username, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAD", "onPause called");
    }
}