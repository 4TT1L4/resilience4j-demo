package ch.bujaki.resilience4jdemo;

import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;

public class BulkheadTest {

	@Test
	public void test_reliableService_noBulkhead() throws InterruptedException {
		Supplier<Response> supplier = new ReliableService();

		for (int i = 0; i < 17; i++) {
			System.out.println(supplier.get());
		}
	}
	
	@Test
	public void test_reliableService_withBulkhead() throws InterruptedException {
		ThreadPoolBulkheadConfig config = 
				ThreadPoolBulkheadConfig.custom()
					.maxThreadPoolSize(5)
					.coreThreadPoolSize(5)
					.queueCapacity(2)
					.build();

		ThreadPoolBulkhead bulkhead = ThreadPoolBulkhead.of("bulkhead-1", config);

		Supplier<Response> supplier = new ReliableService();
		Supplier<CompletionStage<Response>> decorated = bulkhead.decorateSupplier(supplier);

		for (int j = 0; j < 10; j++) {
			try {
				Thread.sleep(500);
				decorated
					.get()
					.thenAccept( r -> System.out.println(r));	
			}
			catch (BulkheadFullException ex) {
				System.out.println("Bulkhead full!");
			}
		}

		
		
		
		Thread.sleep(10000);
	}
}
