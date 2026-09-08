import java.util.ArrayList;

public class School {
    private String name;
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private ArrayList<Subject> subjects;
    private ArrayList<Course> courses;

    public School(String name) {
        this.name = name;
        students = new ArrayList<>();
        instructors = new ArrayList<>();
        subjects = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void add(Student student) {
        students.add(student);
    }

    public void add(Instructor instructor) {
        instructors.add(instructor);
    }

    public void add(Subject subject) {
        subjects.add(subject);
    }

    public void add(Course course) {
        courses.add(course);
    }

    public void remove(Student student) {
        students.remove(student);
    }

    public void remove(Instructor instructor) {
        instructors.remove(instructor);
    }

    public void remove(Subject subject) {
        subjects.remove(subject);
    }

    public void remove(Course course) {
        courses.remove(course);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public boolean hasOpenCourse(Subject subject) {
        for (Course course : courses) {
            if (course.getSubject() == subject && course.getStatus() != 0 && !course.isCancelled()) {
                return true; // j checking if course is available
            }
        }
        return false;
    }

    public boolean isStudentBusy(Student student) {//making a for loop to check if the student is busy
        for (Course course : courses) {
            Student[] enrolled = course.getStudents();
            for (Student p : enrolled) {
                if (p == student && course.getStatus() != 0 && !course.isCancelled()) {// if all conditions are met then student is busy so cant attend said course as they alr in class or smt to that effect
                    return true;
                }
            }
        }
        return false;
    }

    public void aDayAtSchool() {
        for (Subject subject : subjects) {
            if (!hasOpenCourse(subject)) {
                courses.add(new Course(subject, 2));// course starts in 2 days
            }
        }
        for (Course course : courses) {
            if (!course.hasInstructor()) {
                for (Instructor instructor : instructors) {// find instructor
                    if (instructor.getAssignedCourse() == null) {//make sure they free
                        if (course.setInstructor(instructor)) {//can teach
                            break;
                        }
                    }
                }
            }
        }
        for (Student student : students) {
            if (!isStudentBusy(student)) {
                for (Course course : courses) {
                    if (course.getStatus() < 0 && course.getSize() < 3) {// make sure enough space and is available
                        if (!student.hasCertificate(course.getSubject())) {
                            if (course.enrolStudent(student)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (Course course : courses) {
            course.aDayPasses();
        }
        ArrayList<Course> removeCourses = new ArrayList<>();// array for removed courses
        for (Course course : courses) {
            if (course.getStatus() == 0) {
                removeCourses.add(course);// find course finished or cancelled
            }
        }
        courses.removeAll(removeCourses);// now remove all courses
    }
    public String toString () {
            String text = "School" + name + "\n";
            text = text + "\nSubjects:\n";
            for (Subject subject : subjects) {
                text = text + subject.getID() + " - " + subject.getDescription() + "\n";
            }
            text = text + "\nInstructors:\n";
            for (Instructor instructor : instructors) {
                text = text + instructor.getName() + "\n";
            }
            text = text + "\nStudents:\n";
            for (Student student : students) {
                text = text + student.getName() + "\n";
            }
            text = text + "\nCourses:\n";
            for (Course course : courses) {
                int status = course.getStatus();

                String statusText;
                if (course.isCancelled()) {
                    statusText = "Cancelled";
                } else if (status > 0) {
                    statusText = status + " days left";
                } else if (status < 0) {
                    statusText = "Starts in " + (-status) + " days"; // - status as the no. is negative rn
                } else {
                    statusText = "Finished";
                }
                text = text + course.getSubject().getDescription() + " : " + statusText + "\n";

            }
            return text;
        }
    public String getName() {
        return name;
    }
}


