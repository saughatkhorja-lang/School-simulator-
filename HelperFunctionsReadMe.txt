hasOpenCourse - I needed this to check if said course is available because imagine I was trying to assign a student to calculus but it was cancelled and we assign student to that in the timetable which would then prevent us from assigning them to another course which would actually be available. Of course got to make sure the course hasn't started either because imagine catching up on the course after missing half the course it would be so hard and essentially redundant.

isStudentBusy - Making sure student is busy to avoid clashes in timetable for example in semester 1 I had a clash between stats and programming and forced me to ditch one class each time to do the other class.

getDaysUntilStarts - I need to know this information so I can use it in saveSimulation().

getDaysToRun - I need to know this information because I can use it to check if something has gone wrong in my save beacuse imagine yesterday the course had 3 days till it ended so logically if I open everything load everything today and simulate everything and stuff I should have 2 days left in the course now imagine that said 5 days left in course.

getInstructor - I need to have this so I can use it on my other methods with instructors. 

setDaysToRun - So we can override any runtime state previously saved.

setCancelled - Know what courses are cancelled so when we load simulation it remembers it.

forceAddStudent - Imagine we load a save where the course already started I had code that made it so you cant be added to it if it already stated so this bypasses the rule.

forceSetInstructor - bypasses rules when loading a simulation just like with forceAddStudent.

admitStudents - So I can admit the correct no. of students and get information/give information of admitted student.

admitInstructors - So I can admit instructors depending on chances given.

endOfDayUpdates - Somewhere I can put removing instructors and students.

removeInstructorsLeaving - I need to know which instructors are leaving.

removeStudentsLeaving - I need to know which students are leaving be it they are graduating or just dropping out.

hasCertificates - Want to know which certificates the student has to know if they graduate to not. 

isEnrolled - Want to know if they are enrolled of course.

getStudentCourse - I want course of each student because think about it its actually so useless having the student with out what course they doing.

randomChance - I was using random chance for instructors leaving and what not to get the correct percentages. Got to make sure the probabilities are correct.

randomGender - I need to know gender to assign.

getCourseStatus - I want to know if its running cancelled or about to start because imagine its cancelled and I just don't know and assign 3 students to that course a bit stupid don't you think.

printState - I want to know course, student and instructor status.

readSubject - I want it to give me information of Subject from file.

readStudent - I want it to give me information of Student from file.

readTeacher - I want it to give me information of Teacher from file.

readDemonstrator - I want it to give me information of Demonstrator from file.

readOOTrainer - I want it to give me information of OOTrainer from file.

readGUITrainer - I want it to give me information of GUITrainer from file.

findSubjectByID - Easier and quicker in my opinion to search for by id than characters.

readSavedStudent - I want it to give me information of Student from save.

readSavedInstructor - I want it to give me information of Instructor from save.
