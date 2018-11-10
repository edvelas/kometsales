package co.com.kometsales.appapi.trucks.dto;

import java.util.regex.Pattern;

import co.com.kometsales.appapi.utils.Utils;

public enum TypeValidation {
	CARPLATE("CAR PLATE", "DEV100", 6, Utils.PATTERN_ALPHA_NUMERIC, true),
	MODEL("MODEL", "2012", 4, Utils.PATTERN_NUMBER, true),
	REGISTRATION("REGISTRATION", "ENVIGADO", 100, Utils.PATTERN_ALPHA_NUMERIC, true),
	COLOR("COLOR", "BLANCO", 100, Utils.PATTERN_ALPHA_NUMERIC, true),
	DATELOAD("DATE LOAD", "10/10/2018", 10, Utils.PATTERN_DATE, true);

	private String name;
	private String example;
	private int maxlength;
	private boolean required;
	private Pattern regex;

	private TypeValidation(String name, String example, int maxlength, Pattern regex, boolean required) {
		this.name = name;
		this.example = example;
		this.maxlength = maxlength;
		this.required = required;
		this.regex = regex;
	}

	public String getName() {
		return name;
	}

	public String getExample() {
		return example;
	}

	public int getMaxlength() {
		return maxlength;
	}

	public boolean isRequired() {
		return required;
	}

	public Pattern getRegex() {
		return regex;
	}

}
