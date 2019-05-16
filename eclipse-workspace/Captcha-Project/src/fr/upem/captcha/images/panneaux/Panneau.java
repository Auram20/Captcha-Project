package fr.upem.captcha.images.panneaux;

import fr.upem.captcha.images.ImagesLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.upem.captcha.images.Images;

final public class Panneau implements Images {

	private ArrayList<URL> photos = new ArrayList<URL>();
	
	public Panneau() {

		
	}

	@Override
	public String toString() {
		return "panneau";
	}

	@Override
	public List<URL> getPhotos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<URL> getRandomPhotosURL(int length) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getRandomPhotoURL() throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPhotoCorrect(URL url) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
