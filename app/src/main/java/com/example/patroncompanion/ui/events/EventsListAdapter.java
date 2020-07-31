package com.example.patroncompanion.ui.events;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patroncompanion.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<EventsElement> data;
    private String username;
    EventsViewHolder a;
    private Context context;

    public EventsListAdapter(List<EventsElement> data, String username, Context context) {
        this.data = data;
        this.username = username;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View VHItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_fragment, parent, false);
            return new EventsViewHolder(VHItem);
        } else if (viewType == TYPE_HEADER) {
            View VHHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_header, parent, false);
            return new HeaderViewHolder(VHHeader);
        }
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_fragment, parent, false);
        //return new EventsViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof EventsViewHolder) {
            a = new EventsViewHolder(holder.itemView);
            a.mTextView.setText(data.get(position - 1).getText());
            //a.mDataText.setText(data.get(position-1).getDate().toString());

            /*
            if(a.timerCount==null) {
                Date eventDate = data.get(position-1).getDate();
                Date curDate = Calendar.getInstance().getTime();
                long milliseconds = eventDate.getTime() - curDate.getTime();
                Log.d("TAC", String.valueOf((milliseconds)));
                ((EventsViewHolder) holder).timerCount = new CountDownTimer(milliseconds, 1000) {
                    @Override
                    public void onTick(long millis) {
                        Log.d("TAC", String.valueOf((millis)));
                        String hms = String.format("%02d:%02d",  TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                        ((EventsViewHolder) holder).mDataText.setText(hms);
                    }

                    @Override
                    public void onFinish() {

                    }
                };
            }
            ((EventsViewHolder) holder).mDataText.setVisibility(View.VISIBLE);
            ((EventsViewHolder) holder).timerCount.start();

         */

/*
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putLi("data", data);

            AsyncTimerTask task = new AsyncTimerTask();
            task.execute(bundle);
            */
/*
            Date eventDate = data.get(position-1).getDate();
            Date curDate = Calendar.getInstance().getTime();
            long milliseconds = eventDate.getTime() - curDate.getTime();
            Log.d("TAS", "eventDate = " + eventDate + " curDate = " + curDate);
            Log.d("TAS", "eventDate = " + eventDate.getTime() + " curDate = " + curDate.getTime());
            if(curDate.before(eventDate)) {
                Log.d("TAS", String.valueOf((milliseconds)));
                CountDownTimer timer = new CountDownTimer(milliseconds, 1000) {
                    public void onTick(long millisUntilFinished) {
                        Log.d("TAS", "millis = " + millisUntilFinished);
                        long seconds = ((millisUntilFinished / 1000) % 60) ;
                        long minutes = ((millisUntilFinished / (1000*60)) % 60);
                        long hours   = ((millisUntilFinished / (1000*60*60)));
                        a.mDataText.setText(String.format("%02d:%02d:%02d",hours,minutes,seconds));
                    }
                    public void onFinish() {
                        a.mDataText.setText("Time Up");
                    }
                };
                timer.start();
            }
            a.mDataText.setText(" " + milliseconds);

 */

            ((EventsViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventActivity.class);
                    intent.putExtra("date", data.get(position - 1).getDate().toString());
                    intent.putExtra("text", data.get(position - 1).getText());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder b = new HeaderViewHolder(holder.itemView);
            b.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventAddActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public EventsElement getItem(int position) {
        return data.get(position);
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTextView, mDataText;
        CountDownTimer timerCount;

        public EventsViewHolder(View itemView) {
            super(itemView);

            timerCount = null;

            mCardView = (CardView) itemView.findViewById(R.id.events_cardView);
            mTextView = (TextView) itemView.findViewById(R.id.events_title);
            mDataText = (TextView) itemView.findViewById(R.id.events_date);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView mHTextView;
        Button mHButton;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            mHTextView = (TextView) itemView.findViewById(R.id.event_insert_text);
            mHButton = (Button) itemView.findViewById(R.id.event_insert_date);
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        Log.d("TAN", "onViewRecycled");
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d("TAN", "onAttachedToRecyclerView");
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.d("TAN", "onDetachedFromRecyclerView");
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d("TAN", "onViewAttachedToWindow");
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("TAN", "onViewDetachedFromWindow");
        ((EventsViewHolder) holder).timerCount.cancel();
    }
}
