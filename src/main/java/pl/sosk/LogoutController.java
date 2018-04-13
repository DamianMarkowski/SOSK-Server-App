package pl.sosk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

	private static final String LOGGED_OUT_CODE = "LOGGED OUT"; 
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Message logOut() {
    	return new Message(1, LOGGED_OUT_CODE);
    }

}