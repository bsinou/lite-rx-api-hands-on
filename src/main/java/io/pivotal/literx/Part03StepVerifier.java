/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.literx;

import java.time.Duration;
import java.util.function.Supplier;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of
 * Reactive Streams Publisher.
 *
 * @author Sebastien Deleuze
 * @see <a href=
 *      "http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier
 *      Javadoc</a>
 */
public class Part03StepVerifier {

	/**
	 * Uses a StepVerifier to check that the flux parameter emits "foo" and "bar"
	 * elements then completes successfully.
	 */
	void expectFooBarComplete(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo").expectNext("bar").expectComplete().verify();
	}

	/**
	 * Uses a StepVerifier to check that the flux parameter emits "foo" and "bar"
	 * elements then a RuntimeException error.
	 */
	void expectFooBarError(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo").expectNext("bar").expectError(RuntimeException.class).verify();
	}

	/**
	 * Uses StepVerifier to check that the flux parameter emits a User with "swhite"
	 * username and another one with "jpinkman" then completes successfully.
	 */
	void expectSkylerJesseComplete(Flux<User> flux) {
		StepVerifier.create(flux).consumeNextWith(u -> u.getUsername().equals("swhite"))
				.consumeNextWith(u -> u.getUsername().equals("jpinkman")).expectComplete().verify();
	}

	/** Expects 10 elements then complete and notice how long the test takes. */
	void expect10Elements(Supplier<Flux<Long>> supplier) {
		Duration duratiom = StepVerifier.withVirtualTime(supplier).thenAwait(Duration.ofSeconds(10L))
				.expectNextCount(10L).expectComplete().verify();
		System.out.printf("Verification took %d ms instead of 10s%n", duratiom.toMillis());
	}

	/**
	 * Expects 3600 elements at intervals of 1 second, and verify quicker than 3600s
	 * by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice
	 * how long the test takes
	 */
	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		Duration duration = StepVerifier.withVirtualTime(supplier).thenAwait(Duration.ofHours(1)).expectNextCount(3600)
				.verifyComplete();
		System.out.printf("Verification took %d ms instead of an hour%n", duration.toMillis());
	}
}
