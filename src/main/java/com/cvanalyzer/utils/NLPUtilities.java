package com.cvanalyzer.utils;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

public class NLPUtilities {

    private static TokenNameFinderModel nameModel;

    static {
        try (InputStream modelIn = NLPUtilities.class.getClassLoader().getResourceAsStream("models/en-ner-person.bin")) {
            if (modelIn != null) {
                nameModel = new TokenNameFinderModel(modelIn);
            } else {
                System.out.println("Name model not found. Please place en-ner-person.bin in resources/models/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Extract candidate name
    public static String extractName(String text) {
        if (nameModel == null) {
            System.out.println("Name model not loaded.");
            return null;
        }

        NameFinderME nameFinder = new NameFinderME(nameModel);
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text);

        Span[] nameSpans = nameFinder.find(tokens);

        if (nameSpans.length > 0) {
            return tokens[nameSpans[0].getStart()] + " " + tokens[nameSpans[0].getStart() + 1];
        }

        return null;
    }

    // Extract candidate email using regex
    public static String extractEmail(String text) {
        String emailRegex = "([a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + 
                            "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7})";
        
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    // Extract candidate phone numbers using regex
    public static String extractPhone(String text) {
        String phoneRegex = "(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}";
        
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    // Calculate match percentage (example with a simple placeholder logic)
    public static double calculateMatchPercentage(String cvText, String jobDescription) {
        // You can refine this with more sophisticated logic, like semantic matching or keyword analysis
        int matchingKeywords = 0;
        String[] cvWords = cvText.toLowerCase().split("\\W+");
        String[] jobWords = jobDescription.toLowerCase().split("\\W+");

        List<String> jobKeywords = new ArrayList<>();
        for (String word : jobWords) {
            jobKeywords.add(word);
        }

        for (String word : cvWords) {
            if (jobKeywords.contains(word)) {
                matchingKeywords++;
            }
        }

        double matchPercentage = ((double) matchingKeywords / cvWords.length) * 100;
        return matchPercentage;
    }

    // Extract candidate skills (simplified version using regex or keyword matching)
    public static List<String> extractSkills(String text) {
        List<String> skills = new ArrayList<>();
        
        // Example skills (you can expand this list with more technologies)
        String[] skillKeywords = {"Java", "Python", "JavaScript", "Spring", "SQL", "HTML", "CSS", "Docker", "Kubernetes"};
        
        for (String skill : skillKeywords) {
            if (text.toLowerCase().contains(skill.toLowerCase())) {
                skills.add(skill);
            }
        }

        return skills;
    }

    // Extract candidate technical proficiencies (you can expand the list as needed)
    public static List<String> extractTechnicalProficiencies(String text) {
        List<String> proficiencies = new ArrayList<>();
        
        // Example proficiencies (expand as needed)
        String[] techProficiencies = {"Cloud", "AWS", "Azure", "Machine Learning", "Data Science", "DevOps"};
        
        for (String proficiency : techProficiencies) {
            if (text.toLowerCase().contains(proficiency.toLowerCase())) {
                proficiencies.add(proficiency);
            }
        }

        return proficiencies;
    }

    // Extract previous work experience (simplified by looking for keywords such as "experience", "worked at", etc.)
    public static String extractWorkExperience(String text) {
        String[] workExperienceKeywords = {"experience", "worked at", "previous roles", "previous job"};
        
        for (String keyword : workExperienceKeywords) {
            if (text.toLowerCase().contains(keyword)) {
                return "Previous work experience found"; // You can refine this logic further to extract detailed information
            }
        }

        return "No work experience found";
    }

    // Extract previous job titles (simplified for example, you can expand this method with specific job title extraction logic)
    public static String extractJobTitles(String text) {
        String[] jobTitleKeywords = {"developer", "engineer", "manager", "lead", "consultant", "analyst"};

        for (String jobTitle : jobTitleKeywords) {
            if (text.toLowerCase().contains(jobTitle)) {
                return "Previous job title found: " + jobTitle; // Refine as needed
            }
        }

        return "No job title found";
    }
 // Extract candidate education (simplified version based on keywords)
    public static List<String> extractEducation(String text) {
        List<String> educationDetails = new ArrayList<>();
        
        // Example education-related keywords and phrases
        String[] educationKeywords = {"degree", "bachelor", "master", "university", "college", "graduated", "phd", "mba", "school"};
        
        // Split the text into sentences (you can adjust this depending on the format of the CV)
        String[] sentences = text.split("\\.\\s*");
        
        for (String sentence : sentences) {
            // Check if the sentence contains any of the education-related keywords
            for (String keyword : educationKeywords) {
                if (sentence.toLowerCase().contains(keyword.toLowerCase())) {
                    educationDetails.add(sentence.trim());  // Add the sentence containing the education info
                    break;  // Break after finding the keyword, to avoid duplicate additions
                }
            }
        }

        // Return the list of education-related sentences
        return educationDetails;
    }


}
