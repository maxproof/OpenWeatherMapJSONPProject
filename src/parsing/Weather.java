package parsing;

public class Weather {
	private String city;
	private String country;
	private String temperature;
	private String sunrise;
	private String sunset;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	@Override
	public String toString() {
		return "Weather [city=" + city + ", country=" + country + ", temperature=" + temperature + ", sunrise="
				+ sunrise + ", sunset=" + sunset + "]";
	}

}
