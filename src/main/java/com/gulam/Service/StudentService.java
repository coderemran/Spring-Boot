package com.gulam.Service;

import com.gulam.Dao.StudentDao;
import com.gulam.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;


    public Collection<Student> getAllStudents(){

        return this.studentDao.getAllStudents();
    }

    public Student getStudentById(int id){

        return this.studentDao.getStudentById(id);

    }

    public Student removeStudentByID(int id){
        return this.studentDao.removeStudentByID(id);
    }


}
