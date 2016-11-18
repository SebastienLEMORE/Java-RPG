import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class ImagePanneau extends JPanel{
	Image image=null;
	
	public ImagePanneau(){
		super();
	}
	public ImagePanneau(Image image){
		super();
		this.image=image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	public void paintComponent(Graphics g){
		g.drawImage(image, 0, 0, null);
	}
}
