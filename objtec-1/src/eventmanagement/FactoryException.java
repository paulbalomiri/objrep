package eventmanagement;

public class FactoryException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public FactoryException(String description) {
		this.description=description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
