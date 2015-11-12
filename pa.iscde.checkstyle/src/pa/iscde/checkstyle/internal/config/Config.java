package pa.iscde.checkstyle.internal.config;

import java.util.List;

public interface Config {
	public String getName();
	
	public List<ConfigProperty> getProperties();
}
