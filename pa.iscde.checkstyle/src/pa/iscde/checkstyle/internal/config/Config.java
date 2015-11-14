package pa.iscde.checkstyle.internal.config;

import java.util.List;

import pa.iscde.checkstyle.model.SeverityType;

public interface Config {
	public String getName();
	
	public List<ConfigProperty> getProperties();
	
	public String getMessage();
	
	public SeverityType getSeverity();
}
