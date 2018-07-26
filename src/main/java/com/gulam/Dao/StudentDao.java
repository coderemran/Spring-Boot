package com.gulam.Dao;
import com.gulam.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Repository
public class StudentDao {

    @Autowired
    private static Map<Integer, Student> students;

    static {
        students = new HashMap< Integer, Student>(){
            {
                put(1, new Student(1, "Dave", "physics"));
                put(2, new Student(2, "Jon", "Islamic"));
                put(3, new Student(3, "Prince", "Computer"));
            }
        };
    }


    public Collection<Student> getAllStudents(){
        return students.values();
    }


    public Student getStudentById(int id){
        return this.students.get(id);
    }

    public Student removeStudentByID(int id){
        return this.students.remove(id);
    }

}
