package t05.threadinterleaving;

public class ThreadInterleaving {
	public static void main(final String[] args) throws InterruptedException {
		final Counter counterTask = new Counter();

		final int threadsAmount = 10;
		final Thread[] threads = new Thread[threadsAmount];
		for (int i = 0; i < threads.length; ++i) {
			threads[i] = new Thread(counterTask);
			threads[i].start();
		}
		for (final Thread thread : threads) {
			thread.join();
		}

		System.out.println("Counter should be: " + Counter.LOOPS * threadsAmount);
		System.out.println("Counter is: " + counterTask.counter);
	}
}

class Counter implements Runnable {
	static public final int LOOPS = 1_000_000;
	public int counter = 0;

	@Override
	public void run() {
		for (int i = 0; i < LOOPS; i++) {
			synchronized (this) {
				counter++;
			}
		}
	}
}
