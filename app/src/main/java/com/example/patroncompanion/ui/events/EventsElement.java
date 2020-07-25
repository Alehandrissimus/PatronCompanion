package com.example.patroncompanion.ui.events;

import java.util.Date;

public class EventsElement {
    private String mText;
    private Date mDate;

    public EventsElement() {
        this.mText = null;
    }

    public String getText() {
        return mText;
    }
    public void setText(String text) {
        mText = text;
    }

    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }
}
