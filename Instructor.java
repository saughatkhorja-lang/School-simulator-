public abstract class Instructor extends Person {
//want to make a specific instructor what I mean is a math instructor and so on
    private Course assignedCourse;

    public Instructor(String name, char gender, int age){
        super(name, gender, age);
        assignedCourse = null;
    }
    public void assignCourse(Course course){
        assignedCourse = course;
    }
    public void unassignCourse(){
        assignedCourse = null;
    }
    public Course getAssignedCourse(){
        return assignedCourse;
    }
    public abstract boolean canTeach(Subject subject);
}
