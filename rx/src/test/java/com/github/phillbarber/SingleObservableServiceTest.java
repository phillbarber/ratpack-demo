package com.github.phillbarber;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;

public class SingleObservableServiceTest {


    @Test
    public void emitsTwoItems(){
        Observable<String> contentFromDownstreamSystem = new SingleObservableService().getContent();
        ArrayList<String> listOfItems = Lists.newArrayList(contentFromDownstreamSystem.toBlocking().getIterator());
        Assertions.assertThat(listOfItems.size()).isEqualTo(1);
    }

}
