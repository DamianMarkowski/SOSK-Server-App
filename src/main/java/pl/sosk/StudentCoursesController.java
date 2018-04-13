package pl.sosk;

import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import requestResults.Course;
import requestResults.CoursesList;

@RestController
@RequestMapping("/student/courses")
public class StudentCoursesController {

	private static final String COURSE_FINISHED = "zakończony";
	private static final String COURSE_IN_PROGRESS = "w trakcie";
	private static final String COURSE_NOT_STARTED = "nierozpoczęty"; 
	
    String result = null;
    String status;
	List<Course> courseTempList = new ArrayList<Course>();
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody CoursesList getCourses(@RequestParam(value="userId", required=true, defaultValue="") int userId) {
    	try{ 
  	      ResultSet resultCourses = DatabaseManager.getDbCon().query(createCoursesQuery(userId));
  	      while(resultCourses.next()){
			Date dateNow = new Date();
			Date courseStartDate = resultCourses.getDate("Kursy.dataRozpoczecia");
			Date courseEndDate = resultCourses.getDate("Kursy.dataZakonczenia");
			if(dateNow.after(courseEndDate)){
				setStatus(COURSE_FINISHED);
			}else if(dateNow.after(courseStartDate) && dateNow.before(courseEndDate)){
				setStatus(COURSE_IN_PROGRESS);
			}else{
				setStatus(COURSE_NOT_STARTED);
			}
			
			Course course = new Course(resultCourses.getInt("Kursy.kursy_id"),resultCourses.getString("Kursy.nazwa"),status);
    		System.out.println(status);
			courseTempList.add(course);
  	      }
  	   }catch(SQLException se){
  	      se.printStackTrace();
  	   }
    	
    	CoursesList cList = new CoursesList(courseTempList);
    	
    	return cList;
    }
    
    private String createCoursesQuery(int userId){
    	return "SELECT DISTINCT Kursy.kursy_id, Kursy.nazwa, Kursy.dataRozpoczecia, Kursy.dataZakonczenia FROM KursanciKursy INNER JOIN Kursy ON KursanciKursy.kursy_id=Kursy.kursy_id WHERE KursanciKursy.kursanci_id = '" + userId + "'";
    }
    
    private void setStatus(String value){
    	this.status = value;
    }

}