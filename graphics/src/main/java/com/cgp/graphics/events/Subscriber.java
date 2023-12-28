package com.cgp.graphics.events;

public interface Subscriber<T extends  Event> {
    void process(T event);
}
