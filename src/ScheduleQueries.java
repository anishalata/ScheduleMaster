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
import java.sql.Timestamp;
/**
 *
 * @author acv
 */
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSchedule;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduleByCourse;
    private static PreparedStatement getScheduleByStudentCount;
    private static ResultSet resultSet;
    private static PreparedStatement deleteScheduleEntry;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    
    public static void addSchedule(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.schedule (semester, courseCode,studentID, status, timeStamp) values (?, ?, ?, ?, ?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getCourseCode());
            addSchedule.setString(3, entry.getStudentID());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntry = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentID = ?" );
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                String coursecode= resultSet.getString(2);
                String status= resultSet.getString(4);
                String seme= resultSet.getString(1);
                String studentid = resultSet.getString(3);
                Timestamp timestamp = resultSet.getTimestamp(5);
                ScheduleEntry sc = new ScheduleEntry(seme, coursecode, studentid, status, timestamp);
                scheduleEntry.add(sc);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleEntry;
        
    }
    public static int getScheduleByStudentCount(String semester, String studentID) {
    int studentCount = 0;
    Connection connection = DBConnection.getConnection();
        
        try {
            getScheduleByStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduleByStudentCount.setString(1, semester);
            getScheduleByStudentCount.setString(2, studentID);
            resultSet = getScheduleByStudentCount.executeQuery();
            while(resultSet.next())
            {
                studentCount = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentCount;
            }
    
    

public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<>();
        
        try {
            
            getWaitlistedStudentsByClass = connection.prepareStatement("SELECT semester, courseCode, studentID, status, timestamp from app.schedule where semester = ? and courseCode = ? and status = 'Waitlisted' order by timestamp");
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);
            resultSet = getWaitlistedStudentsByClass.executeQuery();

            while(resultSet.next()) {
                String scSemester = resultSet.getString(1);
                String scCourseCode = resultSet.getString(2);
                String studentID = resultSet.getString(3);
                String status = resultSet.getString(4);
                Timestamp timestamp = resultSet.getTimestamp(5);

                ScheduleEntry sc = new ScheduleEntry(scSemester, scCourseCode, studentID, status, timestamp);
                waitlistedStudents.add(sc);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return waitlistedStudents;
}
public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
    connection = DBConnection.getConnection();

    try {
        deleteScheduleEntry = connection.prepareStatement("DELETE FROM app.schedule WHERE semester = ? AND studentID = ? AND courseCode = ?");
        deleteScheduleEntry.setString(1, semester);
        deleteScheduleEntry.setString(2, studentID);
        deleteScheduleEntry.setString(3, courseCode);

        deleteScheduleEntry.executeUpdate();
        System.out.println("Schedule entry dropped for student " + studentID + " in course " + courseCode + " for semester " + semester);
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();

    }
}
public static void dropScheduleByCourse(String semester, String courseCode) {
    connection = DBConnection.getConnection();

    try {
        dropScheduleByCourse = connection.prepareStatement("DELETE FROM app.schedule WHERE semester = ? AND courseCode = ?");
        dropScheduleByCourse.setString(1, semester);
        dropScheduleByCourse.setString(2, courseCode);

        dropScheduleByCourse.executeUpdate();
        System.out.println("Schedule entries dropped for course " + courseCode + " for semester " + semester);
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
}
public static void updateScheduleEntry(ScheduleEntry updatedEntry) {
    connection = DBConnection.getConnection();

    try {
        updateScheduleEntry = connection.prepareStatement("UPDATE app.schedule SET status = 'Scheduled' WHERE semester = ? AND courseCode = ? AND studentID = ?");
        //updateScheduleEntry.setString(1, updatedEntry.getStatus());
        updateScheduleEntry.setString(1, updatedEntry.getSemester());
        updateScheduleEntry.setString(2, updatedEntry.getCourseCode());
        updateScheduleEntry.setString(3, updatedEntry.getStudentID());

        updateScheduleEntry.executeUpdate();
        System.out.println("Schedule entry updated for student " + updatedEntry.getStudentID() + " in course " + updatedEntry.getCourseCode() + " for semester " + updatedEntry.getSemester());
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
}


}



