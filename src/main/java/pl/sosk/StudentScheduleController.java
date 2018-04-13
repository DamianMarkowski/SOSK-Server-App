package pl.sosk;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import requestResults.Class;
import requestResults.ClassesList;

@RestController
@RequestMapping("/student/schedule")
public class StudentScheduleController {

    List<Class> classesTempList = new ArrayList<Class>();
    String result = null;
	String classType;
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody ClassesList getStudentSchedule(@RequestParam(value="courseId", required=true, defaultValue="") int courseId) {
    	try{
    	      ResultSet resultClasses = DatabaseManager.getDbCon().query(createClassesQuery(courseId)); 
    	      while(resultClasses.next()){
    	    	  int classTypeId = resultClasses.getInt("Zajecia.rodzajZajec_id");
        	      ResultSet resultClassesType = DatabaseManager.getDbCon().query(createClassesTypeQuery(classTypeId));
        	      while(resultClassesType.next()){
        	    	  classType = resultClassesType.getString("rodzajZajec");
        	      }
    	    	  Class class1 = new Class(resultClasses.getDate("Zajecia.dataRozpoczecia").toString(),classType);
    	    	  classesTempList.add(class1);
    	      }
    	   }catch(SQLException se){
    	      se.printStackTrace();
    	   }
    	
    	ClassesList cList = new ClassesList(classesTempList);
    	
    	return cList;
    }

    private String createClassesQuery(int courseId){
    	return "SELECT Zajecia.dataRozpoczecia, Zajecia.rodzajZajec_id FROM KursyZajecia INNER JOIN Zajecia ON KursyZajecia.zajecia_id=Zajecia.zajecia_id WHERE KursyZajecia.kursy_id = '" + courseId + "'";
    } 
    
    private String createClassesTypeQuery(int classTypeId){
    	return "SELECT rodzajZajec FROM RodzajZajec WHERE rodzajZajec_id = '" + classTypeId + "'";
    }
}
