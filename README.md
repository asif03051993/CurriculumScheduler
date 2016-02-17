# CurriculumScheduler
As part of our Software Engineering course, I have developed curriculum scheduler, an application that assists in course fractalization. While it doesn't do the fractalization and subsequent scheduling of the courses for you, it could be a handy tool for  visualization and keeping a sanity check regarding various constraints (e.g prerequisites, no. of  credits, no. of LA credits, free electives per sem etc).

README FILE :
	
 <==========================================<>=============================================<>=========================================>
	
	CONTACT INFORMATION: For suggesting features, any questions, reporting bugs please contact us:
		 Name:					      Email Id:
		Asif Ahammed			cs10b034@iith.ac.in
		Siva Krishna			cs10b028@iith.ac.in
		Himakar					  cs10b039@iith.ac.in
		
	
	DATE: Friday, 2nd April, 2014 23:00
	
	Description:
		This software helps a student to select courses conveniently. The purpose is to schedule all the courses semester
		wise in a department for a batch based on the input file that contains the course details.
		
	System Requirements:
		Any Linux or Windows platform with JDK (Java Development Kit) (optional) and JRE (Java Runtime Environment) installed on it.
		
	Installation and Running instructions
		When CurriculumScheduler.rar file is extracted it contsina the following files
			a.Read Me File.txt
			b.Executable Jar folder
		1. Execute the SoftwareEngineering.jar file.
		2. In every step follow the instructions provided in the Instructions pane on the right side.
		3. In order to input the semester credits details either enter the semester details manually or import the SemInput.csv file.
		4. Now import the courses.csv file to input the courses available for selection.
				csv (Comma Separated Value) file stores tabular data (numbers and text) in plain-text form. 
				Courses.csv file contains CourseName (String), CourseID (String), Fractal (boolean), Credits (int), Type (String),
				PreRequisite(CourseID), MinSem (int) , MaxSem (int), ElementaryCourse1 (CourseID), ElementaryCourse2 (CourseID)				
		5. Use Export option to export your selection to either  csv file or xls file depending on your requirement.
		
 <==========================================<>=============================================<>=========================================>
