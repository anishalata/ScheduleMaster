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
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudentList;
    private static ResultSet resultSet;
    private static PreparedStatement dropStudent;
    
    public static void addStudent( StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student ( firstName, lastName, studentID) values (?,?,?)");
            addStudent.setString(1, student.getFirstName());
            addStudent.setString(2, student.getLastName());
            addStudent.setString(3, student.getStudentID());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            getStudentList = connection.prepareStatement("select studentID, FirstName, LastName from app.student order by LastName, FirstName");
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry entry = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                student.add(entry);
                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
    public static StudentEntry getStudent(String studentID) {
    connection = DBConnection.getConnection();
    
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            getStudentList = connection.prepareStatement("select studentID, FirstName, LastName from app.student where studentID = (?)");
            getStudentList.setString(1, studentID);
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry entry = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                student.add(entry);
                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student.get(0);
        
    }
    
    public static void dropStudent(String studentID) {
    connection = DBConnection.getConnection();

    try {
        dropStudent = connection.prepareStatement("DELETE FROM app.student WHERE studentID = ?");
        dropStudent.setString(1, studentID);
        dropStudent.executeUpdate();

        System.out.println("Student with ID " + studentID + " has been dropped.");
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
}

}
    

