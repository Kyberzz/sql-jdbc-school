package ua.com.foxminded.sql_jdbc_school.dao.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.sql_jdbc_school.dao.ConnectionDAOFactory;
import ua.com.foxminded.sql_jdbc_school.dao.DAOException;
import ua.com.foxminded.sql_jdbc_school.dao.DAOPropertiesCache;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDAO;
import ua.com.foxminded.sql_jdbc_school.dao.entities.CourseEntity;
import ua.com.foxminded.sql_jdbc_school.dao.entities.StudentEntity;

public class UniversityStudentDAO extends UniversityGenericDAO<StudentEntity> implements StudentDAO {
    
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String GET_STUDENTS_OF_COURS_BY_ID = "getStudentsOfCourseByID";
	private static final String ERROR_GET_STUDENTS_OF_COURS_BY_ID = "Getting students of the course by "
																  + "Id is failed.";
	private static final String GET_STUDENT_OF_COURSE_BY_ID = "getStudentOfCourseById";
	private static final String ERROR_GET_STUDENT_OF_COURSE_BY_ID = "Getting the student from "
																  + "the course by ID is failed.";
	private static final String ERROR_GET_STUDENTS_HAVING_COURSE = "Receiving students have been added "
																 + "to courses is failed.";
	private static final String GET_STUDENTS_HAVING_COURSE = "getStudentsHavingCouse";
	private static final String ERROR_ADD_STUDENT_TO_COURSE = "The student has not been added to the course";
	private static final String ADD_STUDENT_TO_COURSE = "addStudentToCourse";
	private static final String SQL_QUERIES_FILENAME = "student-queries.properties";
	private static final String SELECT_STUDENTS_WITH_GROUP = "selectStudentsWithGroup";
	private static final String SELECT_STUDENT = "selectStudent";
	private static final String DELETE_STUDENT = "deleteStudent";
	private static final String INSERT_STUDENTS = "insertStudent";
	private static final String SELECT_ALL = "selectAll";
    private static final String UPDATE = "update";
    private static final String STUDENT_ID = "student_id";
    private static final String GROUP_ID = "group_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ERROR_GET_ALL = "The getting of all the students failed.";
    private static final String ERROR_INSERT = "The inserting of the students failed.";
    private static final String ERROR_UDATE = "The updating of the students infurmation failed.";
    private static final String ERROR_DELETE = "The deletion of the student data failed.";
    private static final String ERROR_GET_STUDENT = "Getting the student data failed.";
    private static final String ERROR_GET_STUDENTS_WITHOUT_GROUP = "Getting the student data, that have no "
                                                                 + "group ID failed.";
    
    public UniversityStudentDAO(ConnectionDAOFactory universityConnectionDAOFactory) {
		super(universityConnectionDAOFactory);
	}
    
    public int someMethod() throws SQLException {
    	UniversityConnectionPool pool = UniversityConnectionPool.create("url", "user", "pass");
    	Connection con = pool.getConnection();
    }
    
    /*
    @Override
    public List<StudentEntity> getStudensOfCourseById(Integer courseId) throws DAOException {
    	ResultSet resultSet = null;
    	List<StudentEntity> studentsOfcourse = new ArrayList<>();
    	
    	try (Connection con = universityConnectionDAOFactory.createConnection();
    	     PreparedStatement prStatement = con.prepareStatement(DAOPropertiesCache
    	    		 .getInstance(SQL_QUERIES_FILENAME)
    	    		 .getProperty(GET_STUDENTS_OF_COURS_BY_ID));) {
    		
    		resultSet = prStatement.executeQuery();
    		
    		while (resultSet.next()) {
    			studentsOfcourse.add(new StudentEntity(resultSet.getInt(STUDENT_ID), 
    												   resultSet.getInt(GROUP_ID),
    												   resultSet.getString(FIRST_NAME),
    												   resultSet.getString(LAST_NAME)));
    		}
    		
    		return studentsOfcourse;
    	} catch (DAOException | SQLException e) {
    		LOGGER.error(ERROR_GET_STUDENTS_OF_COURS_BY_ID, e);
    		throw new DAOException(ERROR_GET_STUDENTS_OF_COURS_BY_ID, e);
    	}
    }
    */
    
