package fr.upem.captcha.images;

/**
 * @author Cusumano/Anik
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* Global class that inherits from the AbstractImages class
*/
public class Global extends AbstractImages {
	
	/**
	* List of subClasses
	*/
	private ArrayList<AbstractImages> subClasses = new ArrayList<AbstractImages>();
	
	/**
	* Constructor calls super constructor
	*/
	public Global() {
		super();
	}
	
	/**
	* Add class to the class's list
	* @param String of class name
	*/
	@SuppressWarnings("deprecation")
	private void addClassToList(String className) {
		Object o;
		try {
			o = Class.forName(className).newInstance();
			if(o instanceof AbstractImages) {
				subClasses.add((AbstractImages) o);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get Photos method in interface
	 * @param className String of class name
	 * @throws ClassNotFoundException if class is not found
	 * @return URLs list
	 */
	public List<URL> getPhotos(String className) throws ClassNotFoundException {
		AbstractImages images = findClass(className);
		return images.getPhotos();
	}

	/**
	* Get random photos urls 
	* @param length of the list
	* @return list of photos urls 
	*/
	@Override
	public List<URL> getRandomPhotosURL(int length) {
		List<URL> photos = new ArrayList<URL>(getPhotos());
		if(photos.size() <= length) {
			throw new IndexOutOfBoundsException();
		}
		
		Collections.shuffle(photos);
		return photos.subList(0, length);
	}
	
	/**
	* Get random photos urls 
	* @param length  length of the list and the class name where the urls are taken from 
	* @param className class name string
	* @throws ClassNotFoundException If the class is not found
	* @return list of photos urls 
	*/
	public List<URL> getRandomPhotosURL(String className, int length) throws ClassNotFoundException {
		List<URL> photos = new ArrayList<URL>(getPhotos(className));
		if(photos.size() <= length) {
			throw new IndexOutOfBoundsException();
		}
		
		Collections.shuffle(photos);
		return photos.subList(0, length);
	}


	@Override
	public boolean isPhotoCorrect(URL url) {
		List<URL> urls = getPhotos();
		return urls.contains(url);
	}
	
	/**
	* Checks if photo selected is correct
	* @param url url of the photo
	* @param className class name 
	* @return boolean if photo is correct
	* @throws ClassNotFoundException if class is not found
	*/
	public boolean isPhotoCorrect(URL url, String className) throws ClassNotFoundException {
		List<URL> urls = getPhotos(className);
		return urls.contains(url);
	}
	
	
	/**
	* Way to write the class name
	*/
	@Override
	public String toString() {
		return getClass().getName();
	}
	
	protected AbstractImages findClass(String className) throws ClassNotFoundException {
		for(AbstractImages subClass : subClasses) {
			if(subClass.getClass().getName().equals(className)) {
				return subClass;
			}
		}
		throw new ClassNotFoundException();
	}

	@Override
	public boolean checkResources(String name) {
		return name.endsWith(".class")
				&& name.indexOf('$') == -1
				&& occurrences(name, "/") != occurrences(getClassDirectory(), "/") + 1;
	}

	@Override
	public void loadResources(List<String> resources) {
		for(String resource : resources) {
			addClassToList(resource.replace('/', '.').replace(".class", ""));
		}
	}
	
	
	/**
	* Checks occurences are there in a string 
	* @param s string for occurences
	* @param c string for occurences
	* @return occurences in a string 
	*/
	static public int occurrences(String s, String c) {
		if(c.length() != 0) {
			return (s.length() - s.replace(c, "").length()) / c.length();
		} else {
			return s.length();
		}
	}
	
	/**
	 * Get random class name in subclasses 
	 * @return random class name in subclasses  
	 */
	public String getRandomClassName() {
		if(subClasses.size() == 0) {
			throw new IndexOutOfBoundsException();
		}
		int rand = (int) (Math.random() * subClasses.size());
		return subClasses.get(rand).getClass().getName();
	}
	
	/**
	 * Get random class in the list of subclasses 
	 * @return random classes 
	 */
	public AbstractImages getRandomClass() {
		int rand = (int) (Math.random() * subClasses.size());
		return subClasses.get(rand);
	}
	
	@Override
	public List<URL> getPhotos() {
		ArrayList<URL> urls = new ArrayList<URL>();
		for(AbstractImages subClass : subClasses) {
			urls.addAll(subClass.getPhotos());
		}
		return urls;
	}

}
