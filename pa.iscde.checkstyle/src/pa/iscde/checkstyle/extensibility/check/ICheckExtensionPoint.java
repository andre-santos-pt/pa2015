package pa.iscde.checkstyle.extensibility.check;

import java.util.List;

import pa.iscde.checkstyle.internal.check.Check;

public interface ICheckExtensionPoint {
	
	/**
	 * TODO
	 * @param checks
	 */
	public void addChecks(List<Check> checks);
}
