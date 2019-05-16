package fr.upem.captcha.images;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

abstract public class AbstractImage implements Images {

	
	final static public int levels = 3;
	protected File directory;
	protected List<URL> urls;
	
	public boolean isPhotoCorrect(URL url) {
		try {
			return !Objects.isNull(ImageIO.read(url));
		} catch(Exception e) {
			return false;
		}
	}
	
	public List<URL> getPhotos() {
		try {
			return convertFileListToURL(loadImages(directory));
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ArrayList<URL>();
		}
	}
	
	public List<URL> getRandomPhotosURL(int length) throws MalformedURLException {
		List<URL> urls = getPhotos();
		if(length > urls.size()) {
			throw new IndexOutOfBoundsException();
		}
		Collections.shuffle(urls);
		return urls.subList(0, length);
	}
	
	
	public URL getRandomPhotoURL() throws MalformedURLException {
		return getRandomPhotosURL(1).get(0);
	}
	
	static public List<URL> convertFileListToURL(List<File> files) throws MalformedURLException {
		ArrayList<URL> urls = new ArrayList<URL>();
		for(File file : files) {
			urls.add(new URL(file.getPath()));
		}
		return urls;
	}
	
	protected AbstractImage(URL directory) {
		System.out.println(directory);
		try {
			URI dirURI = directory.toURI();
			URI parent = dirURI.getPath().endsWith("/") ? dirURI.resolve("..") : dirURI.resolve(".");
			this.directory = new File(parent);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		

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

}
