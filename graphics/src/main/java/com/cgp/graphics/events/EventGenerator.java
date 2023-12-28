package com.cgp.graphics.events;

import java.util.HashSet;

public class EventGenerator<T extends Event> {
    private final HashSet<Subscriber<T>> subscribers = new HashSet<>();

    public void subscribe(Subscriber<T> subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber<T> subscriber){
        subscribers.remove(subscriber);
    }

    public void publish(T event){
        subscribers.parallelStream().forEach(subscriber -> subscriber.process(event));
    }
}
