package be.abis.exercise.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Repository;

import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;

@Repository
public class FilePersonRepository implements PersonRepository {

	private ArrayList<Person> allPersons;
	private String fileLoc = "c:\\UserTemp\\personsSpringDate.csv";

	public FilePersonRepository() {
		allPersons = new ArrayList<Person>();
		this.readFile();
	}

	@Override
	public ArrayList<Person> getAllPersons() {
		return allPersons;
	}

	public void readFile() {

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (allPersons.size() != 0)
			allPersons.clear();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileLoc));
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] vals = s.split(";");
				if (!vals[0].equals("")) {
					Address a = new Address();
					a.setStreet(!vals[10].equals("null") ? vals[10] : null);
					a.setNr(Integer.parseInt(!vals[11].equals("null") ? vals[11] : "0"));
					a.setZipcode(!vals[12].equals("null") ? vals[12] : null);
					a.setTown(!vals[13].equals("null") ? vals[13] : null);

					Company c = new Company();
					c.setName(!vals[7].equals("null") ? vals[7] : null);
					c.setTelephoneNumber(!vals[8].equals("null") ? vals[8] : null);
					c.setVatNr(!vals[9].equals("null") ? vals[9] : null);
					c.setAddress(a);

					Person p = new Person();
					p.setPersonId(!vals[0].equals("null") ? Integer.parseInt(vals[0]) : 0);
					p.setFirstName(!vals[1].equals("null") ? vals[1] : null);
					p.setLastName(!vals[2].equals("null") ? vals[2] : null);
					//p.setAge(Integer.parseInt(!vals[3].equals("null") ? vals[3] : "0"));
					p.setBirthDay(LocalDate.parse(!vals[3].equals("null") ? vals[3] : "01/01/0001", dateFormatter));
					p.setEmailAddress(!vals[4].equals("null") ? vals[4] : null);
					p.setPassword(!vals[5].equals("null") ? vals[5] : null);
					p.setLanguage(!vals[6].equals("null") ? vals[6] : null);
					p.setCompany(c);

					allPersons.add(p);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Person findPerson(String emailAddress, String passWord) {
		if (emailAddress == null || passWord == null) {
			return null;
		}

		this.readFile();
		// System.out.println("persons in PersonList" + allPersons);
		Iterator<Person> iter = allPersons.iterator();

		while (iter.hasNext()) {
			Person pers = iter.next();
			if (pers.getEmailAddress().equalsIgnoreCase(emailAddress) && pers.getPassword().equals(passWord)) {
				return pers;
			}
		}
		return null;
	}
	
	@Override
	public ArrayList<Person> findPersonByCompany(String compName) {
		ArrayList<Person> myPersons = new ArrayList<Person>();
		if (compName == null) 
		{
			return null;
		}

		this.readFile();
		// System.out.println("persons in PersonList" + allPersons);
		Iterator<Person> iter = allPersons.iterator();

		while (iter.hasNext()) 
		{
			Person pers = iter.next();
			if (pers.getCompany().getName().equalsIgnoreCase(compName)) {
				myPersons.add(pers);
			}
		}
		if (myPersons != null)
		{
			return myPersons;
		}
		else return null;
	}
	
	@Override
	public Person findPerson(int id) {
		this.readFile();
		return allPersons.stream().filter(p->p.getPersonId()==id).findFirst().orElse(null);
	}

	@Override
	public void addPerson(Person p) throws IOException {
		boolean b = false;
		this.readFile();
		Iterator<Person> iter = allPersons.iterator();
		PrintWriter pw = new PrintWriter(new FileWriter(fileLoc, true));
		while (iter.hasNext()) {
			Person pers = iter.next();
			if (pers.getEmailAddress().equalsIgnoreCase(p.getEmailAddress())) {
				throw new IOException("you were already registered, login please");
			} else {
				b = true;
			}
		}
		if (b) {
			StringBuilder sb = this.parsePerson(p);
			// System.out.println(sb);

			pw.append("\n" + sb);
			allPersons.add(p);

		}
		pw.close();
	}

	@Override
	public void deletePerson(int id) throws PersonCanNotBeDeletedException {
		Iterator<Person> iter = allPersons.iterator();
        boolean removed = false;
		while (iter.hasNext()) {
			Person pers = iter.next();
			if (pers.getPersonId()==id) {
				iter.remove();
				removed=true;
			}
		}
		
		if (!removed) throw new PersonCanNotBeDeletedException("Person with this id does not exist");

		try {
			this.writePersons();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void changePassword(Person p, String newPswd) throws IOException {
		Iterator<Person> iter = allPersons.iterator();
		while (iter.hasNext()) {
			Person pers = iter.next();
			if (pers.getEmailAddress().equals(p.getEmailAddress())) {
				pers.setPassword(newPswd);
			}
		}
		this.writePersons();
	}

	private StringBuilder parsePerson(Person p) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		int nr = p.getCompany().getAddress().getNr();
		sb.append(p.getPersonId() + ";").append(p.getFirstName() + ";").append(p.getLastName() + ";")
				.append((p.getBirthDay().format(dateFormatter) != ("01/01/0001") ? p.getBirthDay() : null) + ";").append(p.getEmailAddress() + ";")
				.append(p.getPassword() + ";").append(p.getLanguage().toLowerCase() + ";")
				.append(p.getCompany().getName() + ";").append(p.getCompany().getTelephoneNumber() + ";")
				.append(p.getCompany().getVatNr() + ";").append(p.getCompany().getAddress().getStreet() + ";")
				.append((nr != 0 ? nr : null) + ";").append(p.getCompany().getAddress().getZipcode() + ";")
				.append(p.getCompany().getAddress().getTown());

		System.out.println(sb);
		return sb;
	}

	private void writePersons() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(fileLoc));

		for (Person pe : allPersons) {
			StringBuilder sb = this.parsePerson(pe);
			pw.println(sb);
		}

		pw.close();
	}

}
