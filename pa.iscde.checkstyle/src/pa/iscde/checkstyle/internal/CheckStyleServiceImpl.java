package pa.iscde.checkstyle.internal;

import pa.iscde.checkstyle.model.ViolationModelProvider;
import pa.iscde.checkstyle.service.CheckStyleService;

public class CheckStyleServiceImpl implements CheckStyleService {
	
	public CheckStyleServiceImpl(){
		
	}
	
	public void process(){
		ViolationModelProvider.getInstance().resetViolations();
		ViolationModelProvider.getInstance().updateViolations();
	}
}
