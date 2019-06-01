package fr.upem.captcha.images;

/**
 * @author Cusumano/Anik
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
* Abstract class that implements the interface Image and has loading resources methods
*/
public abstract class AbstractImages implements Images {

	/**
	 * Get Photos method in interface
	 * @return URLs list
	 */
	abstract public List<URL> getPhotos();

	/**
	 * Get random urls method in interface
	 * @return Random URLs list
	 * @param length of list wanted
	 */
	abstract public List<URL> getRandomPhotosURL(int length);

	
	/**
	 * Overrides the getRandomPhotoURL method
	 * @return Single random photo URL
	 */
	@Override
	public URL getRandomPhotoURL() {
		return getRandomPhotosURL(1).get(0);
	}

	
	/**
	 * Checks if photo selected is correct 
	 * @return Boolean if correct or not
	 */
	abstract public boolean isPhotoCorrect(URL url);

	
	/**
	 * Get resources from the ClassDirectory
	 * @throws IOException if no good entry selected
	 * @return List with all the paths to class files. 
	 */
	public ArrayList<String> getResourcesFromClassDirectory() throws IOException {
		File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		return getResources(jarFile, getClassDirectory());
	}
	
	/**
	 * Gets the class directory
	 * @return Class directory string 
	 */
	public String getClassDirectory() {
		return getParent(getClass().getName().replace('.', '/'));
	}
	
	/**
	 * Gets directory's parent path
	 * @return path string 
	 * @param path directory path string
	 */
	static public String getParent(String path) {
		return path.substring(0, path.lastIndexOf('/'));
	}
	
	/**
	 * Get resources from a path 
	 * @return String list with the resources 
	 * @param jarFile 
	 * 		Jar file 
	 * @param
	 * 		path of the file
	 * @throws IOException if no good entry selected
	 */
	public ArrayList<String> getResources(File jarFile, String path) throws IOException {
		ArrayList<String> resources = new ArrayList<String>();
		if(jarFile.isFile()) {  // Run with JAR file
		    final JarFile jar = new JarFile(jarFile);
		    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
		    while(entries.hasMoreElements()) {
		        final String name = entries.nextElement().getName();
		        if (name.startsWith(path + "/") && checkResources(name)) { //filter according to the path
		        	System.out.println(name);
		        	resources.add(name);
		        }
		    }
		    jar.close();
		}
		
		return resources;
	}
	
	/**
	 * Loads resources from class directory
	 */
	public void load() {
		ArrayList<String> resources;
		try {
			resources = getResourcesFromClassDirectory();
			loadResources(resources);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Check resources
	 * @param name resource string name
	 * @return boolean if resource in 
	 */
	abstract public boolean checkResources(String name);
	
	/**
	 * Loads resources from a list
	 * @param resources list of resources string 
	 */
	abstract public void loadResources(List<String> resources);
}
