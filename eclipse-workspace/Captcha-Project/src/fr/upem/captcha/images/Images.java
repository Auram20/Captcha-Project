package fr.upem.captcha.images;

/**
 * @author Cusumano/Anik
 */

import java.net.URL;
import java.util.List;

  /**
  * Interface Image with different methods
  */
public interface Images {
	/**
	 * Get Photos method in interface
	 * @return URLs list
	 */
	public List<URL> getPhotos();
	
	
	/**
	 * Get random urls method in interface
	 * @return Random URLs list
	 * @param length of list wanted
	 */
	public List<URL> getRandomPhotosURL(int length);
	
	
	/**
	* Get a single random url method in interface
	* @return Single random URL
	*/
	public URL getRandomPhotoURL();
	
	/**
	* Checking method in interface
	* @return boolean of validication
	* @param url of photo to check
	*/
	public boolean isPhotoCorrect(URL url);
}
