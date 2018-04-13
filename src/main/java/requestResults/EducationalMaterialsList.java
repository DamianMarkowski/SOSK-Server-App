package requestResults;

import java.util.List;

public class EducationalMaterialsList {

	private List<EducationalMaterial> list;
	
	public EducationalMaterialsList(){
		
	}
	
	public EducationalMaterialsList(List<EducationalMaterial> l){
		this.list = l;
	}
	
	public List<EducationalMaterial> getList() {
		return list;
	}

	public void setList(List<EducationalMaterial> list) {
		this.list = list;
	}
}