    @Override
    public StudentEntity getStudentOfCourseById(Integer studentId, 
    											Integer courseId) throws DAOException {
    	ResultSet resultSet = null;
    	StudentEntity student = null;
    	
    	try (Connection con = universityConnectionDAOFactory.createConnection();
    		 PreparedStatement prStatement = con.prepareStatement(DAOPropertiesCache
    				 .getInstance(SQL_QUERIES_FILENAME)
    				 .getProperty(GET_STUDENT_OF_COURSE_BY_ID))) {
    		prStatement.setInt(1, studentId);
    		prStatement.setInt(2, courseId);
    		resultSet = prStatement.executeQuery();
    		
    		while (resultSet.next()) {
    			student = new StudentEntity(resultSet.getInt(STUDENT_ID),
    										resultSet.getInt(GROUP_ID),
    										resultSet.getString(FIRST_NAME),
    										resultSet.getString(LAST_NAME));
    		}
    		
    		return student;
    	} catch (DAOException | SQLException e) {
    		LOGGER.error(ERROR_GET_STUDENT_OF_COURSE_BY_ID, e);
    		throw new DAOException(ERROR_GET_STUDENT_OF_COURSE_BY_ID, e);
    	}
    }
    
    @Override
    public List<StudentEntity> getAllStudentsHavingCouse() throws DAOException {
        try (Connection con = universityConnectionDAOFactory.createConnection();
             PreparedStatement statement = con.prepareStatement(DAOPropertiesCache
            		 .getInstance(SQL_QUERIES_FILENAME)
            		 .getProperty(GET_STUDENTS_HAVING_COURSE));
             ResultSet resultSet = statement.executeQuery();) {
            
            List<StudentEntity> studentsHavingCourse = new ArrayList<>();
            
            while (resultSet.next()) {
                studentsHavingCourse.add(new StudentEntity((Integer) resultSet.getObject(STUDENT_ID),
                                                           (Integer) resultSet.getObject(GROUP_ID),
                                                           resultSet.getString(FIRST_NAME),
                                                           resultSet.getString(LAST_NAME)));
            }
            
            return studentsHavingCourse;
        } catch (DAOException | SQLException e) {
        	LOGGER.error(ERROR_GET_STUDENTS_HAVING_COURSE, e);
            throw new DAOException(ERROR_GET_STUDENTS_HAVING_COURSE, e);
        }
    }
    
    @Override
    public int addStudentToCourse(StudentEntity student, CourseEntity course) throws DAOException {
    	try(Connection con = universityConnectionDAOFactory.createConnection();
    	    PreparedStatement prStatement = con.prepareStatement(DAOPropertiesCache
    	    		.getInstance(SQL_QUERIES_FILENAME).getProperty(ADD_STUDENT_TO_COURSE))) {
    		
    		con.setAutoCommit(false);
    		Savepoint save1 = con.setSavepoint();
    		int status = 0;
    		
    		try {
    			prStatement.setObject(1, student.getStudentId());
    			prStatement.setObject(2, student.getGroupId());
    			prStatement.setObject(3, student.getFirstName());
    			prStatement.setObject(4, student.getLastName());
    			prStatement.setObject(5, course.getCourseId());
    			prStatement.setObject(6, course.getCourseName());
    			prStatement.setObject(7, course.getCourseDescription());
    			status = prStatement.executeUpdate();
    			con.commit();
    		} catch (SQLException ex) {
    			if (con != null) {
    				try {
    					con.rollback(save1);
    				} catch (SQLException exp) {
    					throw new SQLException(exp);
    				}
    				throw new SQLException(ex);
    			}
    		}
    		return status;
    	} catch (DAOException | SQLException e) {
    		LOGGER.error(ERROR_ADD_STUDENT_TO_COURSE, e);
    		throw new DAOException(ERROR_ADD_STUDENT_TO_COURSE, e);
    	}
    }

	@Override
    public List<StudentEntity> getStudentsHavingGroupId() throws DAOException {
        try (Connection con = universityConnectionDAOFactory.createConnection();
             PreparedStatement statement = con.prepareStatement(DAOPropertiesCache
            		 .getInstance(SQL_QUERIES_FILENAME).getProperty(SELECT_STUDENTS_WITH_GROUP));
             ResultSet resultSet = statement.executeQuery();) {
            
            List<StudentEntity> studentsHavingGroupId = new ArrayList<>();
            
            while (resultSet.next()) {
                studentsHavingGroupId.add(new StudentEntity(resultSet.getInt(STUDENT_ID),
                                                            resultSet.getInt(GROUP_ID),
                                                            resultSet.getString(FIRST_NAME),
                                                            resultSet.getString(LAST_NAME)));
            }
            
            return studentsHavingGroupId;
        } catch (DAOException | SQLException e) {
        	LOGGER.error(ERROR_GET_STUDENTS_WITHOUT_GROUP, e);
            throw new DAOException(ERROR_GET_STUDENTS_WITHOUT_GROUP, e);
        }
    }
    
