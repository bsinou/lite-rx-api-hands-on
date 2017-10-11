package io.pivotal.literx;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href=
 *      "http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux
 *      Javadoc</a>
 */
public class Part01Flux {

	/** Return an empty Flux */
	Flux<String> emptyFlux() {
		return Flux.<String>empty();
	}

	/**
	 * Return a Flux that contains 2 values "foo" and "bar" without using an array
	 * or a collection
	 */
	Flux<String> fooBarFluxFromValues() {
		return Flux.just("foo", "bar");
	}

	/** Create a Flux from a List that contains 2 values "foo" and "bar" */
	Flux<String> fooBarFluxFromList() {
		List<String> iterable = Arrays.asList("foo", "bar");
		return Flux.fromIterable(iterable);
	}

	/** Create a Flux that emits an IllegalStateException */
	Flux<String> errorFlux() {
		return Flux.<String>error(new IllegalStateException());
	}

	/** Create a Flux that emits increasing values from 0 to 9 each 100ms */
	Flux<Long> counter() {
		Flux<Long> initial = Flux.interval(Duration.ofMillis(100));
		return initial.take(10);
	}
}
