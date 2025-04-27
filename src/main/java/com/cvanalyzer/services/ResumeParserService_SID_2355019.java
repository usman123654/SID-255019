package com.cvanalyzer.services;

import com.cvanalyzer.entities.ResumeDetails_SID_2355019;
import com.cvanalyzer.utils.FileParserUtilities_SID_2355019;
import com.cvanalyzer.utils.NLPUtilities_SID_2355019;
import org.apache.tika.Tika;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResumeParserService_SID_2355019 {

    private static List<ResumeDetails_SID_2355019> parsedResumes = new ArrayList<>();

    public static List<ResumeDetails_SID_2355019> parseResumesFromFolder(String folderPath) {
        parsedResumes.clear(); // Clear previous data
        File[] files = FileParserUtilities_SID_2355019.getFilesFromFolder(folderPath);
        Tika tika = new Tika();

        for (File file : files) {
            try {
                String text = tika.parseToString(file);  // Extract resume text

                // Print the extracted text for debugging
                System.out.println("Extracted Resume Text:\n" + text);

                ResumeDetails_SID_2355019 resume = new ResumeDetails_SID_2355019();
                resume.setRawCVText(text);

                // Perform further extraction on this text (name, email, phone, etc.)
                resume.setCandidateName(NLPUtilities_SID_2355019.extractName(text));  // Assuming `extractName()` works
                resume.setCandidateEmail(NLPUtilities_SID_2355019.extractEmail(text));
                resume.setCandidatePhone(NLPUtilities_SID_2355019.extractPhone(text));
                resume.setCandidateEducationalQualifications(NLPUtilities_SID_2355019.extractEducation(text));
                parsedResumes.add(resume);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parsedResumes;
    }


    public static void uploadResumes(String folderPath) {
        parseResumesFromFolder(folderPath);
    }

    // ✅ THIS METHOD is what was missing
    public static List<ResumeDetails_SID_2355019> getParsedResumes() {
        return parsedResumes;
    }

    public static void printParsedResumes() {
        if (parsedResumes.isEmpty()) {
            System.out.println("No resumes parsed yet. Please upload first.");
            return;
        }

        System.out.println("\n=== Parsed Resumes ===\n");
        for (ResumeDetails_SID_2355019 resume : parsedResumes) {
            System.out.println("Name: " + (resume.getCandidateName() != null ? resume.getCandidateName() : "N/A"));
            System.out.println("Email: " + (resume.getCandidateEmail() != null ? resume.getCandidateEmail() : "N/A"));
            System.out.println("Phone: " + (resume.getCandidatePhone() != null ? resume.getCandidatePhone() : "N/A"));
            System.out.println("Percentage Match: " + (resume.getCandidatePhone() != null ? resume.getCandidatePhone() : "N/A"));

            System.out.println("----------------------------------");
        }
    }
}
