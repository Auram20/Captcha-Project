package fr.upem.captcha.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.panneaux.Panneau;
import fr.upem.captcha.images.ponts.Pont;
import fr.upem.captcha.images.villes.Ville;

public class MainUi {
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	final private static Images[] images = {new Pont(), new Ville(), new Panneau()};
	private static int result = 0;
	private static int goal = 0;
	private static int difficulty = 0;
	
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame("Capcha"); // Création de la fenêtre principale
		GridLayout layout = createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(1024, 768); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		JButton okButton = createOkButton();

		for(int i = 0; i < 9; ++i) {
			frame.add(createLabelImage("/fr/upem/captcha/images/villes/le havre.jpg", 0)); //ajouter des composants à la fenêtre
		}

		frame.add(new JTextArea("Cliquez n'importe où ... juste pour tester l'interface !"));
		frame.add(okButton);
		frame.setVisible(true);
	}
	
	private static GridLayout createLayout(){
		return new GridLayout(4,3);
	}
	
	@SuppressWarnings("serial")
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						if(result < goal) {
							System.out.println("Perdu");
						} else {
							System.out.println("Gagné");
						}
					}
				});
			}
		});
	}
	
	private static JLabel createLabelImage(String imageLocation, int point) throws IOException{
		
		URL url = MainUi.class.getResource(imageLocation); //Aller chercher les images !! IMPORTANT 
		
		Objects.requireNonNull(url);
		
		return createLabelImage(url, point);
		
	}
	
	private static JLabel createLabelImage(URL url, int point) throws IOException {
		
		System.out.println(url); 
		
		BufferedImage img = ImageIO.read(url); //lire l'image
		Image sImage = img.getScaledInstance(1024/3,768/4, Image.SCALE_SMOOTH); //redimentionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // créer le composant pour ajouter l'image dans la fenêtre
		
		label.addMouseListener(new MouseListener() { //Ajouter le listener d'évenement de souris
			private boolean isSelected = false;
			
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
		
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { //ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
				EventQueue.invokeLater(new Runnable() { 
					
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
							isSelected = true;
							selectedImages.add(url);
							result += point;
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(url);
							result += point;
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
	
	private static URL getRandomURL() throws MalformedURLException {
		int rand = (int)Math.floor(Math.random() * images.length);
		return images[rand].getRandomPhotoURL();
	}
}
