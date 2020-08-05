package com.example.patroncompanion.ui.events;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.example.patroncompanion.utilities.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<EventsElement> data;
    private String username;
    private Context context;
    private int timerCount = 0;
    private List<EventsViewHolder> test;

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
            EventsViewHolder a = new EventsViewHolder(holder.itemView);
            a.mTextView.setText(data.get(position - 1).getTitle());

            Date eventDate = data.get(position-1).getDate();
            Date curDate = Calendar.getInstance().getTime();
            long milliseconds = eventDate.getTime() - curDate.getTime();

            if(curDate.before(eventDate)) {
                Log.d("TAW", "if true");
                Intent intent = new Intent(context, AlarmReceiver.class);
                intent.putExtra("NotificationText", "some text");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
            }

            //Log.d("TAS", "eventDate = " + eventDate + " curDate = " + curDate);
            //Log.d("TAS", "eventDate = " + eventDate.getTime() + " curDate = " + curDate.getTime());
            if(curDate.before(eventDate)) {
                CountDownTimer timer = new CountDownTimer(milliseconds, 1000) {
                    public void onTick(long millisUntilFinished) {
                        //Log.d("TAS", "millis = " + millisUntilFinished);
                        EventsViewHolder b = new EventsViewHolder(holder.itemView);
                        long seconds = ((millisUntilFinished / 1000) % 60) ;
                        long minutes = ((millisUntilFinished / (1000*60)) % 60);
                        long hours   = ((millisUntilFinished / (1000*60*60)));
                        b.mDataText.setText(String.format("%02d:%02d:%02d",hours,minutes,seconds));
                    }
                    public void onFinish() {
                        EventsViewHolder b = new EventsViewHolder(holder.itemView);
                        b.mDataText.setText("Time Up");
                    }
                };
                timer.start();
            } else {
                a.mDataText.setText("Time's Up");
            }

            a.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventActivity.class);
                    intent.putExtra("title", data.get(position - 1).getTitle());
                    intent.putExtra("text", data.get(position - 1).getText());
                    intent.putExtra("date", data.get(position - 1).getDate().toString());
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
        //Log.d("TAN", "onViewRecycled");
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //Log.d("TAN", "onAttachedToRecyclerView");
        timerCount = 0;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        //Log.d("TAN", "onDetachedFromRecyclerView");
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        //Log.d("TAN", "onViewAttachedToWindow");
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //Log.d("TAN", "onViewDetachedFromWindow");
        for(int i = 0; i < timerCount; i++) {
            EventsViewHolder buf = test.get(i);
            buf.timerCount.cancel();
        }
    }
}
