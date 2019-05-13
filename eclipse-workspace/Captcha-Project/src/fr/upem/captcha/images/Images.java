package fr.upem.captcha.images;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public interface Images {
	public List<URL> getPhotos();
	public List<URL> getRandomPhotosURL(int length) throws MalformedURLException;
	public URL getRandomPhotoURL() throws MalformedURLException;
	public boolean isPhotoCorrect(URL url) throws IOException;
}
