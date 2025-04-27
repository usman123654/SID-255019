package com.cvanalyzer.entities;


import java.util.List;

public class JobPosting_SID_2355019 {

    private String jobTitle;
    private List<String> requiredSkills;
    private String jobDescription;


    public String getJobTitle() {
		return jobTitle;
	}


	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}


	public List<String> getRequiredSkills() {
		return requiredSkills;
	}


	public void setRequiredSkills(List<String> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}


	public String getJobDescription() {
		return jobDescription;
	}


	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}


	@Override
    public String toString() {
        return "JobDescription{" +
                "jobTitle='" + jobTitle + '\'' +
                ", requiredSkills=" + requiredSkills +
                ", description='" + jobDescription + '\'' +
                '}';
    }
}
