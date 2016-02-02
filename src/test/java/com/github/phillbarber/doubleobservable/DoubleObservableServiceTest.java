package com.github.phillbarber.doubleobservable;

import com.google.common.collect.Lists;
import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleObservableServiceTest {

    @Test
    public void emitsTwoItems(){
        Observable<String> contentFromDownstreamSystem = new DoubleObservableService().getContent();
        ArrayList<String> listOfItems = Lists.newArrayList(contentFromDownstreamSystem.toBlocking().getIterator());
        assertThat(listOfItems.size()).isEqualTo(2);
    }
}
