
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anishsparida
 */

    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class MultiTableQueries {
    private static Connection connection;
    private static ArrayList<ClassDescription> allClassDescriptions = new ArrayList<ClassDescription>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllClassDescriptions;
    private static ResultSet resultSet;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
   
    
    
    
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String Semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> entry = new ArrayList<ClassDescription>();
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            getAllClassDescriptions.setString(1, Semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {
                String coursecode = resultSet.getString(1);
                String description = resultSet.getString(2);
                int seats = resultSet.getInt(3);
                
                
                ClassDescription des = new ClassDescription(coursecode, description, seats);
               entry.add(des);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entry;
        
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String Semester, String courseCode) {
    connection = DBConnection.getConnection();
    ArrayList<StudentEntry> scheduledStudents = new ArrayList<>();

    try {
        getScheduledStudentsByClass = connection.prepareStatement("SELECT app.student.studentID, firstName, lastName " +"FROM app.student, app.schedule " +"WHERE semester = ? " + "AND courseCode = ? " + "and status = 'Scheduled' AND app.schedule.StudentID =  " + " app.student.studentID");
        
        getScheduledStudentsByClass.setString(1, Semester);
        getScheduledStudentsByClass.setString(2, courseCode);
        resultSet = getScheduledStudentsByClass.executeQuery();

        while (resultSet.next()) {
            String studentID = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            //String email = resultSet.getString(4);

            StudentEntry student = new StudentEntry(studentID, firstName, lastName);
            scheduledStudents.add(student);
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
    return scheduledStudents;


        
    }
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String Semester, String courseCode) {
    connection = DBConnection.getConnection();
    ArrayList<StudentEntry> waitlistedStudents = new ArrayList<>();

   try {
        getScheduledStudentsByClass = connection.prepareStatement("SELECT app.student.studentID, firstName, lastName " +"FROM app.student, app.schedule " +"WHERE semester = ? " + "AND courseCode = ? " + "and status = 'Waitlisted' AND app.schedule.StudentID =  " + " app.student.studentID");
        
        getScheduledStudentsByClass.setString(1, Semester);
        getScheduledStudentsByClass.setString(2, courseCode);
        resultSet = getScheduledStudentsByClass.executeQuery();

        while (resultSet.next()) {
            String studentID = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            //String email = resultSet.getString(4);

            StudentEntry student = new StudentEntry(studentID, firstName, lastName);
            waitlistedStudents.add(student);
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
    return waitlistedStudents;
}

}
