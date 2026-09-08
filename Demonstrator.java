public class Demonstrator extends Instructor{
    public Demonstrator(String name, char gender, int age){
        super(name, gender, age);
    }
    @Override
    public boolean canTeach(Subject subject) {
        return subject.getSpecialism() == 2;
        // if not 2 return false ofc j checking they can teach 2 as they are a Demonstrator
    }
}
