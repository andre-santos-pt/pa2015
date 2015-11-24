package pa.iscde.checkstyle.internal;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.service.CheckStyleService;

public class CheckStyleServiceImpl implements CheckStyleService {

	@Override
	public List<String> getChecks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enableCheck(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableCheck(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Violation> getViolations(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Violation> getViolations(File file, Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeSeverity(String type, SeverityType severity) {
		// TODO Auto-generated method stub
		
	}
}
