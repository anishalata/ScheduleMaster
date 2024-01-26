/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getClassSeats;
    private static ResultSet resultSet;
    private static PreparedStatement dropClass;
    
    public static void addClass(ClassEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.class (semester, CourseCode,seats) values (?, ?, ?)");
            addClass.setString(1, entry.getSemester());
            addClass.setString(2, entry.getCourseCode());
            addClass.setInt(3, entry.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<String> getAllClassCodes(String semester)
    {
     
        
        connection = DBConnection.getConnection();
        ArrayList<String> classes = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select courseCode from app.class where semester =  ?");
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                classes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classes;
        
    }
    public static int getClassSeats(String semester, String courseCode) {
    int seatCount = 0;
    Connection connection = DBConnection.getConnection();
        
        try {
            getClassSeats = connection.prepareStatement("select seats from app.class where semester =? and courseCode= ?");
            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);
            resultSet = getClassSeats.executeQuery();
            while(resultSet.next())
            {
                seatCount = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seatCount;
            }
        
   public static void dropClass(String semester, String courseCode) {
    connection = DBConnection.getConnection();
    try {
        dropClass = connection.prepareStatement("DELETE FROM app.class WHERE semester = ? AND CourseCode = ?");
        dropClass.setString(1, semester);
        dropClass.setString(2, courseCode);
        dropClass.executeUpdate();
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
}
}


