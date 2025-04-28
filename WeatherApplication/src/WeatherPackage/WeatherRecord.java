package WeatherPackage;

// The WeatherRecord class contains the weather information for each day.
public class WeatherRecord {

    private String dayName; // name of weekday
    private String forecast; // forecast
    private int highTemp; // high temperature
    private int lowTemp; // low temperature
  
    
    /** Creates a new instance of WeatherData */
    public WeatherRecord(String name, String fcast, int high, int low) {

        dayName = name;
        forecast = fcast;
        highTemp = high;
        lowTemp = low;
    }
    
// ACCESSORS
    // retrieves day name
    public String getDayName(){
        return dayName;
    }
    
    // retrieves forecast
    public String getForecast(){
        return forecast;
    }
    
    // retrieves high temp
    public int getHighTemp(){
        return highTemp;
    }
    
    // retrieves low temp
    public int getLowTemp(){
        return lowTemp;
    }
    
    
// MUTATORS
    
    // sets day Name
    public void setDayName(String name){
        dayName = name;
    }
    
    // sets forecast
    public void setForecast(String fcast){
        forecast = fcast;
    }
    
    // sets high temp
    public void setHighTemp(int high){
        highTemp = high;
    }
    
     // sets low temp
    public void setLowTemp(int low){
        lowTemp = low;
    }
  
    public String toString(){
        return dayName + "\t" + forecast + "\t" + highTemp + "\t" + lowTemp;
    }
}// end WeatherRecord Class
