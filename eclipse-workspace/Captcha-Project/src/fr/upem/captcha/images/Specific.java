package fr.upem.captcha.images;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* Specific class that inherits from the AbstractImages class
*/

abstract public class Specific extends AbstractImages {
	
	private ArrayList<URL> photos = new ArrayList<URL>();

	public Specific() {
		super();
	}
	
	@Override
	public List<URL> getPhotos() {
		return photos;
	}

	@Override
	public List<URL> getRandomPhotosURL(int length) {
		if(photos.size() <= length) {
			throw new IndexOutOfBoundsException();
		}
		Collections.shuffle(photos);
		return photos.subList(0, length);
	}

	
	@Override
	public void loadResources(List<String> resources) {
		for(String resource : resources) {
			photos.add(getClass().getResource('/' + resource));
		}
	}
	
	@Override
	public boolean checkResources(String name) {
		return (name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("png")) && !name.endsWith("/");
	}

	@Override
	public boolean isPhotoCorrect(URL url) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
