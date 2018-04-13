package requestResults;

import java.util.List;

public class StudentsList {
	
	private List<Student> list;
	
	public StudentsList(){
		
	}
	
	public StudentsList(List<Student> l){
		this.list = l;
	}
	
	public List<Student> getList() {
		return list;
	}

	public void setList(List<Student> list) {
		this.list = list;
	}
}
