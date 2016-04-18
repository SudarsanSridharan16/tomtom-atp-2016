package t07.deadlocks;

public class Deadlocks {
	public static void main(final String[] args) throws InterruptedException {
		final BankAccount kowalski = new BankAccount("Jan Kowalski", 1_000_000);
		final BankAccount nowak = new BankAccount("Jerzy Nowak", 1_000_000);

		final Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				kowalski.transfer(nowak, 1000);
			}
		});
		final Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				nowak.transfer(kowalski, 1000);
			}
		});
		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println(kowalski);
		System.out.println(nowak);
	}
}

class BankAccount {
	private final String id;
	private int balanceInCents;

	public BankAccount(final String id, final int balanceInCents) {
		this.id = id;
		this.balanceInCents = balanceInCents;
	}

	public synchronized void withdraw(final int centAmount) {
		this.balanceInCents -= centAmount;
	}

	public synchronized void deposit(final int centAmount) {
		this.balanceInCents += centAmount;
	}

	public void transfer(final BankAccount target, final int centAmount) {
		synchronized (this) {
			synchronized (target) {
				this.withdraw(centAmount);
				target.deposit(centAmount);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("BankAccount [id=%s, balanceInCents=%s]", id, balanceInCents);
	}

}