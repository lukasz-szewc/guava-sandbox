package org.luksze.eventbus;

class Event {

    private String name;

    public Event(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                '}';
    }
}
