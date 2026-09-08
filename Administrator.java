import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class Administrator {

    private School school;
    private Random random;
    private int nextStudentNumber;
    private int nextInstructorNumber;

    public Administrator(School school) {
        this.school = school;
        this.random = new Random();
        this.nextStudentNumber = 1;
        this.nextInstructorNumber = 1;
    }

    public void run() {
        while (true) {
            simulateOneDay();
        }
    }

    public void run(int days) {
        for (int i = 1; i <= days; i++) {
            System.out.println("Day " + i);
            simulateOneDay();
            printState();
            System.out.println();
        }
    }

    private void simulateOneDay() {
        admitStudents();
        admitInstructors();
        school.aDayAtSchool();
        endOfDayUpdates();
    }

    private void admitStudents() {
        int newStudents = random.nextInt(3);// only admit up to 2 students
        for (int i = 0; i < newStudents; i++) {
            String name = "Student" + nextStudentNumber;
            char gender = randomGender();
            int age = 18 + random.nextInt(20);//18 is minimum age 18-37 is my age range
            Student student = new Student(name, gender, age);
            school.add(student);
            nextStudentNumber = nextStudentNumber + 1; // ensure no duplicates
        }
    }

    private void admitInstructors() {// ive j made a range of ages I thought would be appropriate
        if (randomChance(20)) {
            Teacher teacher = new Teacher("Teacher" + nextInstructorNumber, randomGender(), 25 + random.nextInt(31));
            school.add(teacher);
            nextInstructorNumber = nextInstructorNumber + 1;
        }
        if (randomChance(10)) {
            Demonstrator demonstrator = new Demonstrator("Demonstrator" + nextInstructorNumber, randomGender(), 22 + random.nextInt(19));
            school.add(demonstrator);
            nextInstructorNumber = nextInstructorNumber + 1;
        }
        if (randomChance(5)) {
            OOTrainer trainer = new OOTrainer("OOTrainer" + nextInstructorNumber, randomGender(), 30 + random.nextInt(16));
            school.add(trainer);
            nextInstructorNumber = nextInstructorNumber + 1;
        }
        if (randomChance(5)) {
            GUITrainer trainer = new GUITrainer("GUITrainer" + nextInstructorNumber, randomGender(), 30 + random.nextInt(16));
            school.add(trainer);
            nextInstructorNumber = nextInstructorNumber + 1;
        }
    }

    private void removeInstructorsLeaving() {// im j making array to have one big bin to release all at once later
        ArrayList<Instructor> leaving = new ArrayList<Instructor>();
        for (Instructor instructor : school.getInstructors()) {
            if (instructor.getAssignedCourse() == null) {
                if (randomChance(20)) {
                    leaving.add(instructor);
                }
            }
        }
        for (Instructor instructor : leaving) {
            school.remove(instructor);
        }
    }

    private void removeStudentsLeaving() {//obv if they have certificate they gonna leave and if they arent they have 5% chacne of leaving
        ArrayList<Student> leaving = new ArrayList<Student>();
        for (Student student : school.getStudents()) {
            if (hasCertificates(student)) {
                leaving.add(student);
            } else if (!isEnrolled(student)) {
                if (randomChance(5)) {
                    leaving.add(student);
                }
            }
        }
        for (Student student : leaving) {
            school.remove(student);
        }
    }

    private boolean hasCertificates(Student student) {//certificates as multiple certificate needed
        for (Subject subject : school.getSubjects()) {
            if (!student.hasCertificate(subject)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEnrolled(Student student) {
        for (Course course : school.getCourses()) {
            for (Student p : course.getStudents()) {
                if (p == student) {
                    return true;
                }
            }
        }
        return false;
    }

    private Course getStudentCourse(Student student) {
        for (Course course : school.getCourses()) {
            for (Student p : course.getStudents()) {
                if (p == student) {
                    return course;
                }
            }
        }
        return null;
    }

    private boolean randomChance(int percent) {
        int number = random.nextInt(100);
        return number < percent;
    }

    private char randomGender() {
        if (random.nextBoolean()) {
            return 'M';
        }
        return 'F';
    }

    private void endOfDayUpdates() {
        removeInstructorsLeaving();
        removeStudentsLeaving();
    }

    private String getCourseStatus(Course course) {
        int status = course.getStatus();
        if (course.isCancelled()) {
            return "Is Cancelled";
        }
        if (status < 0) {// status >0 means its running <0 means it hasnt started
            return "Starts in " + (-status) + " days";
        }
        if (status > 0) {
            return "Running: " + status + " days left";
        }
        return "Finished";// if status  == 0 its obv finished
    }

    private void printState() {
        System.out.println("Courses: ");
        for (Course course : school.getCourses()) {
            System.out.println(course.getSubject().getDescription() + " - " + getCourseStatus(course));
            System.out.println("Students: ");
            boolean hasStudents = false;
            for (Student student : course.getStudents()) {
                if (student != null) {
                    System.out.println(student.getName() + " ");
                    hasStudents = true;
                }
            }
            if (!hasStudents) {
                System.out.print("None");
            }
            System.out.println();//j for to make it look clean
        }
        System.out.println("Students: ");
        for (Student student : school.getStudents()) {
            System.out.print(student.getName());
            System.out.print(" | Certificates: " + student.getCertificates());
            System.out.print(" | Course: ");
            Course course = getStudentCourse(student);
            if (course == null) {
                System.out.println("None");
            } else {
                System.out.println(course.getSubject().getDescription());
            }
        }
        System.out.println("Instructors:");
        for (Instructor instructor : school.getInstructors()) {
            System.out.print(instructor.getName());
            System.out.print(" | Course: ");
            if (instructor.getAssignedCourse() == null) {
                System.out.println("None");
            } else {
                System.out.println(instructor.getAssignedCourse().getSubject().getDescription());
            }

        }
    }

    public static School loadSchool(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        School school = null;// j make an empty school

        while (scanner.hasNextLine()) {//read lines and remove spaces and skips empty lines ofc
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String type = line.substring(0, line.indexOf(":"));//gets subject from subject: 1,2,3 blah blah and so on
            String data = line.substring(line.indexOf(":") + 1).trim();// gets the stuff after subject and student etc
            switch (type) {
                case "school":
                    school = new School(data);
                    break;

                case "subject":
                    if (school != null) {
                        school.add(readSubject(line));
                    }
                    break;

                case "student":
                    if (school != null) {
                        school.add(readStudent(line));
                    }
                    break;

                case "Teacher":
                    if (school != null) {
                        school.add(readTeacher(line));
                    }
                    break;

                case "Demonstrator":
                    if (school != null) {
                        school.add(readDemonstrator(line));
                    }
                    break;

                case "OOTrainer":
                    if (school != null) {
                        school.add(readOOTrainer(line));
                    }
                    break;

                case "GUITrainer":
                    if (school != null) {
                        school.add(readGUITrainer(line));
                    }
                    break;
            }
        }
        scanner.close();
        return school;
    }

    private static Subject readSubject(String line) {
        String data = line.substring(8);
        String[] parts = data.split(",");// I want individual subjects

        String description = parts[0].trim();//im sectioning of parts off then combining later in a better format
        int id = Integer.parseInt(parts[1].trim());
        int specialism = Integer.parseInt(parts[2].trim());
        int duration = Integer.parseInt(parts[3].trim());

        return new Subject(id, specialism, duration, description);
    }

    private static Student readStudent(String line) {//im j rinse and repeating ://////////
        String data = line.substring(8);
        String[] parts = data.split(",");

        String name = parts[0].trim();
        char gender = parts[1].trim().charAt(0);
        int age = Integer.parseInt(parts[2].trim());

        return new Student(name, gender, age);
    }

    private static Teacher readTeacher(String line) {
        String data = line.substring(8);
        String[] parts = data.split(",");

        String name = parts[0].trim();
        char gender = parts[1].trim().charAt(0);
        int age = Integer.parseInt(parts[2].trim());

        return new Teacher(name, gender, age);
    }

    private static Demonstrator readDemonstrator(String line) {
        String data = line.substring(13);
        String[] parts = data.split(",");

        String name = parts[0].trim();
        char gender = parts[1].trim().charAt(0);
        int age = Integer.parseInt(parts[2].trim());

        return new Demonstrator(name, gender, age);
    }

    private static OOTrainer readOOTrainer(String line) {
        String data = line.substring(10);
        String[] parts = data.split(",");

        String name = parts[0].trim();
        char gender = parts[1].trim().charAt(0);
        int age = Integer.parseInt(parts[2].trim());

        return new OOTrainer(name, gender, age);
    }

    private static GUITrainer readGUITrainer(String line) {
        String data = line.substring(11);
        String[] parts = data.split(",");

        String name = parts[0].trim();
        char gender = parts[1].trim().charAt(0);
        int age = Integer.parseInt(parts[2].trim());

        return new GUITrainer(name, gender, age);
    }

    public void saveSimulation(String filename) {
        String saveFile = filename + ".save.txt";

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(saveFile));
            writer.println("school:" + school.getName());
            writer.println("admin:" + nextStudentNumber + "," + nextInstructorNumber);

            for(Subject subject : school.getSubjects()){//create a loop to get each subject info
                writer.println("subject:" + subject.getDescription() + "," + subject.getID() + "," + subject.getSpecialism() + "," + subject.getDuration());
            }
            for(Student student : school.getStudents()){
                String certificates = "";//create a place for all their certificates:p

                for(int i = 0; i < student.getCertificates().size(); i++){//loop to show what certificates they have
                    certificates = certificates + student.getCertificates().get(i);

                    if (i < student.getCertificates().size() - 1) {
                        certificates = certificates + ",";
                    }
                }
                writer.println("student:" + student.getName() + "," + student.getGender() + "," + student.getAge() + "," + certificates);
            }
            for(Instructor instructor : school.getInstructors()){
                String type = instructor.getClass().getSimpleName();// abit more complex cos we need to know if its a teacher or smt and so on
                int courseIndex = school.getCourses().indexOf(instructor.getAssignedCourse());// writing course as indec so [0] is first course so like calc 1 would be [0] for exmaple idek boo hooo

                writer.println("instructor:" + type + "," + instructor.getName() + "," + instructor.getGender() + "," + instructor.getAge() + "," + courseIndex);
            }
            for(Course course : school.getCourses()){
                int subjectID = course.getSubject().getID();
                int daysUntilStarts = course.getDaysUntilStarts();
                int daysToRun = course.getDaysToRun();
                boolean cancelled = course.isCancelled();

                String studentIndexes = ""; // create empty string to hold stuff
                Student[] students = course.getStudents();

                for (Student student : students) {
                    if (student != null) {

                        int index = -1;//not found yet;)

                        for (int j = 0; j < school.getStudents().size(); j++) {
                            if (school.getStudents().get(j) == student) {
                                index = j;
                            }
                        }

                        if (studentIndexes.isEmpty()) {
                            studentIndexes = "" + index;
                        }
                        else {
                            studentIndexes = studentIndexes + "," + index;
                        }
                    }
                }

                int instructorIndex = -1;

                for(int i = 0; i < school.getInstructors().size(); i++){
                    if(school.getInstructors().get(i) == course.getInstructor()){
                        instructorIndex = i;
                    }
                }

                writer.println("course:" + subjectID + "," + daysUntilStarts + "," + daysToRun + "," + cancelled + "," + studentIndexes + "," + instructorIndex);
            }
            writer.close();
            System.out.println("Simulation saved to: " + saveFile);
        }
        catch(IOException e){
            System.out.println("Error saving simulation: " + e.getMessage());
        }
    }


    public static Administrator loadSim(String filename){
        School school = null;
        int nextStudentNumber = 1;
        int nextInstructorNumber = 1;


        ArrayList<String> courseLines = new ArrayList<String>();
        try{
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.isEmpty()){
                    continue;
                }//j a bunch of waffle that I did earlier
                if(line.startsWith("school:")){
                    String name = line.substring(7).trim();
                    school = new School(name);
                }
                else if(line.startsWith("admin:")){
                    String[] parts = line.substring(6).split(",");
                    if(parts.length != 2){//
                        throw new IllegalArgumentException("Insufficient number of values - Malformed admin line: " + line);
                    }
                    nextStudentNumber = Integer.parseInt(parts[0].trim());
                    nextInstructorNumber = Integer.parseInt(parts[1].trim());
                }
                else if(line.startsWith("subject:")){
                    if(school == null){
                        throw new IllegalArgumentException("School not named I need the name before subjects broski");
                    }
                    Subject subject = readSubject(line);
                    school.add(subject);
                }
                else if(line.startsWith("student:")){
                    if(school == null){
                        throw new IllegalArgumentException("cmon broksi what did i say u gotta define School smh");
                    }
                    Student student = readSavedStudent(line);
                    school.add(student);
                }
                else if(line.startsWith("instructor:")){
                    if(school == null){
                        throw new IllegalArgumentException("Nt next time define the School first");
                    }
                    Instructor instructor = readSavedInstructor(line);
                    school.add(instructor);
                }
                else if(line.startsWith("course:")){
                    courseLines.add(line);// keep here cos we need other info first ygm ggggg
                }
                else{
                    throw new IllegalArgumentException("Unknown line type idk what u sending me smh:p : " + line);
                }
            }
            scanner.close();
            if(school == null){
                throw new IllegalArgumentException("No School founddddd in save file");
            }
            ArrayList<Course> loadedCourses = new ArrayList<Course>();
            for(int i = 0; i < courseLines.size(); i++){
                String line = courseLines.get(i);
                String[] parts = line.substring(7).split(",",6);//splitting into 6 pieces
                if(parts.length !=6){
                    throw new IllegalArgumentException("not enough information - Malformed course line: " + line);
            }
                int subjectID = Integer.parseInt(parts[0].trim());
                int daysUntilStarts = Integer.parseInt(parts[1].trim());
                int daysToRun = Integer.parseInt(parts[2].trim());
                boolean cancelled = Boolean.parseBoolean(parts[3].trim());

                Subject subject = findSubjectByID(school, subjectID);
                if(subject == null){
                    throw new IllegalArgumentException("Subject ID not found: " + subjectID);
                }
                    Course course = new Course(subject, daysUntilStarts);
                course.setDaysToRun(daysToRun);
                course.setCancelled(cancelled);

                school.add(course);
                loadedCourses.add(course);

            }
            for(int i = 0; i < courseLines.size(); i++){

                String line = courseLines.get(i);
                String[] parts = line.substring(7).split(",", 6);

                Course course = loadedCourses.get(i);

                String studentPart = parts[4].trim();
                String instructorPart = parts[5].trim();

                if(!studentPart.isEmpty()){

                    String[] studentNumbers = studentPart.split(",");

                    for(int j = 0; j < studentNumbers.length; j++){

                        int studentIndex = Integer.parseInt(studentNumbers[j].trim());

                        if(studentIndex < 0 || studentIndex >= school.getStudents().size()){//checking validity
                            throw new IllegalArgumentException("Invalid student index: " + studentIndex);
                        }

                        Student student = school.getStudents().get(studentIndex);
                        course.forceAddStudent(student);
                    }
                }

                int instructorIndex = Integer.parseInt(instructorPart);

                if(instructorIndex != -1){

                    if(instructorIndex < 0 || instructorIndex >= school.getInstructors().size()){
                        throw new IllegalArgumentException("Invalid instructor index: " + instructorIndex);
                    }

                    Instructor instructor = school.getInstructors().get(instructorIndex);
                    course.forceSetInstructor(instructor);
                    instructor.assignCourse(course);
                }
            }

            Administrator admin = new Administrator(school);
            admin.nextStudentNumber = nextStudentNumber;
            admin.nextInstructorNumber = nextInstructorNumber;

            return admin;
        }
        catch(FileNotFoundException e){
            System.out.println("save file not found big sadge:/");
        }
        catch(NumberFormatException e){
            System.out.println("Error: invalid number in save file;)");
        }
        catch(IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("Error loading sim:(: " + e.getMessage());
        }

        return null;
    }
    private static Subject findSubjectByID(School school, int id) {
        for(Subject subject : school.getSubjects()){
            if(subject.getID() == id){
                return subject;
            }
        }

        return null;
    }
    private static Student readSavedStudent(String line) {
        String[] parts = line.substring(8).split(",", 4);

        if (parts.length < 3){
            throw new IllegalArgumentException("I cant think of a quip smh im washed. Malformed student line: " + line);
        }

        String name = parts[0].trim();
        char gender = parts[1].trim().charAt(0);
        int age = Integer.parseInt(parts[2].trim());

        Student student = new Student(name, gender, age);

        if(parts.length == 4){
            String certificates = parts[3].trim();

            if(!certificates.isEmpty()){
                String[] certificateParts = certificates.split(",");

                for(int i = 0; i < certificateParts.length; i++){
                    int certificateID = Integer.parseInt(certificateParts[i].trim());
                    student.getCertificates().add(certificateID);
                }
            }
        }

        return student;
    }
    private static Instructor readSavedInstructor(String line) {
        String[] parts = line.substring(11).split(",");

        if(parts.length < 4){
            throw new IllegalArgumentException("Malformed instructor line: " + line);
        }

        String type = parts[0].trim();
        String name = parts[1].trim();
        char gender = parts[2].trim().charAt(0);
        int age = Integer.parseInt(parts[3].trim());

        if(type.equals("Teacher")){
            return new Teacher(name, gender, age);
        }
        else if (type.equals("Demonstrator")) {
            return new Demonstrator(name, gender, age);
        }
        else if (type.equals("OOTrainer")) {
            return new OOTrainer(name, gender, age);
        }
        else if (type.equals("GUITrainer")) {
            return new GUITrainer(name, gender, age);
        }
        else {
            throw new IllegalArgumentException("Man who u looking for cos bro aint here. Unknown instructor type: " + type);
        }
    }
    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("Not enough info given");
            return;
        }

        try{
            String filename = args[0];
            int days = Integer.parseInt(args[1]);

            Administrator admin = null;

            if(filename.endsWith(".save.txt")){
                admin = loadSim(filename);
            }
            else{
                School school = loadSchool(filename);

                if(school == null){
                    System.out.println("Error: could not load school");
                    return;
                }

                admin = new Administrator(school);
            }

            if(admin == null){
                System.out.println("Error: could not start simulation");
                return;
            }

            admin.run(days);
        }
        catch(NumberFormatException e){
            System.out.println("Error: invalid number of days");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
