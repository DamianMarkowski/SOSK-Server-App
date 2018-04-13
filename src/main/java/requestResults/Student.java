package requestResults;

public class Student {

	int id;
	
	private String firstName;
	
	private String lastName;

	public Student(){
		
	}
	
	public Student(int id, String f, String l){
		this.id = id;
		this.firstName = f;
		this.lastName = l;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
