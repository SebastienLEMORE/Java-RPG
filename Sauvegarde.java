import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Sauvegarde {
	public Sauvegarde(){
		
	}
	
	public void sauvegarde(Personnage j){
		ObjectOutputStream oos=null;
		try{
			final FileOutputStream fichier=new FileOutputStream("personnage1.ser");
			oos=new ObjectOutputStream(fichier);
			oos.writeObject(j);
			oos.flush();
		}catch(final IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(oos!=null){
					oos.flush();
					oos.close();
				}
			}catch(final IOException ex){
				ex.printStackTrace();
			}
		}
	}
	public Personnage charger(){
		ObjectInputStream ois=null;
		try {
			final FileInputStream fichier = new FileInputStream("personnage1.ser");
			ois = new ObjectInputStream(fichier);
			Personnage j= (Personnage) ois.readObject();
			return j;
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
