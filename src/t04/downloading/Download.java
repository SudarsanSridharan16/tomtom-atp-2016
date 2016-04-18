package t04.downloading;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

@SuppressWarnings("unused")
public class Download {
	public static void main(final String a[]) throws Exception {

		final String jdkVersion = "8u74";
		final URL jdkUrl = getUrlToJdk(jdkVersion);
		final long jdkSizeInBytes = getJdkFileSize(jdkUrl);
		System.out.printf("JDK %s is %,d bytes big%n", jdkVersion, jdkSizeInBytes);

		final long startTime = System.nanoTime();

		serial(jdkUrl, jdkSizeInBytes);

		// parallel(jdkUrl, jdkSizeInBytes);

		final long endTime = System.nanoTime();

		System.out.printf("downloading took %d seconds", elapsedTimeInSeconds(startTime, endTime));
	}

	private static long elapsedTimeInSeconds(final long startTime, final long endTime) {
		return TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
	}

	private static void parallel(final URL jdkUrl, final long jdkSizeInBytes) throws InterruptedException {
		final Thread[] threads = new Thread[4];
		for (int i = 0; i < threads.length; ++i) {
			final long start = jdkSizeInBytes * i / threads.length;
			final long end = jdkSizeInBytes * (i + 1) / threads.length;
			final String downloadTaskName = "download" + (i + 1);
			threads[i] = new Thread(() -> downloadPart(downloadTaskName, jdkUrl, start, end));
			threads[i].start();
		}
		for (final Thread thread : threads) {
			thread.join();
		}
	}

	private static void serial(final URL jdkUrl, final long jdkSizeInBytes) {
		downloadPart("download1", jdkUrl, 0, jdkSizeInBytes);
	}

	private static void downloadPart(final String taskId, final URL url, final long startPart, final long endPart) {
		try {
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Range", String.format("bytes=%d-%d", startPart, endPart));
			connection.connect();

			final InputStream inputStream = connection.getInputStream();
			long size = 0;

			while (inputStream.read() != -1) {
				size++;
				if (size % 1000_000 == 0) {
					System.out.printf("%s: %,d / %,d%n", taskId, size, endPart - startPart);
				}
			}
			connection.disconnect();
			System.out.printf("%s downloaded: %,d bytes%n", taskId, size);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static URL getUrlToJdk(final String version) throws MalformedURLException, IOException {
		final URL url = new URL(
				"https://download.oracle.com/otn-pub/java/jdk/"
						+ version +
						"-b02/jdk-"
						+ version + "-linux-x64.tar.gz");
		final HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		urlConnection.setHostnameVerifier((hostname, session) -> true);
		urlConnection.setRequestProperty("Cookie", "oraclelicense=accept-securebackup-cookie");
		urlConnection.connect();

		System.out.println("Response Code: " + urlConnection.getResponseCode());
		System.out.println("Content-Length: " + urlConnection.getContentLengthLong());
		final String redirectLocation = urlConnection.getHeaderField("Location");
		System.out.println("Location: " + redirectLocation);
		System.out.println();
		urlConnection.disconnect();

		final URL redirectedUrl = new URL(redirectLocation);
		return redirectedUrl;
	}

	private static long getJdkFileSize(final URL jdkUrl) throws IOException {
		final HttpURLConnection connection = (HttpURLConnection) jdkUrl.openConnection();
		connection.connect();
		final long contentLength = connection.getContentLengthLong();
		connection.disconnect();
		return contentLength;
	}
}
