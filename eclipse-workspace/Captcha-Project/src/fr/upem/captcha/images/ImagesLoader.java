package fr.upem.captcha.images;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

public class ImagesLoader {
	
	static public List<URL> convertFileListToURL(List<File> files) throws MalformedURLException {
		ArrayList<URL> urls = new ArrayList<URL>();
		for(File file : files) {
			urls.add(new URL(file.getPath()));
		}
		return urls;
	}
	
	public static URI getParent(URL path) throws URISyntaxException {
		URI pathURI = path.toURI();
		URI parent = pathURI.getPath().endsWith("/") ? pathURI.resolve("..") : pathURI.resolve(".");
		return parent;
	}
	
	
	public static ArrayList<File> loadImages(File directory) {
		System.out.println(directory.toString());
		if(directory.isDirectory()) {
			File[] matchingFiles = directory.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("png");
			    }
			});
	
			ArrayList<File> files = new ArrayList<File>(Arrays.asList(matchingFiles));
			
			for(File file : files) {
				System.out.println(file.toString());
			}
			
			return files;
		} else {
			System.out.println("n'est pas une répertoire");
			return new ArrayList<File>();
		}
	}
	
	
	public static List<URL> getPhotos(File directory) {
		try {
			return convertFileListToURL(loadImages(directory));
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ArrayList<URL>();
		}
	}
	
	public static List<URL> getRandomPhotosURL(File directory, int length) throws MalformedURLException {
		List<URL> urls = getPhotos(directory);
		if(length > urls.size()) {
			throw new IndexOutOfBoundsException();
		}
		Collections.shuffle(urls);
		return urls.subList(0, length);
	}
	
	
	public static URL getRandomPhotoURL(File directory) throws MalformedURLException {
		return getRandomPhotosURL(directory, 1).get(0);
	}

}
