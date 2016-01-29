package ratpack.example.java;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by pergola on 28/01/16.
 */
public class ServiceThatReturnsAnObservableOfTwoItemsTest {

    @Test
    public void testObservableEmitsTwoItems(){
        Observable<String> observable = new ServiceThatReturnsAnObservableOfTwoItems().getObservable();

        AtomicInteger itemsEmitted = new AtomicInteger(0);

        observable.subscribe(s -> {
            itemsEmitted.incrementAndGet();
        });

        assertEquals(1, itemsEmitted.get());
    }

}
