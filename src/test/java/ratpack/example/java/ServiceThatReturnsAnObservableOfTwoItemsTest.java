package ratpack.example.java;

import com.github.phillbarber.ServiceThatReturnsAnObservableOfTwoItems;
import org.junit.Test;
import rx.Observable;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceThatReturnsAnObservableOfTwoItemsTest {

    @Test
    public void testObservableEmitsTwoItems(){
        Observable<String> observable = new ServiceThatReturnsAnObservableOfTwoItems().getObservable();

        AtomicInteger itemsEmitted = new AtomicInteger(0);

        observable.subscribe(s -> {
            itemsEmitted.incrementAndGet();
        });

        assertThat(itemsEmitted.get()).isEqualTo(2);
    }

}
