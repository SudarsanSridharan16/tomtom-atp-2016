
package t06.memoryconsistency;

public class MemoryConsistency {

	public static void main(final String[] args) throws InterruptedException {
		System.out.println("Started main");

		class BackgroundAction extends Thread {

			public boolean finished = false;

			@Override
			public void run() {
				try {
					System.out.println("Starting background action");
					Thread.sleep(1000L);
					finished = true;
					Thread.sleep(1000L);
					// do more things here
					System.out.println("Finished background action");
				} catch (final InterruptedException e) {
				}
			}
		}

		final BackgroundAction action = new BackgroundAction();
		action.start();

		while (!action.finished) {
			// this is a semi-infinite loop
		}

		System.out.println("Exiting main");

	}

}
