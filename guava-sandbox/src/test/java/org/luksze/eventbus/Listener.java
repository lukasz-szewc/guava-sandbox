package org.luksze.eventbus;

import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;

class Listener {

    private final ArrayList<Event> events;

    public Listener() {
        events = new ArrayList<>();
    }

    @Subscribe
    public void anyMethodName(Event event) {
        events.add(event);
    }

    @Override
    public String toString() {
        return "Listener{" +
                "events=" + events +
                '}';
    }

    boolean hasBeenInformedAbout(Event event) {
        return events.contains(event);
    }
}
