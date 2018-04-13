package requestResults;

public class PracticalTip {

	private String tip;
	
	private String date;

	public PracticalTip(String t,String d){
		this.tip = t;
		this.date = d;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
