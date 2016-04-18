package t09.streams;

import static java.util.Arrays.*;

import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamsExample {
	public static void main(final String[] args) {
		final List<Person> people = asList(
				new Person("Jan", "Kowalski", 20, true, asList("jk@gmail.com", "jk@outlook.com")),
				new Person("Jerzy", "Kowalski", 21, true, asList("jk2@gmail.com", "jk2@outlook.com")),
				new Person("Adam", "Nowak", 22, true, asList("an@gmail.com", "an@outlook.com")),
				new Person("Adrian", "Nowak", 23, true, asList("an2@gmail.com", "an2@outlook.com")),
				new Person("Agata", "Nowacka", 24, false, asList("an3@gmail.com", "an3@outlook.com")),
				new Person("Barbara", "Zięba", 25, false, asList("bz@gmail.com", "bz@outlook.com")),
				new Person("Cedryk", "Witkacy", 25, true, asList("cw@gmail.com", "cw@outlook.com")),
				new Person("Genowefa", "Kowal", 24, false, asList("gk@gmail.com", "gk@outlook.com")),
				new Person("Hubert", "Hrabia", 23, true, asList("hh@gmail.com", "hh@outlook.com")),
				new Person("Witold", "Witkacy", 22, true, asList("ww@gmail.com", "ww@outlook.com")),
				new Person("Euzebiusz", "Sasanka", 21, true, asList("es@gmail.com", "es@outlook.com")),
				new Person("Roman", "Huncwot", 19, true, asList("rh@gmail.com", "rh@outlook.com")),
				new Person("Ewelina", "Kiełbasa", 27, false, asList("ek@gmail.com", "ek@outlook.com")));

		final long startTime = System.nanoTime();
		final List<String> collect = people.parallelStream()
				.filter(Person::isMale)
				.map(Person::getName)
				.collect(Collectors.toList());
		final long endTime = System.nanoTime();
		System.out.println(collect);
		System.out.println("nanos: " + (endTime - startTime));

	}
}
