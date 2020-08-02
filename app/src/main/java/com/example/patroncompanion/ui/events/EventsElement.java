package com.example.patroncompanion.ui.events;

import java.util.Date;

public class EventsElement {
    private String mTitle;
    private Date mDate;
    private String mText;

    public EventsElement() {
        this.mTitle = null;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String text) {
        mTitle = text;
    }

    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }

    public String getText() {
        return mText;
    }
    public void setText(String text) {
        mText = text;
    }
}
