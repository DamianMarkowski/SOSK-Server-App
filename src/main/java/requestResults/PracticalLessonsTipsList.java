package requestResults;

import java.util.List;

public class PracticalLessonsTipsList {
	
	private List<PracticalTip> list;
	
	public PracticalLessonsTipsList(){
		
	}
	
	public PracticalLessonsTipsList(List<PracticalTip> l){
		this.list = l;
	}
	
	public List<PracticalTip> getList() {
		return list;
	}

	public void setList(List<PracticalTip> list) {
		this.list = list;
	}
}