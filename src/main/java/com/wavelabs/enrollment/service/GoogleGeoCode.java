package com.wavelabs.enrollment.service;

import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@SuppressWarnings({ "squid:S2259", "squid:S2674", "squid:S1488" })
@Component
public class GoogleGeoCode {
	private static final String GEO_CODE_SERVER = "http://maps.googleapis.com/maps/api/geocode/json?";
	Logger log = Logger.getLogger(GoogleGeoCode.class);

	private GoogleGeoCode() {

	}

	public String[] getLatitudeLongitude(String zipcode) {
		String code = zipcode;
		String response = getLocation(code);
		log.info("lattitude and logitude"+response);
		String[] result = parseLocation(response);
		return result;
	}

	private String getLocation(String code) {
		String address = buildUrl(code);

		String content = null;

		try {
			URL url = new URL(address);

			InputStream stream = url.openStream();

			try {
				int available = stream.available();

				byte[] bytes = new byte[available];

				stream.read(bytes);

				content = new String(bytes);
			} finally {
				stream.close();
			}

			return content;
		} catch (Exception e) {
			log.info(e);
		}
		return content;
	}

	private String buildUrl(String code) {
		StringBuilder builder = new StringBuilder();

		builder.append(GEO_CODE_SERVER);

		builder.append("address=");
		builder.append(code.replaceAll(" ", "+"));
		builder.append("&sensor=false");

		return builder.toString();
	}

	private String[] parseLocation(String response) {
		// Look for location using brute force.
		// There are much nicer ways to do this, e.g. with Google's JSON
		// library: Gson
		// https://sites.google.com/site/gson/gson-user-guide

		String[] lines = response.split("\n");

		String lat = null;
		String lng = null;

		for (int i = 0; i < lines.length; i++) {
			if ("\"location\" : {".equals(lines[i].trim())) {
				lat = getOrdinate(lines[i + 1]);
				lng = getOrdinate(lines[i + 2]);
				break;
			}
		}

		return new String[] { lat, lng };
	}

	private String getOrdinate(String s) {
		String[] split = s.trim().split(" ");

		if (split.length < 1) {
			return null;
		}

		String ord = split[split.length - 1];

		if (ord.endsWith(",")) {
			ord = ord.substring(0, ord.length() - 1);
		}

		// Check that the result is a valid double
		Double.parseDouble(ord);

		return ord;
	}

}
