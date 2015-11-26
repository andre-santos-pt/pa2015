package pa.iscde.outlaw.ivo;

import java.util.Map;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import pa.iscde.outlaw.jorge.OutlineClass;
import pa.iscde.outlaw.jorge.OutlineField;
import pa.iscde.outlaw.jorge.OutlineMethod;

public class FileTreeLabelProvider implements ILabelProvider {

	private Map<String, Image> imageMap;

	public FileTreeLabelProvider(Map<String, Image> imageMap) {
		this.imageMap=imageMap;
	}

	@Override
	public Image getImage(Object arg0) {//16x16
		if (arg0 instanceof OutlineField) {
			return imageMap.get(((OutlineField) arg0).getImg());
		} else if (arg0 instanceof OutlineMethod) {
		       return imageMap.get(((OutlineMethod) arg0).getImg());
		} else if(arg0 instanceof OutlineClass){
		       return imageMap.get(((OutlineClass) arg0).getImg());
		} else if (arg0 instanceof String){
				return imageMap.get("package.gif");
		}
		
		return null;
	}

	@Override
	public String getText(Object arg0) {
		if (arg0 instanceof OutlineField) {
		       return ((OutlineField) arg0).toString();
		} else if (arg0 instanceof OutlineMethod) {
		       return ((OutlineMethod) arg0).toString();
		} else if(arg0 instanceof OutlineClass){
		       return ((OutlineClass) arg0).toString();
		} else if (arg0 instanceof String){
				return (String) arg0;
		}
		return "";
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
	}

}
