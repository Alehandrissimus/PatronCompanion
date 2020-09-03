package com.example.patroncompanion.ui.events;

public class EventsElement {
    private String mId;
    private String eventTitle;
    private String eventDate;
    private String eventText;

    public EventsElement() {
        eventTitle = "null";
        eventDate = "0";
        eventText = "null";
    }
    public EventsElement(String mId, String eventTitle, String eventDate, String eventText){
        this.mId = mId;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventText = eventText;
    }

    public String getEventTitle() {
        return eventTitle;
    }
    public void setEventTitle(String text) {
        eventTitle = text;
    }

    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventText() {
        return eventText;
    }
    public void setEventText(String eventText) {
        this.eventText = eventText;
    }

    public String getId() { return mId; }
    public void setId(String id) {
        mId = id;
    }
}
