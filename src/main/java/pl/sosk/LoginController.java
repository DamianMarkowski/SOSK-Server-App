package pl.sosk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import requestResults.LoginResult;
import java.sql.*;

@RestController
@RequestMapping("/login")
public class LoginController {

	String result = null;
	String userType = null;
	String teacherType = null;
	int userId = 0;
	
	private static final String SUCCESS_CODE = "SUCCESS"; 
	private static final String FAILED_CODE = "FAILED"; 
	private static final String STUDENT_USER_TYPE = "student"; 
	private static final String TEACHER_USER_TYPE = "teacher"; 
	private static final String LECTURER_USER_TYPE = "lecturer";
	private static final String INSTRUCTOR_USER_TYPE = "instructor"; 
	private static final String NONE_USER_TYPE = "none"; 
	
	@RequestMapping(method=RequestMethod.GET)
    public @ResponseBody LoginResult logIn(@RequestParam(value="username", required=true, defaultValue="") String username,@RequestParam(value="password", required=true, defaultValue="") String password) {
		try{
	 			String query = createStudentQuery(username,password);
	 			System.out.println(query);
	 			ResultSet resultStudent = DatabaseManager.getDbCon().query(query);
	      
	 			if (!resultStudent.next()) {
	 				ResultSet resultTeacher = DatabaseManager.getDbCon().query(createTeacherQuery(username, password));
	 				
	 				if(!resultTeacher.next()){
	 					populateResponseData(FAILED_CODE,NONE_USER_TYPE);
	 					System.out.println("no user found");
	 				}else{
	 					do {
	 						ResultSet resultTeacherType = DatabaseManager.getDbCon().query(createTeacherTypeQuery(resultTeacher.getInt(1)));
	 						
	 						if(!resultTeacherType.next()){
	 							populateResponseData(resultTeacher.getInt(1), SUCCESS_CODE, TEACHER_USER_TYPE);
 			 					System.out.println("teacher found");
	 						}else{
	 							do {
	 								switch(resultTeacherType.getString(1)){
	 									case "wykładowca":
	 										teacherType = LECTURER_USER_TYPE;
	 										break;
	 									case "instruktor":
	 										teacherType = INSTRUCTOR_USER_TYPE;
	 										break;
	 									default:
	 										break;
	 								}
	 								
	 								populateResponseData(resultTeacher.getInt(1), SUCCESS_CODE, teacherType);
	 			 					System.out.println("teacher found");
	 							}while (resultTeacherType.next());
	 						}
	 					} while (resultTeacher.next());
	 				}
	 			}	
	 			else {
					do {
						populateResponseData(resultStudent.getInt(1),SUCCESS_CODE,STUDENT_USER_TYPE);
		 				System.out.println("student found");
					} while (resultStudent.next());
				}
	   }catch(SQLException se){
	      se.printStackTrace();
	   }
	 		
	 return new LoginResult(result,userType,userId);
	}
	
	private String createStudentQuery(String username, String password){
    	return "SELECT kursanci_id FROM Kursanci WHERE login='" + username + "' AND haslo='" + password + "'";
    }
    
    private String createTeacherQuery(String username, String password){
    	return "SELECT pracownicy_id FROM Pracownicy WHERE login='" + username + "' AND haslo='" + password + "'";
    }
    
    private String createTeacherTypeQuery(int teacherId){
    	return "SELECT Stanowiska.nazwa FROM PracownicyStanowiska INNER JOIN Stanowiska ON PracownicyStanowiska.stanowiska_id=Stanowiska.stanowiska_id WHERE PracownicyStanowiska.pracownicy_id = '" + teacherId + "' LIMIT 1";
    }
        
    private void populateResponseData(int userId, String result, String userType){
    	this.userId = userId;
    	this.result = result;
    	this.userType = userType;
    }
    
    private void populateResponseData(String result, String userType){
    	this.result = result;
    	this.userType = userType;
    }
}