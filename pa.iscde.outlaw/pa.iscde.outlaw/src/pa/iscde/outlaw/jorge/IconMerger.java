package pa.iscde.outlaw.jorge;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class IconMerger {

	public java.awt.Image merge(String[] icons){
		if(icons.length>1){
			try {
				BufferedImage imagebckg = ImageIO.read(new File(icons[0]));
				for(int i=1;i<icons.length-1;i++){
					BufferedImage newoverlay = ImageIO.read(new File(icons[i]));
					int w = Math.max(imagebckg.getWidth(), newoverlay.getWidth());
					int h = Math.max(imagebckg.getHeight(), newoverlay.getHeight());
					imagebckg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				}
				return bufferedImagetoImage(imagebckg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public java.awt.Image bufferedImagetoImage(BufferedImage bi) {
	    return Toolkit.getDefaultToolkit().createImage(bi.getSource());
	}
	
}
