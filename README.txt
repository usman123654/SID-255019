1. This is a Maven-based Java project, developed using JDK 17. It uses a pom.xml file to automatically download the required Java libraries.
2. In this project, I have included libraries like Apache Tika and OpenNLP. Tika is used to extract text from files of different formats like PDF, RTF, and TXT, while OpenNLP helps in processing natural language from resumes.
3. I am using a pre-trained model called en-ner-person.bin from Apache OpenNLP to accurately detect and extract names from resumes.
4. This project can run on any operating system — Mac, Linux, or Windows — as long as JRE 17 is installed.

INSTRUCTIONS FOR RUNNING THE PROJECT: 
5. After exporting the project as a JAR file, you can run it from the Command Prompt or Terminal using the following command:
java -jar SID-2355019.jar

6. Before running the project install postgresql as the relational database management system on your pc using the following credentials: - 

Port: 5432
Host:localhost
Username: postgres
Password: 

7. Install dbeaver or pycharm for executing sql queries using GUIs. 

8. After connecting dbeaver with postgresql using above mentioned credentials create a schema named cvanalyzer in postgresql database. 

9. Inside the cvanalyzer schema create a table named shortlisted_candidates
using the following sql script: - 

CREATE TABLE cvanalyzer.shortlisted_candidates (
	id serial NOT NULL,
	candidate_name varchar(255) NULL,
	candidate_email varchar(255) NULL,
	candidate_phone varchar(50) NULL,
	match_percentage numeric(5,2) NULL,
	candidate_skills text NULL,
	technical_proficiencies text NULL,
	previous_work_experience text NULL,
	previous_job_titles text NULL,
	created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT shortlisted_candidates_pkey PRIMARY KEY (id));



10. After running this project, we see the menu as : - 

=== Resume Analysis System ===

Please choose an option:
1. Match Resumes to a Job Description (parse & match in one step)
2. View Parsed Resumes
3. View Shortlisted Candidates from Database
4. Exit
Enter option: 



After that, we can continue working with the application based on our needs.
11. The CVAnalyzerCLI_SID_2355019 class provides a menu with four options and handles user input from the Command Prompt or Terminal.
12. The DBConnection_SID_2355019 class is used to connect to the database and contains database security related entities  such as database’s user’s  username and password trying to connect to the DB, which in this project is PostgreSQL as the relational database management system.
13. When resumes are matched against a specific job description using Option 1, the top five  matching candidates — along with their names, phone numbers, and matching scores — are saved into a database table called shortlisted_candidates.
14. You can also parse resumes separately using Option 2, and view the shortlisted candidates using Option 3.
15. Option 4 closes the application.
16. I have created entities like ResumeDetails_SID_2355019 and JobPosting_SID_2355019 to temporarily store candidate’s information and job-related fields, such as job title and job description.
17. The ResumeMatcherService_SID_2355019 class includes methods for displaying the shortlisted candidates on the console from the database. It also has methods for parsing resumes, saving the top 5 matched candidates based on the job description into the database, and extracting details like the candidate’s name, phone number, email etc.
18.The ResumeParserService_SID_2355019 class provides methods for parsing multiple resumes from a folder and printing the parsed results to the console or command line.
