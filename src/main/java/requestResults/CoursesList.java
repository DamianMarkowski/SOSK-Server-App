package requestResults;

import java.util.List;

public class CoursesList {
	
	private List<Course> list;
	
	public CoursesList(){
		
	}
	
	public CoursesList(List<Course> l){
		this.list = l;
	}
	
	public List<Course> getList() {
		return list;
	}

	public void setList(List<Course> list) {
		this.list = list;
	}
	
}
