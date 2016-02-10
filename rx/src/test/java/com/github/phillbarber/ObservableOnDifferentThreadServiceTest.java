package com.github.phillbarber;

import com.google.common.collect.Lists;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableOnDifferentThreadServiceTest {

    @Test
    public void emitsItemsOnDifferentThread() throws InterruptedException {

        Observable<String> content = new ObservableOnDifferentThreadService().getContent();

        Observable<Thread> observableThread = content.map(new Func1<String, Thread>() {
            @Override
            public Thread call(String s) {
                return Thread.currentThread();
            }
        });

        Thread threadThatWasUsedToEmitItems = observableThread.toBlocking().first();

        assertThat(Thread.currentThread()).isNotSameAs(threadThatWasUsedToEmitItems);
    }

    @Test
    public void emitsOneStringOnly(){
        ArrayList<String> items = Lists.newArrayList(new ObservableOnDifferentThreadService().getContent().toBlocking().getIterator());
        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0)).isEqualTo("First Value");
    }
}
