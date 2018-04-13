package pl.sosk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import requestResults.Course;
import requestResults.CoursesList;

@RestController
@RequestMapping("/teacher/courses")
public class TeacherCoursesController {

	String result = null;
	List<Course> courseTempList = new ArrayList<Course>();
	String sql;
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody CoursesList getCourses(@RequestParam(value="userId", required=true, defaultValue="1") int userId) {
    	try{
  	      sql = "SELECT zajecia_id FROM Zajecia WHERE pracownicy_id = '" + userId + "'";
  	      ResultSet rs = DatabaseManager.getDbCon().query(sql);
  	      
  	      while(rs.next()){
  	    	sql = "SELECT DISTINCT Kursy.kursy_id, Kursy.dataRozpoczecia, Kursy.dataZakonczenia, Kursy.nazwa FROM KursyZajecia INNER JOIN Kursy ON KursyZajecia.kursy_id=Kursy.kursy_id WHERE zajecia_id = '" + rs.getInt("zajecia_id") + "'";
  	    	ResultSet rs2 = DatabaseManager.getDbCon().query(sql);
  	    	while(rs2.next()){
  	    	  	String status;
					Date dateNow = new Date();
					Date courseStartDate = rs2.getDate("Kursy.dataRozpoczecia");
					Date courseEndDate = rs2.getDate("Kursy.dataZakonczenia");
					if(dateNow.after(courseEndDate)){
						status = "zakończony";
					}else if(dateNow.after(courseStartDate) && dateNow.before(courseEndDate)){
						status = "w trakcie";
					}else{
						status = "nierozpoczęty";
					}
				
				Course course = new Course(rs2.getInt("Kursy.kursy_id"),rs2.getString("Kursy.nazwa"),status);
				System.out.println(status);
				courseTempList.add(course);
  	    	}
  	      }
  	   }catch(SQLException se){
  	      se.printStackTrace();
  	   }
    	
    	CoursesList cList = new CoursesList(courseTempList);
    	
    	return cList;
    }

}