
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePan extends JPanel {

	private JPanel SidBand = new JPanel();
	private JPanel NbSu = new JPanel();
	private JPanel NbSo = new JPanel();
	private JPanel NbSuSelect = new JPanel();
	private JPanel NbSoSelect = new JPanel();
	private JPanel BaseSelect = new JPanel();

	ImageIcon soundOn  = new ImageIcon("IMG/GamePan/sound.png");
	ImageIcon soundOff = new ImageIcon("IMG/GamePan/mute.png");
	private JButton blecteur = new JButton(Game.options[2].equals("MusicOn") ? soundOn : soundOff );

	private JButton bExit = new JButton(new ImageIcon( "IMG/GamePan/Exit.png"));

	private JLabel iconSu  = new JLabel( new ImageIcon("IMG/GamePan/SU2.png"));
	private JLabel iconSo  = new JLabel( new ImageIcon("IMG/GamePan/So2.png"));
	private JLabel iconSuC  = new JLabel( new ImageIcon( "IMG/GamePan/SUS2.png"));
	private JLabel iconSoC  = new JLabel( new ImageIcon( "IMG/GamePan/SoS2.png"));
	private JLabel iconBaseC  = new JLabel( new ImageIcon( "IMG/GamePan/Base.png"));
	private JLabel lNbSu = new JLabel("0");
	private JLabel lNbSo = new JLabel("0");
	private JLabel lNbSuSelect = new JLabel("0");
	private JLabel lNbSoSelect = new JLabel("0");
	private JLabel lBaseSelect = new JLabel(" ");

	public Camera camera = new Camera();
	private Minimap minimap = new Minimap();
	private Box BoxSidBand = Box.createVerticalBox();
	Key key = new Key(Game.human);


	//_____________CONSTRUCTOR_____________//

	/**
	 * Constructeur.
	 * @param Height Hauteur
	 * @param Width Largeur
	 */
	public GamePan(int Height,int Width ) {

		super();
		this.setLayout(null);

		this.addKeyListener(key);
		this.setFocusable(true);
		this.requestFocus();


		//Location and size of the components
		SidBand.setBounds(0,0,(int)(Width*0.2),(int)Height-(int)(Width*0.2*Finals.HEIGHT/Finals.WIDTH));
		minimap.setBounds(0,Height-(int)(Width*0.2*Finals.HEIGHT/((double)Finals.WIDTH)),(int)(Width*0.2),
				(int)(Width*0.2*Finals.HEIGHT/((double)Finals.WIDTH)));
		camera.setBounds ((int)(Width*0.2),0,(int)(Width*0.8),Height);
		SidBand.setBackground(new Color(240,240,240));
		BoxSidBand.setAlignmentX(Component.LEFT_ALIGNMENT);
		bExit.setBackground(null);


		//Ajout des images pour chaque objet
		NbSu.setBackground(null);
		NbSu.add(iconSu,null);
		NbSu.add(lNbSu, null);

		NbSo.setBackground(null);
		NbSo.add(iconSo,null);
		NbSo.add(lNbSo, null);

		NbSuSelect.setBackground(null);
		NbSuSelect.add(iconSuC,null);
		NbSuSelect.add(lNbSuSelect, null);
		NbSuSelect.setVisible(false);

		NbSoSelect.setBackground(null);
		NbSoSelect.add(iconSoC,null);
		NbSoSelect.add(lNbSoSelect, null);
		NbSoSelect.setVisible(false);

		BaseSelect.setBackground(null);
		BaseSelect.add(iconBaseC,null);
		BaseSelect.add(lBaseSelect, null);
		BaseSelect.setVisible(false);


		
		BoxSidBand.add(blecteur, null);
		BoxSidBand.add(NbSu);
		BoxSidBand.add(NbSo,null);
		BoxSidBand.add(NbSuSelect, null);
		BoxSidBand.add(NbSoSelect, null);
		BoxSidBand.add(BaseSelect, null);

		SidBand.add(BoxSidBand,null);

		
		ActionListener exit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// le bouton bExit ne sert pas si on a la croix
			}
		};

		ActionListener playPauseListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseOrPlay();
			}
		};

		bExit.addActionListener(exit);
		blecteur.addActionListener(playPauseListener);
		blecteur.setBorderPainted(false);
		blecteur.setBackground(null);
		this.add(SidBand);
		this.add(camera);
		this.add(minimap);
	}


	//______________METHODS______________//

	/**
	 * Actualise les donnees
	 */
	public void UpDate(){
		if (IA.player!=null){
			lNbSu.setText(Integer.toString(IA.player.simpleUnits.size()));
			lNbSo.setText(Integer.toString(IA.player.soldiers.size()));
		}
		if (Listeners.selected!=null){
			LinkedList<Unit> Us = Listeners.selected.group;
			int solS, suS;
			solS = 0;
			suS = 0;

			for (Unit u: Us){
				String className = u.getClass().getName();
				if (className == "Soldier")
					solS++;
				if (className == "SimpleUnit")
					suS++;
			}

			lNbSuSelect.setText(Integer.toString(suS));
			lNbSoSelect.setText(Integer.toString(solS));
			NbSuSelect.setVisible((suS>0));
			NbSoSelect.setVisible((solS>0));
		}
		BaseSelect.setVisible(Listeners.baseSelected);

	}

	/**
	 * Permet de mettre en pause ou en marche la musique
	 */
	public void pauseOrPlay(){
		Game.musicPlayer.invertLire();
		Game.musicPlayer = new PlayWave(Game.musicPlayer.getLire());
		Game.musicPlayer.start();
		blecteur.setIcon((Game.musicPlayer.lire) ? soundOn : soundOff);
		this.requestFocus();
	}
}