import java.util.ArrayList;



public class Student extends Person{
    private ArrayList<Integer> certificates;

    public Student(String name, char gender, int age){
        super(name, gender, age);
        certificates = new ArrayList<Integer>();
    }
    public void graduate(Subject subject){
        certificates.add(subject.getID());
    }
    public ArrayList<Integer> getCertificates(){
        return certificates;
    }
    public boolean hasCertificate(Subject subject){
        return certificates.contains(subject.getID());
    }
}

