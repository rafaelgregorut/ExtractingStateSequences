package ltl.extraction;

public class Property {

	protected String type;
		
	protected String representation;
	
	public final static String EXISTENCE = "EXISTENCE";

	public final static String PRECEDENCE = "PRECEDENCE";

	Property() {
		representation = null;
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
}
