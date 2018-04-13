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
import requestResults.PracticalLessonsTipsList;
import requestResults.PracticalTip;

@RestController
@RequestMapping("/student/practicalLessonsTips")
public class StudentPracticalLessonsTipsController {
	
	List<PracticalTip> tipsTempList = new ArrayList<PracticalTip>();
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody PracticalLessonsTipsList getTipsList(@RequestParam(value="courseId", required=true, defaultValue="") int courseId) {
    	try{ 
    	     ResultSet resultTips = DatabaseManager.getDbCon().query(createPracticalTipsQuery(courseId));
    	     while(resultTips.next()){
    	    	 
    	         PracticalTip tip = new PracticalTip(resultTips.getString("Zajecia.dataRozpoczecia").toString(),resultTips.getString("Zajecia.uwagi"));
    	         tipsTempList.add(tip);
    	      	  }
    	   }catch(SQLException se){
    	      se.printStackTrace();
    	   }
      	
      	PracticalLessonsTipsList cList = new PracticalLessonsTipsList(tipsTempList);
      	
      	return cList;
    }
    
    private String createPracticalTipsQuery(int courseId){
    	return "SELECT Zajecia.dataRozpoczecia, Zajecia.uwagi FROM KursyZajecia INNER JOIN Zajecia ON KursyZajecia.zajecia_id=Zajecia.zajecia_id WHERE KursyZajecia.kursy_id = '" + courseId + "' AND Zajecia.rodzajZajec_id = 18";
    }

}
