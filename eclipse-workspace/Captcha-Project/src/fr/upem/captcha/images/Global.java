package fr.upem.captcha.images;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Global extends AbstractImages {

	protected ArrayList<AbstractImages> subClasses = new ArrayList<AbstractImages>();
	
	
	@SuppressWarnings("deprecation")
	protected void addClassToList(String className) {
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
	
	public List<URL> getPhotos(String className) throws ClassNotFoundException {
		AbstractImages images = findClass(className);
		return images.getPhotos();
	}

	@Override
	public List<URL> getRandomPhotosURL(int length) {
		List<URL> photos = new ArrayList<URL>(getPhotos());
		if(photos.size() <= length) {
			throw new IndexOutOfBoundsException();
		}
		
		Collections.shuffle(photos);
		return photos.subList(0, length);
	}
	
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
	
	public boolean isPhotoCorrect(URL url, String className) throws ClassNotFoundException {
		List<URL> urls = getPhotos(className);
		System.out.println(urls.size());
		return urls.contains(url);
	}
	
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
	
	static public int occurrences(String s, String c) {
		if(c.length() != 0) {
			return (s.length() - s.replace(c, "").length()) / c.length();
		} else {
			return s.length();
		}
	}
	
	public String getRandomClassName() {
		if(subClasses.size() == 0) {
			throw new IndexOutOfBoundsException();
		}
		int rand = (int) (Math.random() * subClasses.size());
		return subClasses.get(rand).getClass().getName();
	}
	
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
