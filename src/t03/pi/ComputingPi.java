package t03.pi;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class ComputingPi {

	public static void main(final String[] args) throws Exception {
		final int computationsAmount = 8;
		final int precision = 60_000;

		final long clockStart = System.nanoTime();

		final BigDecimal[] results = serial(computationsAmount, precision);
		// final BigDecimal[] results = parallel(computationsAmount, precision);

		final long clockEnd = System.nanoTime();

		System.out.println("Computing pis took: " + elapsedSeconds(clockStart, clockEnd) + " seconds");
		System.out.println(results);
	}

	private static long elapsedSeconds(final long clockStart, final long clockEnd) {
		return TimeUnit.SECONDS.convert(clockEnd - clockStart, TimeUnit.NANOSECONDS);
	}

	private static BigDecimal[] serial(final int computationsAmount, final int precision) {
		final BigDecimal[] results = new BigDecimal[computationsAmount];
		for (int i = 0; i < computationsAmount; ++i) {
			final BigDecimal pi = Pi.computePi(precision);
			results[i] = pi;
		}
		return results;
	}

	private static BigDecimal[] parallel(final int computationsAmount, final int precision) throws InterruptedException {
		class ComputingPiTask extends Thread {

			private final int precision;
			private BigDecimal pi;

			public ComputingPiTask(final int precision) {
				this.precision = precision;
			}

			@Override
			public void run() {
				this.pi = Pi.computePi(precision);
			}

			public BigDecimal getPi() {
				return pi;
			}

		}

		final ComputingPiTask[] tasks = new ComputingPiTask[computationsAmount];
		final BigDecimal[] results = new BigDecimal[computationsAmount];
		for (int i = 0; i < computationsAmount; ++i) {
			tasks[i] = new ComputingPiTask(precision);
			tasks[i].start();
		}
		for (int i = 0; i < tasks.length; i++) {
			tasks[i].join();
			results[i] = tasks[i].getPi();
		}
		return results;
	}
}
