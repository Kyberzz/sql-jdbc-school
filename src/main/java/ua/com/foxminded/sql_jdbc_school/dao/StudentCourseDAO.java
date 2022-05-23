package ua.com.foxminded.sql_jdbc_school.dao;

import java.util.List;
import ua.com.foxminded.sql_jdbc_school.dao.entities.StudentCourseEntity;

public interface StudentCourseDAO extends GenericDAO<StudentCourseEntity, Integer>{
    
    public int deleteStudentFromCourse(int studentId, int courseId) throws DAOException;
    public List<StudentCourseEntity> read(int studentId, int courseId) throws DAOException;
    public List<StudentCourseEntity> readStudentsOfCourse(int courseID) throws DAOException;
    public int createStudentCourseTable() throws DAOException; 
}
