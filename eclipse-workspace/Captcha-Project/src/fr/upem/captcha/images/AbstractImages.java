package fr.upem.captcha.images;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class AbstractImages implements Images {

	abstract public List<URL> getPhotos();

	abstract public List<URL> getRandomPhotosURL(int length);

	@Override
	public URL getRandomPhotoURL() {
		return getRandomPhotosURL(1).get(0);
	}

	abstract public boolean isPhotoCorrect(URL url);

	public ArrayList<String> getResourcesFromClassDirectory() throws IOException {
		File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		return getResources(jarFile, getClassDirectory());
	}
	
	public String getClassDirectory() {
		return getParent(getClass().getName().replace('.', '/'));
	}
	
	static public String getParent(String path) {
		return path.substring(0, path.lastIndexOf('/'));
	}
	
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
	
	abstract public boolean checkResources(String name);
	abstract public void loadResources(List<String> resources);
}
