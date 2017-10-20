package org.luksze.eventbus;

import com.google.common.eventbus.EventBus;

class Producer {
    private EventBus eventBus;

    public Producer(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public Event produceEvent(String name) {
        Event event = new Event(name);
        eventBus.post(event);
        return event;
    }
}
