package t10.parallelstreams;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ParallelStreams {
	public static void main(final String[] args) {
		final long startClock = System.nanoTime();

		final int[] primes = IntStream.range(1, 50_000_000).parallel()
				.filter(number -> isPrime(number))
				.toArray();

		final long endClock = System.nanoTime();

		System.out.println("Found " + primes.length + " prime numbers");
		System.out.println("Time in seconds: " + elapsedTimeInSeconds(startClock, endClock));
	}

	private static long elapsedTimeInSeconds(final long startTime, final long endTime) {
		return TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
	}

	public static boolean isPrime(final int n) {
		if (n % 2 == 0) {
			return false;
		}
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
