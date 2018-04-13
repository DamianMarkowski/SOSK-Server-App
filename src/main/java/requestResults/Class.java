package requestResults;

public class Class {
	
	private String dateAndTime;
	
	private String lessonType;

	public Class(){
		
	}
	
	public Class(String d, String lt){
		this.dateAndTime = d;
		this.lessonType = lt;
	}
	
	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getLessonType() {
		return lessonType;
	}

	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}
	
}
