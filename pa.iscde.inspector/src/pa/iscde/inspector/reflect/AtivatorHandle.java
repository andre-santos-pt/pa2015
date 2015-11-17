package pa.iscde.inspector.reflect;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;

public class AtivatorHandle {
	
	private List<BundleActivator> ativators;
	
	public AtivatorHandle(){
		ativators = new ArrayList<BundleActivator>();
		
	}
	
	public void addAtivator(BundleActivator ativator){
		ativators.add(ativator);
	}
	
	public void removeAtivator(BundleActivator ativator){
		ativators.remove(ativator);
	}
	
	
}
