package SafeStay.model;

import java.sql.Timestamp;

public class Maintains {

	protected Administrators administrators;

	public Administrators getAdministrators() {
		return administrators;
	}

	public void setAdministrators(Administrators administrators) {
		this.administrators = administrators;
	}

	public Incidents getIncidents() {
		return incidents;
	}

	public void setIncidents(Incidents incidents) {
		this.incidents = incidents;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	protected Incidents incidents;
	protected Timestamp created;

	public Maintains(Administrators administrators, Incidents incidents, Timestamp created) {
		this.administrators = administrators;
		this.incidents = incidents;
		this.created = created;
	}

	public Maintains(Administrators administrators) {
		this.administrators = administrators;
	}

}
