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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

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
    public void computationResultsCanBePickedByInOrder() throws Exception {
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

    @Test(timeout = 10000)
    public void computationResultsCanBePickedByInOrderAndTransformed() throws Exception {
        //given
        List<TimeConsumingTask> fromSlowestToFastestTasks = asList(
                new TimeConsumingTask(3), new TimeConsumingTask(2), new TimeConsumingTask(1));

        //when
        List<String> results = taskAreSubmittedAndTransformed(fromSlowestToFastestTasks);

        //then
        assertEquals("1", results.get(0));
        assertEquals("2", results.get(1));
        assertEquals("3", results.get(2));
    }

    private List<Integer> taskAreSubmittedFromSlowestToFaster(List<TimeConsumingTask> timeConsumingTasks) {
        ResultInOrder<Integer> resultInOrder = new ResultInOrder<>();
        timeConsumingTasks.stream().forEach(
                task -> Futures.addCallback(service.submit(task), new IntegerFutureCallback<>(resultInOrder)));
        return resultInOrder.results();
    }

    private List<String> taskAreSubmittedAndTransformed(List<TimeConsumingTask> timeConsumingTasks) {
        ResultInOrder<String> resultInOrder = new ResultInOrder<>();
        timeConsumingTasks.stream().forEach(
                task -> {
                    ListenableFuture<Integer> future = service.submit(task);
                    ListenableFuture<String> transformedFuture = Futures.transform(future, Object::toString);
                    Futures.addCallback(transformedFuture, new IntegerFutureCallback<>(resultInOrder));
                });
        return resultInOrder.results();
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

    private static class IntegerFutureCallback<T> implements FutureCallback<T> {
        private final ResultInOrder<T> results;

        public IntegerFutureCallback(ResultInOrder<T> results) {
            this.results = results;
        }

        @Override
        public void onSuccess(T result) {
            results.add(result);
        }

        @Override
        public void onFailure(Throwable t) {
            Assert.fail();
        }
    }

    private static class ResultInOrder<T> {

        private final CountDownLatch countDownLatch;
        private final ArrayList<T> results;

        public ResultInOrder() {
            countDownLatch = new CountDownLatch(3);
            results = new ArrayList<>(3);
        }

        public void add(T integer) {
            results.add(integer);
            countDownLatch.countDown();
        }

        public List<T> results() {
            await();
            return Collections.unmodifiableList(results);
        }

        private void await() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
