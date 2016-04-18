package t10.parallelstreams;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class Fibonacci {

	public static void main(final String[] args) {
		final long start = System.nanoTime();

		final long[] fibonacci = LongStream.rangeClosed(1, 48)
				.parallel()
				.map(Fibonacci::fib)
				.sorted()
				.toArray();

		final long end = System.nanoTime();
		System.out.println(Arrays.toString(fibonacci));
		System.out.println("seconds: " + TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS));
	}

	static public long fib(final long n) {
		if (n <= 1L) {
			return 1L;
		}
		if (n == 2L) {
			return 2L;
		}
		return fib(n - 1L) + fib(n - 2L);
	}
}
