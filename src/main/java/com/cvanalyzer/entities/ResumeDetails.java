package com.cvanalyzer.entities;

import java.util.List;

public class ResumeDetails {
       
	    private String candidateName;
	    private String candidateEmail;
	    private String candidatePhone;
	    private String percentageMatch;
	    private List<String> candidateSkills;
	    private List<String> candidateWorkExperience;
	    private List<String> candidateEducationalQualifications;
	    
	    public String getPercentageMatch() {
			return percentageMatch;
		}
		public void setPercentageMatch(String percentageMatch) {
			this.percentageMatch = percentageMatch;
		}

		private String rawCVText;
	    
		public String getCandidateName() {
			return candidateName;
		}
		public void setCandidateName(String candidateName) {
			this.candidateName = candidateName;
		}
		public String getCandidateEmail() {
			return candidateEmail;
		}
		public void setCandidateEmail(String candidateEmail) {
			this.candidateEmail = candidateEmail;
		}
		public String getCandidatePhone() {
			return candidatePhone;
		}
		public void setCandidatePhone(String candidatePhone) {
			this.candidatePhone = candidatePhone;
		}
		public List<String> getCandidateSkills() {
			return candidateSkills;
		}
		public void setCandidateSkills(List<String> candidateSkills) {
			this.candidateSkills = candidateSkills;
		}
		public List<String> getCandidateWorkExperience() {
			return candidateWorkExperience;
		}
		public void setCandidateWorkExperience(List<String> candidateWorkExperience) {
			this.candidateWorkExperience = candidateWorkExperience;
		}
		public List<String> getCandidateEducationalQualifications() {
			return candidateEducationalQualifications;
		}
		public void setCandidateEducationalQualifications(List<String> candidateEducationalQualifications) {
			this.candidateEducationalQualifications = candidateEducationalQualifications;
		}
		public String getRawCVText() {
			return rawCVText;
		}
		public void setRawCVText(String rawCVText) {
			this.rawCVText = rawCVText;
		} 
		
		 public String toString() {
		        return "Resume{" +
		                "name='" + candidateName + '\'' +
		                ", email='" + candidateEmail + '\'' +
		                ", phone='" + candidatePhone + '\'' +
		                ", skills=" + candidateSkills +
		                ", workExperience=" + candidateWorkExperience +
		                ", educationQualifications=" + candidateEducationalQualifications +
		                '}';
		    }


}
