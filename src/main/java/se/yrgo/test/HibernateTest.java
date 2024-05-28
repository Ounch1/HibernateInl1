package se.yrgo.test;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import se.yrgo.domain.Student;
import se.yrgo.domain.Tutor;

public class HibernateTest {

    private static SessionFactory sessionFactory = null;

    public static void main(String[] args) {

        SessionFactory sf = getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        
        Tutor newTutor = new Tutor("ABC234", "Natalie Woodward", 387787);
        Student student1 = new Student("Patrik Howard");
        Student student2 = new Student("Marie Sani");
        Student student3 = new Student("Tom Nikson");

        newTutor.addStudentToTeachingGroup(student1);
        newTutor.addStudentToTeachingGroup(student2);
        newTutor.addStudentToTeachingGroup(student3);
                
        session.save(student1);
        session.save(student2);
		session.save(student3);
		session.save(newTutor);

        
        // Extract a tutor from the database and print it
        Tutor tutor = session.get(Tutor.class, 4); // If you get an error here, check the id of the tutor in your database
        List<Student> students = tutor.getTeachingGroup();

        System.out.printf("===================== PRINTING STUDENTS =====================\n\n");
        System.out.println("Tutor: " + tutor.getName());
        for(Student student : students) {
            System.out.println(student);
        }
        System.out.printf("\n===================== =============== =====================\n");
        
        

        tx.commit();

        session.close();
    }


    private static SessionFactory getSessionFactory() {
        if(sessionFactory ==null) {
            Configuration configuration = new Configuration();
            configuration.configure();

            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

}

