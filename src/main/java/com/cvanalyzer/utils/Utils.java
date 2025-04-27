package com.cvanalyzer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	  private static final String EMAIL_PATTERN =
	            "[a-zA-Z0-9_+&*-]+(?:\\." +
	            "[a-zA-Z0-9_+&*-]+)*@" +
	            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
	            "A-Z]{2,7}";

	    private static final String PHONE_PATTERN =
	            "\\b(?:\\+\\d{1,3}[\\s-]?)?(?:\\d{10}|\\d{3}[\\s.-]\\d{3}[\\s.-]\\d{4})\\b";

	    private static final Pattern emailRegex = Pattern.compile(EMAIL_PATTERN);
	    private static final Pattern phoneRegex = Pattern.compile(PHONE_PATTERN);

	    public static String extractEmail(String text) {
	        Matcher matcher = emailRegex.matcher(text);
	        if (matcher.find()) {
	            return matcher.group();
	        }
	        return null;
	    }

	    public static String extractPhone(String text) {
	        Matcher matcher = phoneRegex.matcher(text);
	        if (matcher.find()) {
	            return matcher.group();
	        }
	        return null;
	    }

}
