public class Teacher extends Instructor {
    public Teacher(String name, char gender, int age){
        super(name, gender, age);
    }
    @Override
    public boolean canTeach(Subject subject){
        int specialism = subject.getSpecialism();
        return specialism == 1 || specialism == 2;
        // as spec says teacher can specialise in either 1 or 2 subjects
    }
}
