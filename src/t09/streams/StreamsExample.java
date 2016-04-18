package t09.streams;

import static java.util.Arrays.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsExample {

	public static void main(final String[] args) throws IOException {

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

		final List<Person> malesOnly = people.stream()
				// .filter(p->p.isMale())
				.filter(Person::isMale)
				.collect(Collectors.toList());
		System.out.println("males only:");
		malesOnly.forEach(System.out::println);
		System.out.println();

		final List<String> allNames = people.stream()
				.map(Person::getName)
				// .map(p->p.getName())
				.collect(Collectors.toList());
		System.out.println("just names:");
		allNames.forEach(System.out::println);
		System.out.println();

		final List<String> namesOfPeopleBelow20 = people.stream()
				.filter(p -> p.getAge() < 20)
				.map(Person::getName)
				.collect(Collectors.toList());
		System.out.println("names of people below 20:");
		namesOfPeopleBelow20.forEach(System.out::println);
		System.out.println();

		final List<String> allEmails = people.stream()
				.flatMap(p -> p.getEmails().stream())
				.collect(Collectors.toList());
		System.out.println("All emails:");
		allEmails.forEach(System.out::println);
		System.out.println();

		final List<String> distinctEmailDomains = people.stream()
				.flatMap(p -> p.getEmails().stream())
				.map(email -> email.substring(email.indexOf('@') + 1))
				.distinct()
				.collect(Collectors.toList());
		System.out.println("Distinct email domains:");
		distinctEmailDomains.forEach(System.out::println);
		System.out.println();
	}
}

class Person {
	private final String name;
	private final String surname;
	private final int age;
	private final boolean male;
	private final List<String> emails = new LinkedList<>();

	public Person(final String name, final String surname, final int age, final boolean male, final List<String> emails) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.male = male;
		this.emails.addAll(emails);
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getAge() {
		return age;
	}

	public boolean isMale() {
		return male;
	}

	public List<String> getEmails() {
		return emails;
	}

	@Override
	public String toString() {
		return String.format("Person [name=%s, surname=%s, age=%s, male=%s, emails=%s]", name, surname, age, male, emails);
	}

}