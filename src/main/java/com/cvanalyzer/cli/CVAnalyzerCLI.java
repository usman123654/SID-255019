package com.cvanalyzer.cli;

import com.cvanalyzer.services.ResumeParserService;
import com.cvanalyzer.services.ResumeMatcherService;

import java.util.Scanner;

public class CVAnalyzerCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n=== Resume Analysis System ===\n");

        while (running) {
            printMenu();
            System.out.print("Enter option: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    // Single step: parse resumes & match to JD
                    System.out.println("Enter job description (type 'END' to finish): ");
                    StringBuilder jdText = new StringBuilder();

                    // Collect multiline input from the console
                    String line;
                    while (!(line = scanner.nextLine()).equalsIgnoreCase("END")) {
                        jdText.append(line).append("\n"); // Add the new line to the job description
                    }

                    // Get the resumes folder path
                    System.out.print("Enter path to resumes folder: ");
                    String resumesPath = scanner.nextLine();

                    // Parse resumes once
                    ResumeParserService.uploadResumes(resumesPath);
                    // Now match the resumes to the typed job description
                    ResumeMatcherService.matchResumes(jdText.toString(), resumesPath);
                    System.out.println("✅ Matching completed!\n");
                    break;

                case "2":
                    // still allow viewing what was parsed
                    ResumeParserService.printParsedResumes();
                    break;

                case "3":
                    // view the top-5 shortlist from the database
                    ResumeMatcherService.viewShortlistedCandidates();
                    break;

                case "4":
                    System.out.println("Exiting. Goodbye! 👋");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nPlease choose an option:");
        System.out.println("1. Match Resumes to a Job Description (parse & match in one step)");
        System.out.println("2. View Parsed Resumes");
        System.out.println("3. View Shortlisted Candidates from Database");
        System.out.println("4. Exit");
    }
}
