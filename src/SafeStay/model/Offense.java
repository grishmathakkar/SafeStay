package SafeStay.model;

public class Offense {
	protected int offenceCode;
	protected String description;
	
	public Offense(int offenceCode, String description) {
		this.offenceCode = offenceCode;
		this.description = description;
	}
	
	public Offense(int offenceCode) {
		this.offenceCode = offenceCode;
	}
	
	public Offense(String description) {
		this.description = description;
	}
	
	public int getOffenceCode() {
		return offenceCode;
	}

	public void setOffenceCode(int offenceCode) {
		this.offenceCode = offenceCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public String getCodeGroup() {
//		return codeGroup;
//	}
//
//	public void setCodeGroup(String codeGroup) {
//		this.codeGroup = codeGroup;
//	}

}
