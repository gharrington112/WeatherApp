package WeatherPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class WeatherPane extends Pane {

    public void pieGraphInfo(WeatherRecord[] wRec){ // pie chart graph


        // loop to calculate forecasted days

        double sunCounter = 0;
        double cloudyCounter = 0;
        double windyCounter = 0;
        double snowyCounter = 0;
        double rainyCounter = 0;
        double avgHigh = 0;
        double avgLow = 0;

        for(int day = 0; day <= 6; day++) {
            switch (wRec[day].getForecast()) {
                case "Sun":
                    sunCounter++;
                    avgHigh += wRec[day].getHighTemp();
                    avgLow += wRec[day].getLowTemp();
                    break;
                case "Clouds":
                    cloudyCounter++;
                    avgHigh += wRec[day].getHighTemp();
                    avgLow += wRec[day].getLowTemp();
                    break;
                case "Rain":
                    rainyCounter++;
                    avgHigh += wRec[day].getHighTemp();
                    avgLow += wRec[day].getLowTemp();
                    break;
                case "Snow":
                    snowyCounter++;
                    avgHigh += wRec[day].getHighTemp();
                    avgLow += wRec[day].getLowTemp();
                    break;
                case "Wind":
                    windyCounter++;
                    avgHigh += wRec[day].getHighTemp();
                    avgLow += wRec[day].getLowTemp();
                    break;
            }
        }
        avgHigh = (((int)((avgHigh/7)*100))/100.0); // this gives us avgHigh
        avgLow = (((int)((avgLow/7)*100))/100.0); // this gives us avgLow


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Sunny " + (((int)(((sunCounter/7)*100)*100))/100.0 + "%"), sunCounter),
                        new PieChart.Data("Clouds " + (((int)(((cloudyCounter/7)*100)*100))/100.0 + "%"), cloudyCounter),
                        new PieChart.Data("Wind " + (((int)(((windyCounter/7)*100)*100))/100.0 + "%"), windyCounter),
                        new PieChart.Data("Rain " + (((int)(((rainyCounter/7)*100)*100))/100.0 + "%"), rainyCounter),
                        new PieChart.Data("Snow " + (((int)(((snowyCounter/7)*100)*100))/100.0 + "%"), snowyCounter));
        final PieChart chart = new PieChart(pieChartData);
        chart.setStyle("-fx-font: 12 arial");
        chart.setTitle("Weekly Weather");

        getChildren().clear();
        getChildren().add(chart);
        Text avgHighText = new Text("Avg. High " + (avgHigh) + " °");
        avgHighText.setStyle("-fx-font: 12 arial");
        avgHighText.setX(20);
        avgHighText.setY(10);
        Text avgLowText = new Text("Avg. Low " + (avgLow) + " °");
        avgLowText.setStyle("-fx-font: 12 arial");
        avgLowText.setX(20);
        avgLowText.setY(25);
        getChildren().add(avgHighText);
        getChildren().add(avgLowText);
    }
    private WeatherApp wApp;

    public WeatherPane(WeatherApp a)
    {
       wApp = a;
    }

    public void drawGraphics(WeatherRecord[] wInfo, int index) {

        getChildren().clear(); // clear the pane for next graphicS
        
        // add code to display the input name of the weekday contained in  wInfo parameter

        Text dayText = new Text(25, 15, wInfo[index].getDayName());
        dayText.setStyle("-fx-font: 24 arial");
        getChildren().add(dayText);

        // add code to display the low  temperature contained in  wInfo parameter

        Text lowText = new Text(250, 150, "Low temp: " + (wInfo[index].getLowTemp()) + " °");
        lowText.setStyle("-fx-font: 15 arial");
        getChildren().add(lowText);
        
        // add code to display the high temperature contained in  wInfo parameter

        Text highText = new Text(250, 100, "High temp: " + (wInfo[index].getHighTemp()) + " °");
        highText.setStyle("-fx-font: 15 arial");
        getChildren().add(highText);
                
        switch (wInfo[index].getForecast()) // use forecast from wInfo parameter to draw appropriate graphics
        {    
        
            case "Sun": // Code to draw the graphics for Sun

                Text sunnyText = new Text(25, 35, "Sunny");
                sunnyText.setStyle("-fx-font: 15 arial");
                sunnyText.setFill(Color.GREEN);  // Change the pen color
                getChildren().add(sunnyText);

                // Change the wording of the text above and add code here to display graphics for sunny weather

                ImageView sun = new ImageView(new Image(WeatherPane.class.getResource("/sun.jpg").toString()));
                sun.setFitHeight(200);
                sun.setFitWidth(200);
                sun.setX(15);
                sun.setY(50);
                getChildren().add(sun);
                
                break;
                
            case "Clouds": // Code to draw the graphics for Clouds

                Text cloudyText = new Text(25, 35, "Cloudy");
                cloudyText.setStyle("-fx-font: 15 arial");
                cloudyText.setFill(Color.RED);  // Change the pen color
                getChildren().add(cloudyText);
                
                // Change the wording of the text above and add code here to display graphics for cloudy weather

                ImageView cloudy = new ImageView(new Image(WeatherPane.class.getResource("/cloudy.jpg").toString()));
                cloudy.setFitHeight(200);
                cloudy.setFitWidth(200);
                cloudy.setX(15);
                cloudy.setY(50);
                getChildren().add(cloudy);
                break;
                
            case "Snow":   // Code to draw the graphics for Snow
                Text snowText = new Text(25, 35, "Snowy");
                snowText.setStyle("-fx-font: 15 arial");
                snowText.setFill(Color.BLUE);  // Change the pen color
                getChildren().add(snowText);

                ImageView snowy = new ImageView(new Image(WeatherPane.class.getResource("/snow.jpg").toString()));
                snowy.setFitHeight(200);
                snowy.setFitWidth(200);
                snowy.setX(15);
                snowy.setY(50);
                getChildren().add(snowy);
                
                // Change the wording of the text above and add code here to display graphics for snowy weather

                break;
                
            case "Rain":  // Code to draw the graphics for rain
                Text rainText = new Text(25, 35, "Rainy");
                rainText.setStyle("-fx-font: 15 arial");
                rainText.setFill(Color.MAGENTA);  // Change the pen color
                getChildren().add(rainText);
                
                // Change the wording of the text above and add code here to display graphics for  rainy weather

                ImageView rain = new ImageView(new Image(WeatherPane.class.getResource("/rain.jpg").toString()));
                rain.setFitHeight(200);
                rain.setFitWidth(200);
                rain.setX(15);
                rain.setY(50);
                getChildren().add(rain);

                break;

            case "Wind":   // Code to draw the graphics for wind
                Text windText = new Text(25, 35, "Windy");
                windText.setStyle("-fx-font: 15 arial");
                windText.setFill(Color.TOMATO);  // Change the pen color
                getChildren().add(windText);
                
                // Change the wording of the text above and add code here to display graphics for windy weather

                ImageView windy = new ImageView(new Image(WeatherPane.class.getResource("/windy.jpg").toString()));
                windy.setFitHeight(200);
                windy.setFitWidth(200);
                windy.setX(15);
                windy.setY(50);
                getChildren().add(windy);
                break;

            default:
        } // end switch
    }
    public void drawWeeklyGraphics(WeatherRecord[] wInfo) {

        getChildren().clear(); // clear the pane for next graphicS

        // add code to display the input name of the weekday contained in  wInfo parameter
        int xPos = 5;
        for(int index = 0; index <= 6; index++) {
            //int xPos = 5;
            Text dayText = new Text(xPos, 60, wInfo[index].getDayName());
            dayText.setStyle("-fx-font: 12 arial");
            getChildren().add(dayText);

            // add code to display the low  temperature contained in  wInfo parameter

            Text lowText = new Text(xPos + 5, 250, (wInfo[index].getLowTemp()) + " °");
            lowText.setStyle("-fx-font: 12 arial");
            getChildren().add(lowText);

            // add code to display the high temperature contained in  wInfo parameter

            Text highText = new Text(xPos + 5, 230, (wInfo[index].getHighTemp()) + " °");
            highText.setStyle("-fx-font: 12 arial");
            getChildren().add(highText);

            switch (wInfo[index].getForecast()) // use forecast from wInfo parameter to draw appropriate graphics
            {

                case "Sun": // Code to draw the graphics for Sun

                    Text sunnyText = new Text(xPos, 100, "Sunny");
                    sunnyText.setFill(Color.GREEN);  // Change the pen color
                    getChildren().add(sunnyText);

                    // Change the wording of the text above and add code here to display graphics for sunny weather

                    ImageView sun = new ImageView(new Image(WeatherPane.class.getResource("/sun.jpg").toString()));
                    sun.setFitHeight(60);
                    sun.setFitWidth(60);
                    sun.setX(xPos);
                    sun.setY(150);
                    getChildren().add(sun);

                    break;

                case "Clouds": // Code to draw the graphics for Clouds

                    Text cloudyText = new Text(xPos, 100, "Cloudy");
                    cloudyText.setFill(Color.RED);  // Change the pen color
                    getChildren().add(cloudyText);

                    // Change the wording of the text above and add code here to display graphics for cloudy weather

                    ImageView cloudy = new ImageView(new Image(WeatherPane.class.getResource("/cloudy.jpg").toString()));
                    cloudy.setFitHeight(60); //about 65x65
                    cloudy.setFitWidth(60);
                    cloudy.setX(xPos);
                    cloudy.setY(150);
                    getChildren().add(cloudy);
                    break;

                case "Snow":   // Code to draw the graphics for Snow
                    Text snowText = new Text(xPos, 100, "Snowy");
                    snowText.setFill(Color.BLUE);  // Change the pen color
                    getChildren().add(snowText);

                    ImageView snowy = new ImageView(new Image(WeatherPane.class.getResource("/snow.jpg").toString()));
                    snowy.setFitHeight(60);
                    snowy.setFitWidth(60);
                    snowy.setX(xPos);
                    snowy.setY(150);
                    getChildren().add(snowy);

                    // Change the wording of the text above and add code here to display graphics for snowy weather

                    break;

                case "Rain":  // Code to draw the graphics for rain
                    Text rainText = new Text(xPos, 100, "Rainy");
                    rainText.setFill(Color.MAGENTA);  // Change the pen color
                    getChildren().add(rainText);

                    // Change the wording of the text above and add code here to display graphics for  rainy weather

                    ImageView rain = new ImageView(new Image(WeatherPane.class.getResource("/rain.jpg").toString()));
                    rain.setFitHeight(60);
                    rain.setFitWidth(60);
                    rain.setX(xPos);
                    rain.setY(150);
                    getChildren().add(rain);

                    break;

                case "Wind":   // Code to draw the graphics for wind
                    Text windText = new Text(xPos, 100, "Windy");
                    windText.setFill(Color.TOMATO);  // Change the pen color
                    getChildren().add(windText);

                    // Change the wording of the text above and add code here to display graphics for windy weather

                    ImageView windy = new ImageView(new Image(WeatherPane.class.getResource("/windy.jpg").toString()));
                    windy.setFitHeight(60);
                    windy.setFitWidth(60);
                    windy.setX(xPos);
                    windy.setY(150);
                    getChildren().add(windy);
                    break;

                default:
            }// end switch
            xPos += 72;
        }
    }
}

