package edu.uncc.weatherapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {
    String startTime,temperature,shortforcast,windspeed,icon,value;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getShortforcast() {
        return shortforcast;
    }

    public void setShortforcast(String shortforcast) {
        this.shortforcast = shortforcast;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getHumidy() {
        return value;
    }

    public void setHumidy(String value)  {

        this.value = value;
    }

    public Forecast() {
    }
    @Override
    public String toString() {
        return "Forecast{" +
                "startTime='" + startTime + '\'' +
                ", temperature='" + temperature + '\'' +
                ", shortForecast='" + shortforcast + '\'' +
                ", windSpeed='" + windspeed + '\'' +
                ", icon='" + icon + '\'' +
                ", humidity='" + value+ '\'' +
                '}';
    }



}