    @Override
    public StudentEntity getStudentById(int studentId) throws DAOException {
        try (Connection con = universityConnectionDAOFactory.createConnection();
             PreparedStatement statement = con.prepareStatement(DAOPropertiesCache
            		 .getInstance(SQL_QUERIES_FILENAME).getProperty(SELECT_STUDENT));) {

            StudentEntity student = null;
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                student = new StudentEntity(resultSet.getInt(STUDENT_ID), 
                                         (Integer) resultSet.getObject(GROUP_ID),
                                         resultSet.getString(FIRST_NAME), 
                                         resultSet.getString(LAST_NAME));
            }
            resultSet.close();
            return student;
        } catch (DAOException | SQLException e) {
        	LOGGER.error(ERROR_GET_STUDENT, e);
            throw new DAOException(ERROR_GET_STUDENT, e);
        }
    }
    
    @Override
    public int deleteById(int studentId) throws DAOException {
        try (Connection con = universityConnectionDAOFactory.createConnection();
             PreparedStatement statement = con.prepareStatement(DAOPropertiesCache
            		 .getInstance(SQL_QUERIES_FILENAME).getProperty(DELETE_STUDENT))) {
            
            statement.setInt(1, studentId);
            return statement.executeUpdate();
        } catch (DAOException | SQLException e) {
        	LOGGER.error(ERROR_DELETE, e);
            throw new DAOException(ERROR_DELETE, e);
        }
    }
    
    @Override
    public Integer insert(List<StudentEntity> studentEntities) throws DAOException {
        try(Connection con = universityConnectionDAOFactory.createConnection();
            PreparedStatement statement = con.prepareStatement(DAOPropertiesCache
            		.getInstance(SQL_QUERIES_FILENAME).getProperty(INSERT_STUDENTS));) {
            con.setAutoCommit(false);
            Savepoint save1 = con.setSavepoint();
            int status = 0;
            try {
                
                
                for (StudentEntity student : studentEntities) {
                    statement.setObject(1, student.getGroupId());
                    statement.setString(2, student.getFirstName());
                    statement.setString(3, student.getLastName());
                    status = statement.executeUpdate();
                }
                
                con.commit();
            } catch (SQLException ex) {
                if (con != null) {
                    try {
                        con.rollback(save1);
                    } catch (SQLException exp) {
                        throw new SQLException(exp);
                    }
                }
                
                throw new SQLException(ex);
            }
            
            return status;
        } catch (DAOException | SQLException e) {
        	LOGGER.error(ERROR_INSERT, e);
            throw new DAOException(ERROR_INSERT, e);
        } 
    }
    
    @Override
    public List<StudentEntity> getAll() throws DAOException {
        try(Connection con = universityConnectionDAOFactory.createConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(DAOPropertiesCache
            		.getInstance(SQL_QUERIES_FILENAME).getProperty(SELECT_ALL));) {
            List<StudentEntity> students = new ArrayList<>();
            
            while(resultSet.next()) {
                students.add(new StudentEntity((Integer) resultSet.getObject(STUDENT_ID),
                                            (Integer) resultSet.getObject(GROUP_ID),
                                            resultSet.getString(FIRST_NAME),
                                            resultSet.getString(LAST_NAME)));
            }
            return students;
        } catch (DAOException | SQLException e) {
        	LOGGER.error(ERROR_GET_ALL, e);
            throw new DAOException(ERROR_GET_ALL, e);
        }
    }
    
    @Override
    public int update(List<StudentEntity> students) throws DAOException {
        try(Connection con = universityConnectionDAOFactory.createConnection();
            PreparedStatement statement = con.prepareStatement(DAOPropertiesCache
            		.getInstance(SQL_QUERIES_FILENAME).getProperty(UPDATE))) {
            con.setAutoCommit(false);
            Savepoint save1 = con.setSavepoint();
            int status = 0;
            
            try {
                for (StudentEntity student : students) {
                    statement.setInt(4, student.getStudentId());
                    statement.setObject(1, student.getGroupId());
                    statement.setString(2, student.getFirstName());
                    statement.setString(3, student.getLastName());
                    status += statement.executeUpdate();
                }
                
                con.commit();
            } catch (SQLException ex) {
                if (con != null) {
                    try {
                        con.rollback(save1);
                    } catch (SQLException exp) {
                        throw new SQLException(exp);
                    }
                }
                
                throw new SQLException(ex);
            }
            
            return status;
        } catch (DAOException | SQLException e) {
        	LOGGER.error(ERROR_UDATE, e);
            throw new DAOException(ERROR_UDATE, e);
        }
    }
}
