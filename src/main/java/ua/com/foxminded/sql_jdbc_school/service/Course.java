package ua.com.foxminded.sql_jdbc_school.service;

public interface Course<T> {
    
    public T createCourses() throws ServiceException;
    public T getAllCourses() throws ServiceException;
}
