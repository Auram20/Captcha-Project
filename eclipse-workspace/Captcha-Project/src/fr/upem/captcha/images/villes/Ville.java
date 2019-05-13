package fr.upem.captcha.images.villes;

import fr.upem.captcha.images.AbstractImage;

final public class Ville extends AbstractImage {
	
	public Ville() {
		super(Ville.class.getResource("Ville.class"));
	}

	@Override
	public String toString() {
		return "ville";
	}
}
