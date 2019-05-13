package fr.upem.captcha.images.panneaux;

import fr.upem.captcha.images.AbstractImage;

final public class Panneau extends AbstractImage {

	public Panneau() {
		super(Panneau.class.getResource("Panneau.class"));
	}

	@Override
	public String toString() {
		return "panneau";
	}

}
