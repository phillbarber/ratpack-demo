package com.github.phillbarber.happy;

import com.google.common.collect.Lists;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Ignore;
import org.junit.Test;
import ratpack.http.client.internal.DefaultHttpClient;
import rx.Observable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HappyPathServiceTest {

    //todo - need to remember who to call httpclient code within a ratpack managed thread.
    @Test
    @Ignore()
    public void happyPathHandlerReturnsYAY(){
        Observable<String> contentFromDownstreamSystem = new HappyPathService(new DefaultHttpClient(PooledByteBufAllocator.DEFAULT, 100000)).getContentFromDownstreamSystem();
        List<String> result = Lists.newArrayList(contentFromDownstreamSystem.toBlocking().getIterator());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo("YAY");

    }

}
