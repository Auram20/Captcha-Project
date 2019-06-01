package fr.upem.captcha.images.panneaux;

/**
 * @author Cusumano/Anik
 */
import fr.upem.captcha.images.Specific;


/**
* Panneau class that inherits from the Specific class
*/
final public class Panneau extends Specific {

/**
  * Panneau constructor
  * 
  * @see Specific
*/
	public Panneau() {
		super();
		System.out.println("Panneau");
		load();
	}
	

}