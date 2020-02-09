package ch.bujaki.resilience4jdemo;

import java.util.function.Supplier;

/**
 * Implements a failing service, which responds slowly after some requests for a while.
 */
class FailingService implements Supplier<Response> {
	
	private int count = 0;
	
	/**
	 * Gets the next {@link Response}.
	 */
	@Override
	public Response get() {
		count++;
		
		final boolean fakeSlowResponse = (count > 4) && (count < 7); 
		if (fakeSlowResponse) {
			try {
				System.out.println("  ...Slow response...");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return new Response(Integer.toString(count));
	}
}