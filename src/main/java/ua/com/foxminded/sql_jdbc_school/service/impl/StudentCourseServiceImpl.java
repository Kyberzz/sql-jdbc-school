package ua.com.foxminded.sql_jdbc_school.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.foxminded.sql_jdbc_school.dao.CourseDAO;
import ua.com.foxminded.sql_jdbc_school.dao.DAOException;
import ua.com.foxminded.sql_jdbc_school.dao.StudentCourseDAO;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDAO;
import ua.com.foxminded.sql_jdbc_school.dao.entities.CourseEntity;
import ua.com.foxminded.sql_jdbc_school.dao.entities.StudentCourseEntity;
import ua.com.foxminded.sql_jdbc_school.dao.entities.StudentEntity;
import ua.com.foxminded.sql_jdbc_school.service.ServiceException;
import ua.com.foxminded.sql_jdbc_school.service.StudentCourseService;
import ua.com.foxminded.sql_jdbc_school.service.dto.CourseDto;
import ua.com.foxminded.sql_jdbc_school.service.dto.StudentDto;

public class StudentCourseServiceImpl implements StudentCourseService<List<StudentDto>, 
                                                           List<CourseDto>,
                                                           List<StudentDto>,
                                                           Integer> {
	
	private static final Logger LOGGER = LogManager.getLogger();
    private static final String ERROR_GET_STUDENTS_OF_COURSE = "The getting students of specified course failed.";
    private static final String ERROR_ADD_STUDENT = "Adding the student to the course failed.";
    private static final int BAD_STATUS = 0;
    private static final int NORMAL_STATUS = 1;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
    
    public StudentCourseServiceImpl(StudentCourseDAO studentCourseDAO, StudentDAO studentDAO,
			CourseDAO courseDAO) {
		this.studentCourseDAO = studentCourseDAO;
		this.studentDAO = studentDAO;
		this.courseDAO = courseDAO;
	}
    
    @Override 
    public Integer addStudentToCourse(Integer studentId, Integer courseId) 
            throws ServiceException {
        try {
            StudentCourseEntity studentCourseEntity = studentCourseDAO.read(studentId, courseId);
            
            if (studentCourseEntity != null) {
                return BAD_STATUS;
            } else {
                StudentEntity student = studentDAO.getById(studentId);
                CourseEntity course = courseDAO.getCourseById(courseId);
                List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
                studentCourseEntityList.add(new StudentCourseEntity(student.getStudentId(), 
                                                                    student.getGroupId(), 
                                                                    student.getFirstName(),
                                                                    student.getLastName(),
                                                                    course.getCourseId(), 
                                                                    course.getCourseName(), 
                                                                    course.getCourseDescription()));
                studentCourseDAO.insert(studentCourseEntityList);
                return NORMAL_STATUS;
            }
        } catch (DAOException e) {
        	LOGGER.error(ERROR_ADD_STUDENT, e);
            throw new ServiceException (ERROR_ADD_STUDENT, e);
        }
    }
    
    @Override
    public List<StudentDto> getStudentsOfCourse(Integer courseID) throws ServiceException {
        try {
            return studentCourseDAO.readStudentsOfCourse(courseID)
            					   .stream()
            					   .map((entity) -> new StudentDto(entity.getStudentId(), 
            							   								 entity.getGroupId(), 
            							   								 entity.getFirstName(), 
            							   								 entity.getLastName(), 
            							   								 entity.getCourseId(), 
            							   								 entity.getCourseName(), 
            							   								 entity.getCourseDescription()))
            					   .collect(Collectors.toList());
        } catch (DAOException e) {
        	LOGGER.error(ERROR_GET_STUDENTS_OF_COURSE, e);
            throw new ServiceException(ERROR_GET_STUDENTS_OF_COURSE, e);
        }
    }
}
