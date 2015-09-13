package ltl.extraction;

public class Property {

	protected String type;
		
	protected String representation;
	
	protected String meaning;
	
	Property() {
		representation = null;
		meaning = null;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRepresentation() {
		return representation;
	}
	
	public void setRepresentation(String prop) {
		this.representation = prop;
	}
	
	public void setMeaning(String mean) {
		this.meaning = mean;
	}
	
	public String getMeaning() {
		return this.meaning;
	}
}
