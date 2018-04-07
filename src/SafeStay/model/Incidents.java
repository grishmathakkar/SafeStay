package SafeStay.model;

import java.util.Date;

public class Incidents {
	protected int incidentId;
	public int getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}

	public Offense getOffense() {
		return offense;
	}

	public void setOffense(Offense offense) {
		this.offense = offense;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getReportingArea() {
		return reportingArea;
	}

	public void setReportingArea(int reportingArea) {
		this.reportingArea = reportingArea;
	}

	public Date getOccuredOnDate() {
		return occuredOnDate;
	}

	public void setOccuredOnDate(Date occuredOnDate) {
		this.occuredOnDate = occuredOnDate;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Shootings getShootings() {
		return shootings;
	}

	public void setShootings(Shootings shootings) {
		this.shootings = shootings;
	}

	public UCRs getUcRs() {
		return ucRs;
	}

	public void setUcRs(UCRs ucRs) {
		this.ucRs = ucRs;
	}

	protected Offense offense;
	protected String district;
	protected int reportingArea;
	protected Date occuredOnDate;
	protected String dayOfWeek;
	protected int hour;
	protected Address address;
	protected Shootings shootings;
	protected UCRs ucRs;
	
	public enum Shootings{
		Y,N
	}
	
	public enum UCRs{
		PartOne, PartTwo, PartThree
	}
	
	public Incidents(int incidentId, Offense offense, String district,
			int reportingArea, Shootings shooting, Date occuredOnDate,
			String dayOfWeek, int hour, UCRs ucr, Address address) {
		this.incidentId = incidentId;
		this.offense = offense;
		this.district = district;
		this.reportingArea = reportingArea;
		this.shootings = shooting;
		this.occuredOnDate = occuredOnDate;
		this.dayOfWeek = dayOfWeek;
		this.hour = hour;
		this.ucRs = ucr;
		this.address = address;
	}
	
	public Incidents(Offense offense, String district,
			int reportingArea, Shootings shooting, Date occuredOnDate,
			String dayOfWeek, int hour, UCRs ucr, Address address) {
		this.offense = offense;
		this.district = district;
		this.reportingArea = reportingArea;
		this.shootings = shooting;
		this.occuredOnDate = occuredOnDate;
		this.dayOfWeek = dayOfWeek;
		this.hour = hour;
		this.ucRs = ucr;
		this.address = address;
	}
	
	public Incidents(int incidentId) {
		this.incidentId = incidentId;
	}
	
}
