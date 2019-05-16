package fr.upem.captcha.images.ponts;

import fr.upem.captcha.images.AbstractImage;

final public class Pont extends AbstractImage {

	public Pont() {
		super(Pont.class.getResource("Pont.class"));
	}

	@Override
	public String toString() {
		return "pont";
	}
}
