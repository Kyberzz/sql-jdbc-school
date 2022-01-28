package ua.com.foxminded.university.dao.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import ua.com.foxminded.university.dao.DAOException;
import ua.com.foxminded.university.dao.DAOException.DatabaseConnectionFail;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.dto.StudentData;

public class UniversityStudentDAO implements StudentDAO {
    private static final String SQL_INSERT = "insert into department.students (first_name, last_name) values (?, ?)";
    
    public int insertStudents(List<StudentData> students) throws DAOException.StudentInsertionFail  {
        try(Connection connection = UniversityDAOFactory.creatConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT);) {
            
            connection.setAutoCommit(false);
            Savepoint save1 = connection.setSavepoint();
            
            try {
                int status = 0;
                
                for (StudentData student : students) {
                    prepareStatement.setString(1, student.getFirstName());
                    prepareStatement.setString(2, student.getLastName());
                    status = prepareStatement.executeUpdate();
                }
                
                connection.commit();
                return status;
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback(save1);
                    } catch (SQLException exc) {
                        throw new SQLException(exc);
                    }
                }
                
                throw new SQLException(e);
            }
        } catch (DatabaseConnectionFail | SQLException e) {
            throw new DAOException.StudentInsertionFail(e);
        } 
    }
}
