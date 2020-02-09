package ch.bujaki.resilience4jdemo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Implements a reliable service with long calculation.
 */
class ReliableService implements Supplier<Response> {
	
	private AtomicInteger count = new AtomicInteger(0);
	
	/**
	 * Gets the next {@link Response}.
	 */
	@Override
	public Response get() {
		int result = count.incrementAndGet();
		
		System.out.println(" -> Start " + result);
		calculateSomethingComplex();
		
		return new Response(Integer.toString(result));
	}		

	private void calculateSomethingComplex() {
		// Simulate the long calculation:
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}		
}		