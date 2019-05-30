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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import static javax.swing.JOptionPane.showMessageDialog;
import fr.upem.captcha.images.Global;

public class MainUi {
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	final private static Global images = new Global();
	
	private static int goal = 0;
	private static int attemptNumber = 0;
	private static String actualClassName;
	private static JFrame frame = new JFrame("Captcha"); // Création de la fenêtre principale
	
	public static void main(String[] args) {
		
		images.load();
		
		GridLayout layout = createLayout(attemptNumber);  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(1024, 768); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		
		play();
	}
	
	private static void play() {
		actualClassName = images.getRandomClassName();
		String toSearch =  actualClassName.substring(actualClassName.lastIndexOf(".")+1);
		System.out.println(actualClassName);
		
		JButton okButton = createOkButton();
		
		List<URL> randomURLs;
		try {
			System.out.println(algorithm());
			randomURLs = images.getRandomPhotosURL(actualClassName, 4 + algorithm());
			randomURLs.addAll(images.getRandomPhotosURL(5 + algorithm()));
			for(URL randomURL : randomURLs) {
				frame.add(createLabelImage(randomURL));
			}
			goal = count(randomURLs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		showMessageDialog(null, "Séléctionnez les images correspondant à des : " + toSearch);
		frame.add(new JTextArea("Séléctionnez les images correspondant à des : " + toSearch));
		frame.add(okButton);
		frame.setVisible(true);
	}
	
	private static GridLayout createLayout(int supp){
		return new GridLayout(4 + supp, 3);
	}
	
	@SuppressWarnings("serial")
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						int result = countResult(selectedImages);
						if(result < goal) {
							showMessageDialog(null, "Captcha échoué  ");
							System.out.println("Perdu");
							frame.getContentPane().removeAll();
							frame.repaint();
							attemptNumber++;
							GridLayout layout = createLayout(algorithm());
							frame.setLayout(layout);
							selectedImages.clear();
							play();
						} else {
							System.out.println("Gagné");
							showMessageDialog(null, "Captcha réussi ! ");
							System.exit(0);
						}
					}
				});
			}
		});
	}
	
	private static int algorithm() {
		return (int) Math.max(Math.floor(Math.min(Math.log10((double) 10 * attemptNumber), 20.)), 0);
	}
	
	private static int count(List<URL> urls) {
		int sum = 0;
		try {
			for(URL url : urls) {
				if(images.isPhotoCorrect(url, actualClassName)) {
					++sum;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Goal : " + sum);
		return sum;
	}
	
	private static int countResult(List<URL> urls) {
		int sum = 0;
		try {
			for(URL url : urls) {
				if(images.isPhotoCorrect(url, actualClassName)) {
					++sum;
				} else {
					--sum;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Result : " + sum);
		return sum;
	}
	
	private static JLabel createLabelImage(URL url) throws IOException {
		
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
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(url);
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
}
