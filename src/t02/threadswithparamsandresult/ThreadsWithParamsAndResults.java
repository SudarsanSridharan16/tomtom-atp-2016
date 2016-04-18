package t02.threadswithparamsandresult;

import java.util.Arrays;

public class ThreadsWithParamsAndResults {
	public static void main(final String[] args) throws Exception {
		final int[] numbers = { 4, 2, 7, 5, 3, 0 };

		final TableSortTask sortTask = new TableSortTask(numbers);
		final Thread t = new Thread(sortTask, "sortTask");
		t.start();
		t.join();

		final int[] sortedNumbers = sortTask.getSortedArray();
		System.out.println(" Input numbers: " + Arrays.toString(numbers));
		System.out.println("Output numbers: " + Arrays.toString(sortedNumbers));
	}
}

class TableSortTask implements Runnable {

	private final int[] array;

	public TableSortTask(final int[] array) {
		this.array = Arrays.copyOf(array, array.length);
	}

	public int[] getSortedArray() {
		return array;
	}

	@Override
	public void run() {
		Arrays.sort(array);
	}

}
