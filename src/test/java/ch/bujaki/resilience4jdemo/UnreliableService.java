package ch.bujaki.resilience4jdemo;

import java.util.function.Supplier;

class UnreliableService implements Supplier<Response> {
	
	int i = 0;
	
	@Override
	public Response get() {
		i++;
		
		if (i % 2 == 0) {
			return new Response(Integer.toString(i));
		}
		else {
			return new Response(null);
		}
	}
}