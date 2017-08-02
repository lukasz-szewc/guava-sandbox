package org.luksze.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FuturesTest {

    private ListeningExecutorService service;

    @Before
    public void setUp() throws Exception {
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
    }

    @Test(timeout = 10000)
    public void canSendEventsViaEventBus() throws Exception {
        //given
        List<TimeConsumingTask> fromSlowestToFastestTasks = asList(
                new TimeConsumingTask(3), new TimeConsumingTask(2), new TimeConsumingTask(1));

        //when
        List<Integer> results = taskAreSubmittedFromSlowestToFaster(fromSlowestToFastestTasks);

        //then
        assertEquals(valueOf(1), results.get(0));
        assertEquals(valueOf(2), results.get(1));
        assertEquals(valueOf(3), results.get(2));
    }

    private List<Integer> taskAreSubmittedFromSlowestToFaster(
            List<TimeConsumingTask> timeConsumingTasks) throws Exception {
        final ArrayList<Integer> resultsInOrder = new ArrayList<>();
        ArrayList<Future> futures = new ArrayList<>();
        for (TimeConsumingTask task : timeConsumingTasks) {
            ListenableFuture<Integer> future = service.submit(task);
            Futures.addCallback(future, new IntegerFutureCallback(resultsInOrder));
            futures.add(future);
        }

        for (Future future : futures) {
            future.get(8000, TimeUnit.SECONDS);
        }
        return resultsInOrder;
    }

    private static class TimeConsumingTask implements Callable<Integer> {
        private final int number;

        public TimeConsumingTask(int number) {
            this.number = number;
        }

        public Integer call() throws Exception {
            Thread.sleep((long) (number * 600));
            return number;
        }
    }

    private static class IntegerFutureCallback implements FutureCallback<Integer> {
        private final ArrayList<Integer> results;

        public IntegerFutureCallback(ArrayList<Integer> results) {
            this.results = results;
        }

        @Override
        public void onSuccess(Integer result) {
            results.add(result);
        }

        @Override
        public void onFailure(Throwable t) {
            Assert.fail();
        }
    }
}
