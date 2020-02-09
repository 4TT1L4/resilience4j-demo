package ch.bujaki.resilience4jdemo;

import java.util.function.Supplier;

/**
 * Implements an unreliable service, which fails on every second request.
 */
class UnreliableService implements Supplier<Response> {
	
	private int count = 0;
	
	/**
	 * Gets the next {@link Response}.
	 */
	@Override
	public Response get() {
		count++;
		
		if (count % 2 == 0) {
			return new Response(Integer.toString(count));
		}
		else {
			return new Response(null);
		}
	}
}