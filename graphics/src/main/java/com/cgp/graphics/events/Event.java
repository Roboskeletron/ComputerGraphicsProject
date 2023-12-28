package com.cgp.graphics.events;

public abstract class Event {
    private final Object sender;

    protected Event(Object sender) {
        this.sender = sender;
    }

    public Object getSender() {
        return sender;
    }
}
