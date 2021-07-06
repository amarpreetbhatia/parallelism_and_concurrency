package com.amarpreet.completableFuture;

import com.amarpreet.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);
    @InjectMocks
    CompletableFutureHelloWorldException worldException;

    @Test
    void helloWorld_with_3_async() {
        when(helloWorldService.hello())
                .thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = worldException.helloWorld_with_3_async();

        assertEquals(" WORLD! HI COMPLETABLE FUTURE",result);

    }

    @Test
    void helloWorld_with_3_async_world_exception() {
        when(helloWorldService.hello())
                .thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world())
                .thenThrow(new RuntimeException("Exception occurred on world method as well"));

        String result = worldException.helloWorld_with_3_async();

        assertEquals(" HI COMPLETABLE FUTURE",result);

    }

    @Test
    void helloWorld_with_3_async_hello_world_happy_path() {
        when(helloWorldService.hello())
                .thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world())
                .thenThrow(new RuntimeException("Exception occurred on world method as well"));

        String result = worldException.helloWorld_with_3_async();

        assertEquals(" HI COMPLETABLE FUTURE",result);

    }
}