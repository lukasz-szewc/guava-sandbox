package org.luksze.eventbus;

import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EventBusTest {

    private EventBus eventBus;
    private Listener listener;
    private Producer producer;

    @Before
    public void setUp() throws Exception {
        eventBus = new EventBus();
        listener = new Listener();
        producer = new Producer(eventBus);
    }

    @Test
    public void canSendEventsViaEventBus() throws Exception {
        //given
        eventBus.register(listener);

        //when
        Event event = producer.produceEvent("first");

        //then
        assertThat(listener.hasBeenInformedAbout(event), is(TRUE));
    }

}
