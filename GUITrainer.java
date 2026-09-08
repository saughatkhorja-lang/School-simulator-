public class GUITrainer extends Teacher{
    public GUITrainer(String name, char gender, int age){
        super(name, gender, age);
    }
    @Override
    public boolean canTeach(Subject subject) {
        int specialism = subject.getSpecialism();
        return specialism == 1 || specialism == 2 || specialism == 4;
    }
}
