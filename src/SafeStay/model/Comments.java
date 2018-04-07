package SafeStay.model;

public class Comments {

	protected int commentId;
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Incidents getIncidents() {
		return incidents;
	}

	public void setIncidents(Incidents incidents) {
		this.incidents = incidents;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected Users users;
	protected Incidents incidents;
	protected String description;

	public Comments(int commentId, Users user, Incidents incident, String description) {
		this.commentId = commentId;
		this.users = user;
		this.incidents = incident;
		this.description = description;

	}

	public Comments(Users user, Incidents incident, String description) {
		this.users = user;
		this.incidents = incident;
		this.description = description;
	}

	public Comments(int commentId) {
		this.commentId = commentId;
	}
	
	

}
