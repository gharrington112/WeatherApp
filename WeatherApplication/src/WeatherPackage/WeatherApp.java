package WeatherPackage;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeatherApp extends Application {

    // Constants
    private static final int MAX_RECORDS = 7;
    private String[] dayArray = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private String[] forecast = {"Sun", "Rain", "Clouds", "Wind", "Snow"};

    // Variables and Instances of Classes
    WeatherRecord[] weeklyDataArray = new WeatherRecord[MAX_RECORDS]; //store all weather records

    private Database weatherDB = new Database();

    private int nextDay = 0; // location of next empty position in the array
    private int numDays = 0; // number of input records

    private String xmlDayName; // temporary storage for name of weekday from xml
    private String xmlForecast; //temporary storage for forecast from xml
    private int xmlHighTemp; //temporary storage for high temperature from xml
    private int xmlLowTemp; // temporary storage for low temperature from xml

    // pane declaration
    private VBox mainPane;
    private HBox buttonPane;
    private WeatherPane weatherPane;
    private GridPane editPane;

    // ComboBox declaration
    private ComboBox allForecastChoice;
    private ComboBox weatherChoice;

    // Label declaration
    private Label forecastLabel;
    private Label highLabel;
    private Label lowLabel;

    // TextField declaration
    private TextField highField;
    private TextField lowField;

    // button declarations
    private Button sundayButton, mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton,
            saturdayButton, weeklyButton, statsButton, saveButton;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        mainPane = new VBox(10); // main pane which will serve as placeholder for button pane and graphics pane

        buttonPane = new HBox(10); // pane to hold GUI components for days, week and statistics
        buttonPane.setPadding(new Insets(15, 15, 15, 15));

        weatherPane = new WeatherPane(this); // Create a weather pane and send copy of host for images

        editPane = new GridPane();
        editPane.setHgap(10);
        editPane.setVgap(10);
        editPane.setPadding(new Insets(15, 15, 15, 15));

        // read XML data from file and store in weeklyDataArray 
        readXMLFile("Weather.xml");

        weeklyDataArray = orderDaysInArray();

        sundayButton = new Button("Sun");
        sundayButton.setOnAction(e -> weatherPane.drawGraphics(weeklyDataArray, 0));
        buttonPane.getChildren().add(sundayButton);

        mondayButton = new Button("Mon");
        mondayButton.setOnAction(e -> weatherPane.drawGraphics(weeklyDataArray, 1));
        buttonPane.getChildren().add(mondayButton);

        tuesdayButton = new Button("Tues");
        tuesdayButton.setOnAction(e -> weatherPane.drawGraphics(weeklyDataArray, 2));
        buttonPane.getChildren().add(tuesdayButton);

        wednesdayButton = new Button("Wed");
        wednesdayButton.setOnAction(e -> weatherPane.drawGraphics(weeklyDataArray, 3));
        buttonPane.getChildren().add(wednesdayButton);

        thursdayButton = new Button("Thurs");
        thursdayButton.setOnAction(e -> weatherPane.drawGraphics(weeklyDataArray, 4));
        buttonPane.getChildren().add(thursdayButton);

        fridayButton = new Button("Fri");
        fridayButton.setOnAction(e -> weatherPane.drawGraphics(weeklyDataArray, 5));
        buttonPane.getChildren().add(fridayButton);

        saturdayButton = new Button("Sat");
        saturdayButton.setOnAction(e -> weatherPane.drawGraphics(weeklyDataArray, 6));
        buttonPane.getChildren().add(saturdayButton);

        weeklyButton = new Button("Weekly");
        weeklyButton.setOnAction(e -> weatherPane.drawWeeklyGraphics(weeklyDataArray));
        buttonPane.getChildren().add(weeklyButton);

        statsButton = new Button("Stats");
        statsButton.setOnAction(e -> weatherPane.pieGraphInfo(weeklyDataArray));
        buttonPane.getChildren().add(statsButton);

        mainPane.getChildren().add(buttonPane);
        mainPane.getChildren().add(weatherPane);
        mainPane.getChildren().add(editPane);

        saveButton = new Button("Save");
        editPane.add(saveButton, 2, 0);


        // add drop down menus

        ObservableList<String> dayChoice = // combo box
                FXCollections.observableArrayList(dayArray);

        weatherChoice = new ComboBox(dayChoice);

        ObservableList<String> forecastChoice =
                FXCollections.observableArrayList(forecast);

         allForecastChoice = new ComboBox(forecastChoice);

        // finish adding drop down menu

        // add text fields

        forecastLabel = new Label("Forecast:");
        editPane.add(forecastLabel, 1, 2);
        editPane.add(allForecastChoice, 1, 3);


        highLabel = new Label("High Temp:");
        highField = new TextField();
        editPane.add(highLabel, 2, 2);
        editPane.add(highField, 2, 3);

        lowLabel = new Label("Low Temp:");
        lowField = new TextField();
        editPane.add(lowLabel, 3, 2);
        editPane.add(lowField, 3, 3);

        // end adding text fields

        editPane.add(weatherChoice, 1, 0);
        saveButton.setOnAction(e -> saveAction(weeklyDataArray, dayChoice.indexOf(weatherChoice.getValue())));
        weatherChoice.setOnAction(e -> editValues(weeklyDataArray, dayChoice.indexOf(weatherChoice.getValue())));

        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 500, 600);
        primaryStage.setTitle("Weather App"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    // the method reads info from the input XML file, and then stores it in the weatherArray[]
    public void storeData() {

        // create table in the database
        weatherDB.createTable();

        // store each Student Record in the table
        for (int i = 0; i < numDays; i++) {
            weatherDB.storeRecord(
                    weeklyDataArray[i].getDayName(),
                    weeklyDataArray[i].getForecast(),
                    weeklyDataArray[i].getHighTemp(),
                    weeklyDataArray[i].getLowTemp());
        }

    } // end storeData

    public void readXMLFile(String filename) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            NodeList list = document.getElementsByTagName("dailyWeather");

            // This for loop gathers all the weather attributes, puts them in a WeatherRecord object
            // then stores that weather in the weekArray
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);

                xmlDayName = element.getAttribute("name");
                xmlForecast = element.getAttribute("forecast");
                xmlHighTemp = getHighTemp(element);
                xmlLowTemp = getLowTemp(element);
                WeatherRecord myWeather = new WeatherRecord(xmlDayName, xmlForecast, xmlHighTemp, xmlLowTemp);

                // store weather record in array
                weeklyDataArray[nextDay] = myWeather;

                // increment number of weather records and move to next position in weatherArray
                numDays++;
                nextDay++;

                System.out.println(myWeather.toString()); // delete later

            } // end for loop loading the weatherArray[] with full weather records

            storeData();

        } // end try block
        catch (ParserConfigurationException parserException) {
            parserException.printStackTrace();
        } // end catch block
        catch (SAXException saxException) {
            saxException.printStackTrace();
        } // end catch block
        catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch block

    } // end readFile()

    // RETURNS THE HIGH TEMP OF DAILY WEATHER
    public int getHighTemp(Element parent) {
        NodeList child = parent.getElementsByTagName("highTemp");
        Node childTextNode = child.item(0).getFirstChild();
        return Integer.parseInt(childTextNode.getNodeValue());
    }

    // RETURNS THE LOW TEMP OF DAILY WEATHER
    public int getLowTemp(Element parent) {
        NodeList child = parent.getElementsByTagName("lowTemp");
        Node childTextNode = child.item(0).getFirstChild();
        return Integer.parseInt(childTextNode.getNodeValue());
    }

    public WeatherRecord[] orderDaysInArray() {

        WeatherRecord[] sortedDataArray = new WeatherRecord[MAX_RECORDS];

        for (int i = 0; i < MAX_RECORDS; i++) {
            String dayValue = dayArray[i];
            for (int j = 0; j < MAX_RECORDS; j++) {
                if (dayValue.equals(weeklyDataArray[j].getDayName())) {
                    sortedDataArray[i] = weeklyDataArray[j];
                }
            }
        }

        return sortedDataArray;

    }


    public void editValues(WeatherRecord[] record, int index) {

        // autofills info based on combobox choice
        highField.setText(Integer.toString(record[index].getHighTemp()));
        lowField.setText(Integer.toString(record[index].getLowTemp()));



    }

    public void saveAction(WeatherRecord[] record, int index){

        try {

        // move later to save button onAction
        record[index].setForecast(allForecastChoice.getValue().toString());
        record[index].setHighTemp(Integer.parseInt(highField.getText())); // should set High temp mutator
        record[index].setLowTemp(Integer.parseInt(lowField.getText())); // should set Low temp mutator

            storeData();
        }
        catch (InputMismatchException e) {
            highField.setText("Invalid Data Entry");
        }
        catch (NumberFormatException e) {
            highField.setText("Invalid Data Entry");
        }
    }
}