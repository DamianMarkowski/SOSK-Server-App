package requestResults;

import java.util.List;

public class ClassesList {
	
	private List<Class> list;
	
	public ClassesList(){
		
	}
	
	public ClassesList(List<Class> l){
		this.list = l;
	}
	
	public List<Class> getList() {
		return list;
	}

	public void setList(List<Class> list) {
		this.list = list;
	}
}
