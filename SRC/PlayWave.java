import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;


public class PlayWave extends Thread {

	private String filename;
	boolean lire;
	public static int n;
	private Position curPosition;
	SourceDataLine auline;
	String[] songs; //les noms des musiques
	private final int EXTERNAL_BUFFER_SIZE = 5000; // mis en cache
	enum Position {LEFT, RIGHT, NORMAL};
	/**
	 * @param wavfile le nom du fichier son qui doit etre au format wav
	 */
	public PlayWave(boolean u) {
		lire = u;
		curPosition = Position.NORMAL;
		songs = Finals.SONGS.clone();
	}
	public void run(){
		this.running();
	}
	public void running() {

		while(lire){   	
			File soundFile = new File(songs[n%(songs.length)]);
			if (!soundFile.exists()) {
				System.err.println("Wave file not found: " + songs[n%(songs.length)]);
				return;
			}
			//on prend les infos sur le fichier wav
			AudioInputStream audioInputStream = null;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}

			AudioFormat format = audioInputStream.getFormat();
			auline = null;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

			try {
				auline = (SourceDataLine) AudioSystem.getLine(info);
				auline.open(format);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				return;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			if (auline.isControlSupported(FloatControl.Type.PAN)) {
				FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
				if (curPosition == Position.RIGHT) {
					pan.setValue(1.0f);
				} else if (curPosition == Position.LEFT) {
					pan.setValue(-1.0f);
				}
			}
			//lecture
			auline.start();
			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

			try {
				while (nBytesRead != -1 && lire) {
					nBytesRead = audioInputStream.read(abData, 0, abData.length);
					if (nBytesRead >= 0) {
						auline.write(abData, 0, nBytesRead);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			auline.drain();
			auline.close();
			n=+1;      
		}
	}
	public void invertLire(){
		lire=!lire;
	}
	public boolean getLire(){
		return lire;
	}

}