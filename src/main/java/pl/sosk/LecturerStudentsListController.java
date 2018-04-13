package pl.sosk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import requestResults.Course;
import requestResults.CoursesList;
import requestResults.LoginResult;
import requestResults.Student;
import requestResults.StudentsList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teacher/lecturer/listOfStudents")
public class LecturerStudentsListController {

	List<Student> studentsTempList = new ArrayList<Student>();
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody StudentsList logIn(@RequestParam(value="courseId", required=true, defaultValue="") int courseId) {
    	 
   	 		try{
   	 			ResultSet resultStudents = DatabaseManager.getDbCon().query(createStudentsQuery(courseId));
    	      
   	 			while(resultStudents.next()){
   	 				Student student = new Student(resultStudents.getInt(0),resultStudents.getString(1),resultStudents.getString(1));
   	 				studentsTempList.add(student);
   	 			}
    	      
    	      
    	   }catch(SQLException se){
    	      se.printStackTrace();
    	   }
   	 			
   	 	StudentsList cList = new StudentsList(studentsTempList);
    	
    	return cList;
    }
    
    private String createStudentsQuery(int courseId){
    	return "SELECT Kursanci.kursanci_id, Kursanci.imie, Kursanci.nazwisko FROM KursanciKursy INNER JOIN Kursanci ON KursanciKursy.kursanci_id=Kursanci.kursanci_id WHERE KursanciKursy.kursy_id = '" + courseId + "'";
    }
}