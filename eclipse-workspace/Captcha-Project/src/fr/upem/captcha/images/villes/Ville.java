package fr.upem.captcha.images.villes;
/**
 * @author Cusumano/Anik
 */
import fr.upem.captcha.images.Specific;

/**
* Ville class that inherits from the Specific class
*/
final public class Ville extends Specific {
	
/**
  * Ville constructor
  * 
  * @see Specific
*/
	public Ville() {
		super();
		System.out.println("Ville");
		load();
	}
	

}
