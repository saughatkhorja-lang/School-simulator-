public class Course {
    private Subject subject;
    private int daysUntilStarts;
    private int daysToRun;
    private Student[] students;
    private Instructor instructor;
    private boolean cancelled;

    public Course(Subject subject, int daysUntilStarts) {
        this.subject = subject;
        this.daysUntilStarts = daysUntilStarts;
        this.daysToRun = subject.getDuration();
        this.students = new Student[3];
        instructor = null;
        cancelled = false;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getStatus() {
        if (isCancelled()) {
            return 0;
        }
        if (daysUntilStarts > 0) {
            return -daysUntilStarts;
        }
        if (daysToRun > 0) {
            return daysToRun;
        }
        return 0;
    }

    public boolean enrolStudent(Student student) {
        if (daysUntilStarts <= 0) {
            throw new IllegalStateException("Cannot enrol student on a course that has already started.");
        }
        if (isCancelled()) {
            throw new IllegalStateException("Cannot enrol student on a cancelled course.");        }
        if (getSize() >= 3) { /* if size is 2 we can enroll one more student but if its 3 students we cant enroll
                                 anymore as the max is 3*/
            return false;
        }
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                students[i] = student;
                return true; /* create a loop to get 3 students 1st iteration would be
                              [allan, null(empty space), null(empty space)] and so on after each iteration*/
            }
        }
        return false;
    }

    public int getSize() {
        int count = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i] != null) {// j checking if we have space for more students
                count = count + 1;
            }
        }
        return count;
    }

    public Student[] getStudents() {
        return students;
    }

    public boolean setInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("No instructor assigned.");
        }
        if (!instructor.canTeach(subject)) {
            throw new IllegalArgumentException("Instructor cannot teach this subject.");
        }
        this.instructor = instructor;
        instructor.assignCourse(this);
        return true;
    }

    public boolean hasInstructor() {
        return instructor != null;
    }

    public boolean isCancelled() {
        return cancelled;
    }


    public void aDayPasses() {
        if (isCancelled()) {
            return;
        }
        if (daysToRun == 0) {
            return;
        }
        if (daysUntilStarts > 0) {
            daysUntilStarts--;

            if (daysUntilStarts == 0) {
                if (!hasInstructor() || getSize() == 0) {
                    cancelled = true;

                    if (instructor != null) {
                        instructor.unassignCourse();
                    }
                }
            }
        }
        else if (daysToRun > 0) {
            daysToRun--;
            if (daysToRun == 0) {
                for (Student s : students) {
                    if (s != null) {
                        s.graduate(subject);
                    }
                }
                if (instructor != null) {
                    instructor.unassignCourse();
                            }
                        }
                    }
                }
    public int getDaysUntilStarts() {
        return daysUntilStarts;
    }

    public int getDaysToRun() {
        return daysToRun;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setDaysToRun(int daysToRun) {
        this.daysToRun = daysToRun;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    public void forceAddStudent(Student student){//imagine we load a save where the course alr started i had code that made it so u cant be added to it if it alr stated so this bypasses the rule.
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {// find empty slots for students
                students[i] = student;
                return;
            }
        }

        throw new IllegalStateException("Course is full while loading");
    }

    public void forceSetInstructor(Instructor instructor){
        this.instructor = instructor;
    }
}