package t01.creatingthreads;

import java.util.concurrent.ThreadLocalRandom;

public class CreatingThreads {
	public static void main(final String[] args) throws InterruptedException {
		final Thread countdown = new CountdownThread();
		countdown.start();

		final Thread countup = new Thread(new CountupRunnable());
		countup.start();

		final Thread repeat10 = new Thread(new Runnable() {

			@Override
			public void run() {
				final ThreadLocalRandom random = ThreadLocalRandom.current();
				try {
					for (int i = 0; i <= 10; ++i) {
						System.out.println("My fav number is 10");
						Thread.sleep(random.nextLong(1000L));
					}
				} catch (final InterruptedException e) {
				}
			}
		});
		repeat10.start();

		final Thread repeat7 = new Thread(() -> {
			final ThreadLocalRandom random = ThreadLocalRandom.current();
			try {
				for (int i = 0; i <= 10; ++i) {
					System.out.println("My fav number is 7");
					Thread.sleep(random.nextLong(1000L));
				}
			} catch (final InterruptedException e) {
			}
		});
		repeat7.start();

		repeat10.join();
		repeat7.join();
		countup.join();
		countdown.join();

		Thread.sleep(2000L);

		System.out.println("Exiting main");
	}
}

class CountupRunnable implements Runnable {

	@Override
	public void run() {
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		try {
			for (int i = 0; i <= 10; ++i) {
				System.out.println("Countup " + i);
				Thread.sleep(random.nextLong(1000L));
			}
		} catch (final InterruptedException e) {
		}
	}

}

class CountdownThread extends Thread {
	@Override
	public void run() {
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		try {
			for (int i = 10; i >= 0; --i) {
				System.out.println("Countdown " + i);
				Thread.sleep(random.nextLong(1000L));
			}
		} catch (final InterruptedException e) {
		}
	}
}
