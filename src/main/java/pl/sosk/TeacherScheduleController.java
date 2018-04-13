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
@RequestMapping("/teacher/schedule")
public class TeacherScheduleController {
	
	String result = null;
	String sql;
	List<Class> classesTempList = new ArrayList<Class>();
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody ClassesList getStudentSchedule(@RequestParam(value="userId", required=true, defaultValue="32") int userId) {
    	
    	try{ 
  	      ResultSet rs = DatabaseManager.getDbCon().query(createClassesQuery(userId));
  	      while(rs.next()){
  	    	  	ResultSet rs2 = DatabaseManager.getDbCon().query(createClassesSubjectQuery(rs.getInt("zajecia_id")));
  	    	  	while(rs2.next()){
  	    	  		Class class1 = new Class(rs2.getString("TematZajec.temat"),rs.getDate("dataRozpoczecia").toString());
  	    	  		classesTempList.add(class1);	
  	    	  	}      
  	      }
  	   }catch(SQLException se){
  	      se.printStackTrace();
  	   }
    	
    	ClassesList cList = new ClassesList(classesTempList);
    	
    	return cList;
    }
    
    private String createClassesQuery(int userId){
    	return "SELECT zajecia_id, dataRozpoczecia FROM Zajecia WHERE pracownicy_id = '" + userId + "'";
    }

    private String createClassesSubjectQuery(int classId){
    	return "SELECT TematZajec.temat FROM ZajeciaTematZajec INNER JOIN TematZajec ON ZajeciaTematZajec.tematZajec_id = TematZajec.tematZajec_id WHERE ZajeciaTematZajec.zajecia_id = '" + classId + "'";
    }
}
