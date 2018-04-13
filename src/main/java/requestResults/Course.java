package requestResults;

public class Course {
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	
	private String status;

	public Course(){
		
	}
	
	public Course(int i,String n, String s){
		this.id = i;
		this.name = n;
		this.status = s;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
