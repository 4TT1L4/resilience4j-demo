package ch.bujaki.resilience4jdemo;

import java.time.Duration;
import java.util.function.Supplier;


import org.junit.jupiter.api.Test;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

public class ReplyTest {

	@Test
	public void test_unreliableService() {
		Supplier<Response> supplier = new UnreliableService();
		
		for (int j =0; j<20; j++) {
			System.out.println(supplier.get());
		}
	}
	
	@Test
	public void test_unreliableService_withRetry() {
		RetryConfig config = RetryConfig.<Response>custom()
				.maxAttempts(2)
				.waitDuration(Duration.ofMillis(350))
				.retryOnResult(Response::hasFailed)
				.build();
		
		Retry retryWithCustomConfig =
			RetryRegistry.of(config).retry("custom");
		
		Supplier<Response> supplier = new UnreliableService();
		Supplier<Response> decoratedSuplier = Retry.decorateSupplier(retryWithCustomConfig, supplier);
		
		for (int j =0; j<20; j++) {
			System.out.println(decoratedSuplier.get());
		}
	}

}
