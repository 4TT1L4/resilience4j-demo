package ch.bujaki.resilience4jdemo;

import java.time.Duration;
import java.util.function.Supplier;


import org.junit.jupiter.api.Test;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class CircuitBreakerTest {

	@Test
	public void test_failingService() throws InterruptedException  {
		Supplier<Response> supplier = new FailingService();
		
		for (int i=0; i<17; i++) {
			Thread.sleep(1000);
			System.out.println(supplier.get());
		}
	}
	
	@Test
	public void test_failingService_withCircuitBreaker() throws InterruptedException {
		CircuitBreakerConfig config = CircuitBreakerConfig.custom()
			.minimumNumberOfCalls(2) // in current sliding-window for calculation
			.slidingWindowType(SlidingWindowType.COUNT_BASED)
			.slidingWindowSize(2)
			.failureRateThreshold(50) // per cent
			.slowCallRateThreshold(50) // per cent
			.slowCallDurationThreshold(Duration.ofMillis(100))
			.waitDurationInOpenState(Duration.ofMillis(5000))
			.permittedNumberOfCallsInHalfOpenState(1)
			.ignoreExceptions(BusinessException.class)
			.build();

		CircuitBreaker circuitBreaker = CircuitBreakerRegistry.of(config)
		  .circuitBreaker("name", config);
		
		Supplier<Response> supplier = new FailingService();
		Supplier<Response> decorated = circuitBreaker.decorateSupplier(supplier);

		circuitBreaker
			.getEventPublisher()
			.onStateTransition( e -> System.out.println( e.getStateTransition()));
		
		for (int j =0; j<17; j++) {
			Thread.sleep(1250); // wait a bit...
			try {
				System.out.println(decorated.get());
			} catch (CallNotPermittedException ex) {
				System.out.println("  CircuitBreaker is OPEN -> Fail fast!");
			}
		}
	}

}








