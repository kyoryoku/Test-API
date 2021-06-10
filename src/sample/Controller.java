package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class Controller {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private Button form_browseApiFile;
    @FXML private TextField form_apiFile;
    @FXML private TextField form_ipAddress;
    @FXML private TextField form_port;
    @FXML private ListView<String> form_availableApi;
    @FXML private ListView<String> form_selectedApi;
    @FXML private Button form_run;
    @FXML private TextField form_pause;
    @FXML private ToggleButton form_mode;
    @FXML private TextArea form_results;
    @FXML private Button form_clear;
    @FXML private CheckBox form_autoClear;
    @FXML private Button form_details;
    @FXML private Button form_add;
    @FXML private Button form_remove;

    private ObservableList<String> availableApi = FXCollections.observableArrayList();
    private SimpleStringProperty currentAvailableApi = new SimpleStringProperty();
    private ObservableList<String> selectedApi = FXCollections.observableArrayList();
    private SimpleStringProperty currentSelectedApi = new SimpleStringProperty();

    private SimpleStringProperty ipAddress = new SimpleStringProperty("127.0.0.1");
    private SimpleStringProperty port = new SimpleStringProperty("8084");
    private SimpleStringProperty apiFile = new SimpleStringProperty("D:\\testApi.csv");
    private SimpleStringProperty pause = new SimpleStringProperty("2000");
    private SimpleStringProperty results = new SimpleStringProperty();
    private SimpleStringProperty mode = new SimpleStringProperty("Автоматический режим");
    private SimpleBooleanProperty autoClear = new SimpleBooleanProperty(true);


    @FXML
    public void initialize() {
        form_apiFile.textProperty().bindBidirectional(apiFile);
        form_ipAddress.textProperty().bindBidirectional(ipAddress);
        form_port.textProperty().bindBidirectional(port);
        form_results.textProperty().bindBidirectional(results);
        form_pause.textProperty().bindBidirectional(pause);
        form_mode.textProperty().bindBidirectional(mode);

        form_availableApi.setItems(availableApi);
        form_availableApi.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null){
                currentAvailableApi.set(newValue);
            }
        });
        form_selectedApi.setItems(selectedApi);
        form_selectedApi.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null){
                currentSelectedApi.set(newValue);
            }
        });

        form_autoClear.selectedProperty().bindBidirectional(autoClear);

    }

    @FXML
    void form_run_OnAction(ActionEvent event) {

    }

    @FXML
    void form_browseApiFile_OnAction(ActionEvent event) {

    }


    @FXML
    void form_add_OnAction(ActionEvent event) {

    }

    @FXML
    void form_remove_OnAction(ActionEvent event) {

    }

    @FXML
    void form_details_OnAction(ActionEvent event) {

    }

    @FXML
    void form_clear_OnAction(ActionEvent event) {

    }

    @FXML
    void form_mode_OnAction(ActionEvent event) {

    }


}
