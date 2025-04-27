package com.cvanalyzer.services;

import com.cvanalyzer.entities.ResumeDetails_SID_2355019;
import com.cvanalyzer.entities.JobPosting_SID_2355019;
import com.cvanalyzer.connect.DBConnection_SID_2355019;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

public class ResumeMatcherService_SID_2355019 {

    public static void matchResumes(String jobDescriptionText, String resumesFolderPath) {
        // Use job description passed directly as a String
        JobPosting_SID_2355019 jobDescription = new JobPosting_SID_2355019();
        jobDescription.setJobDescription(jobDescriptionText);
        
        // Extract required skills directly from the job description
        List<String> skills = extractSkillsFromText(jobDescriptionText);
        jobDescription.setRequiredSkills(skills);

        // Ensure resumes are parsed and available
        ResumeParserService_SID_2355019.uploadResumes(resumesFolderPath);
        List<ResumeDetails_SID_2355019> resumes = ResumeParserService_SID_2355019.getParsedResumes();

        if (resumes == null || resumes.isEmpty()) {
            System.out.println("❌ No resumes uploaded yet. Please upload resumes first (Option 1).");
            return;
        }

        Map<ResumeDetails_SID_2355019, Integer> scoredResumes = new HashMap<>();
        int maxPossibleScore = (jobDescription.getRequiredSkills().size() * 10) + 20 + 10;

        for (ResumeDetails_SID_2355019 resume : resumes) {
            int score = calculateScore(resume, jobDescription);
            scoredResumes.put(resume, score);
        }

        List<Map.Entry<ResumeDetails_SID_2355019, Integer>> sorted = scoredResumes.entrySet()
                .stream()
                .sorted(Map.Entry.<ResumeDetails_SID_2355019, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());

        System.out.println("\n=== Top 5 Resume Match Results ===\n");

        try (Connection conn = DBConnection_SID_2355019.getConnection()) {
            for (Map.Entry<ResumeDetails_SID_2355019, Integer> entry : sorted) {
                ResumeDetails_SID_2355019 resume = entry.getKey();
                int score = entry.getValue();
                double percentage = (double) score / maxPossibleScore * 100;

                String candidateName = resume.getCandidateName() != null ? resume.getCandidateName() : "N/A";
                String candidateEmail = resume.getCandidateEmail() != null ? resume.getCandidateEmail() : "N/A";
                String candidatePhone = resume.getCandidatePhone() != null ? resume.getCandidatePhone() : "N/A";

                String candidateSkills = (resume.getCandidateSkills() != null)
                        ? String.join(", ", resume.getCandidateSkills()) : "N/A";

                String technicalProficiencies = candidateSkills;

                String previousWorkExperience = (resume.getCandidateWorkExperience() != null)
                        ? String.join("; ", resume.getCandidateWorkExperience()) : "N/A";

                String previousJobTitles = extractJobTitles(resume);

                // Extracting qualifications
                String qualifications = (resume.getCandidateEducationalQualifications() != null)
                        ? String.join(", ", resume.getCandidateEducationalQualifications()) : "N/A";

                System.out.printf("Name: %-30s | Match: %.2f%% | Email: %s | Phone: %s | Qualifications: %s\n",
                        candidateName, percentage, candidateEmail, candidatePhone, qualifications);

                // Insert into database
                String insertSQL = "INSERT INTO cvanalyzer.shortlisted_candidates " +
                        "(candidate_name, candidate_email, candidate_phone, match_percentage, candidate_skills, technical_proficiencies, previous_work_experience, previous_job_titles) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    pstmt.setString(1, candidateName);
                    pstmt.setString(2, candidateEmail);
                    pstmt.setString(3, candidatePhone);
                    pstmt.setDouble(4, percentage);
                    pstmt.setString(5, candidateSkills);
                    pstmt.setString(6, technicalProficiencies);
                    pstmt.setString(7, previousWorkExperience);
                    pstmt.setString(8, previousJobTitles);
                    pstmt.executeUpdate();
                }
            }
            System.out.println("\n✅ Top 5 candidates successfully saved to database!\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> extractSkillsFromText(String text) {
        List<String> predefinedSkills = Arrays.asList(
                "Java", "Spring", "Spring Boot", "SQL", "Docker", "Kubernetes",
                "React", "Angular", "Node.js", "Hibernate", "JPA", "Struts",
                "Jasper Reports", "HTML", "CSS", "JavaScript", "Oracle", "PostgreSQL", "MySQL"
        );

        List<String> foundSkills = new ArrayList<>();
        for (String skill : predefinedSkills) {
            if (text.toLowerCase().contains(skill.toLowerCase())) {
                foundSkills.add(skill);
            }
        }
        return foundSkills;
    }

    private static int calculateScore(ResumeDetails_SID_2355019 resume, JobPosting_SID_2355019 jobDescription) {
        int score = 0;

        if (resume.getRawCVText() == null) return score;

        for (String skill : jobDescription.getRequiredSkills()) {
            if (resume.getRawCVText().toLowerCase().contains(skill.toLowerCase())) {
                score += 10; // Each skill match gives 10 points
            }
        }

        // Bonus points for experience
        if (resume.getCandidateWorkExperience() != null) {
            for (String exp : resume.getCandidateWorkExperience()) {
                if (exp.toLowerCase().contains("software engineer") ||
                    exp.toLowerCase().contains("developer") ||
                    exp.toLowerCase().contains("programmer")) {
                    score += 20;
                    break;
                }
            }
        }

        // Bonus points for education
        if (resume.getCandidateEducationalQualifications() != null) {
            for (String edu : resume.getCandidateEducationalQualifications()) {
                if (edu.toLowerCase().contains("master") || edu.toLowerCase().contains("bachelor")) {
                    score += 10;
                    break;
                }
            }
        }

        return score;
    }

    private static String extractJobTitles(ResumeDetails_SID_2355019 resume) {
        if (resume.getCandidateWorkExperience() == null) return "N/A";

        List<String> titles = new ArrayList<>();
        for (String exp : resume.getCandidateWorkExperience()) {
            if (exp.toLowerCase().contains("software engineer")) {
                titles.add("Software Engineer");
            } else if (exp.toLowerCase().contains("operational officer")) {
                titles.add("Operational Officer");
            } else if (exp.toLowerCase().contains("data entry operator")) {
                titles.add("Data Entry Operator");
            }
        }
        if (titles.isEmpty()) return "N/A";
        return String.join(", ", titles);
    }

    public static void viewShortlistedCandidates() {
        System.out.println("\n=== Shortlisted Candidates from Database ===\n");

        String query = "SELECT * FROM cvanalyzer.shortlisted_candidates ORDER BY match_percentage DESC";

        try (Connection conn = DBConnection_SID_2355019.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             java.sql.ResultSet rs = pstmt.executeQuery()) {

            int count = 0;
            while (rs.next()) {
                count++;
                String name = rs.getString("candidate_name");
                String email = rs.getString("candidate_email");
                String phone = rs.getString("candidate_phone");
                double matchPercentage = rs.getDouble("match_percentage");
                String skills = rs.getString("candidate_skills");
                String jobTitles = rs.getString("previous_job_titles");
                
                System.out.println("Candidate #" + count);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.printf("Match Percentage: %.2f%%\n", matchPercentage);
                System.out.println("Skills: " + skills);
                System.out.println("Previous Job Titles: " + jobTitles);
                System.out.println("-------------------------------------------");
            }

            if (count == 0) {
                System.out.println("No shortlisted candidates found in the database.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
