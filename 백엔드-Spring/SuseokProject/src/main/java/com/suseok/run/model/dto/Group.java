package com.suseok.run.model.dto;

public class Group {
	private int groupId;
	private String groupName;
	private int groupAdmin;
	private Double goalPace;
	private Double goalFrequency;
	private Double goalTotalDistance;
	private Double conPace;
	private Double conFrequency;
	private Double conTotalDistance;
	private Double pace;
	private Double frequency;
	private Double totalDistance;

	public Group() {
	}

	public Group(int groupId, String groupName, int groupAdmin, Double goalPace, Double goalFrequency,
			Double goalTotalDistance, Double conPace, Double conFrequency, Double conTotalDistance, Double pace,
			Double frequency, Double totalDistance) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupAdmin = groupAdmin;
		this.goalPace = goalPace;
		this.goalFrequency = goalFrequency;
		this.goalTotalDistance = goalTotalDistance;
		this.conPace = conPace;
		this.conFrequency = conFrequency;
		this.conTotalDistance = conTotalDistance;
		this.pace = pace;
		this.frequency = frequency;
		this.totalDistance = totalDistance;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupAdmin() {
		return groupAdmin;
	}

	public void setGroupAdmin(int groupAdmin) {
		this.groupAdmin = groupAdmin;
	}

	public Double getGoalPace() {
		return goalPace;
	}

	public void setGoalPace(Double goalPace) {
		this.goalPace = goalPace;
	}

	public Double getGoalFrequency() {
		return goalFrequency;
	}

	public void setGoalFrequency(Double goalFrequency) {
		this.goalFrequency = goalFrequency;
	}

	public Double getGoalTotalDistance() {
		return goalTotalDistance;
	}

	public void setGoalTotalDistance(Double goalTotalDistance) {
		this.goalTotalDistance = goalTotalDistance;
	}

	public Double getConPace() {
		return conPace;
	}

	public void setConPace(Double conPace) {
		this.conPace = conPace;
	}

	public Double getConFrequency() {
		return conFrequency;
	}

	public void setConFrequency(Double conFrequency) {
		this.conFrequency = conFrequency;
	}

	public Double getConTotalDistance() {
		return conTotalDistance;
	}

	public void setConTotalDistance(Double conTotalDistance) {
		this.conTotalDistance = conTotalDistance;
	}

	public Double getPace() {
		return pace;
	}

	public void setPace(Double pace) {
		this.pace = pace;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

}
