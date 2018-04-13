package pl.sosk;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import requestResults.Course;
import requestResults.CoursesList;
import requestResults.EducationalMaterial;
import requestResults.EducationalMaterialsList;

@RestController
@RequestMapping("/student/educationalMaterials")
public class StudentEducationalMaterialsController {

	List<EducationalMaterial> materialsTempList = new ArrayList<EducationalMaterial>();
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody EducationalMaterialsList getMaterials(@RequestParam(value="courseId", required=true, defaultValue="") int courseId) {
    	try{ 
    	    ResultSet resultMaterials = DatabaseManager.getDbCon().query(createMaterialsQuery(courseId));
    	    while(resultMaterials.next()){
    	    		ResultSet resultMaterialNames = DatabaseManager.getDbCon().query(createMaterialNamesQuery(resultMaterials.getInt("KategorieMaterialy.materialy_id")));
    	    		
    	    		while(resultMaterialNames.next()){
    	    			EducationalMaterial material = new EducationalMaterial(resultMaterialNames.getString("nazwa"));
        	    		materialsTempList.add(material);
    	    		}
    	     }
    	   }catch(SQLException se){
    	      se.printStackTrace();
    	   }
      	
      	EducationalMaterialsList cList = new EducationalMaterialsList(materialsTempList);
      	
      	return cList;
    }
    
    private String createMaterialsQuery(int courseId){
    	return "SELECT KategorieMaterialy.materialy_id FROM Kursy INNER JOIN KategorieMaterialy ON Kursy.kategoriePrawaJazdy_id=KategorieMaterialy.kategoriePrawaJazdy_id WHERE Kursy.kursy_id = '" + courseId + "'";
    }
    
    private String createMaterialNamesQuery(int materialId){
    	return "SELECT nazwa FROM Materialy WHERE materialy_id = '" + materialId + "'";
    }

}
