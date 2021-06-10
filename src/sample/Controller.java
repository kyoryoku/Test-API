package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

public class Controller {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private Button form_browseApiFile;
    @FXML private TextField form_apiFile;
    @FXML private TextField form_ipAddress;
    @FXML private TextField form_port;
    @FXML private ListView<Api> form_availableApi;
    @FXML private ListView<Api> form_selectedApi;
    @FXML private Button form_run;
    @FXML private TextField form_pause;
    @FXML private ToggleButton form_mode;
    @FXML private TextArea form_results;
    @FXML private Button form_clear;
    @FXML private CheckBox form_autoClear;
    @FXML private Button form_details;
    @FXML private Button form_add;
    @FXML private Button form_remove;

    private ObservableList<Api> availableApi = FXCollections.observableArrayList();
    private ObservableList<Api> selectedApi = FXCollections.observableArrayList();

    private SimpleStringProperty ipAddress = new SimpleStringProperty("127.0.0.1");
    private SimpleStringProperty port = new SimpleStringProperty("8084");
    private SimpleStringProperty apiFile = new SimpleStringProperty("D:\\testApi.csv");
    private SimpleStringProperty pause = new SimpleStringProperty("2000");
    private SimpleStringProperty results = new SimpleStringProperty();
    private SimpleStringProperty modeLabel = new SimpleStringProperty("Ручной режим");
    private SimpleBooleanProperty mode = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty autoClear = new SimpleBooleanProperty(true);

    private ArrayList<String> strings = new ArrayList<>();

    @FXML
    public void initialize() {
        form_apiFile.textProperty().bindBidirectional(apiFile);
        form_ipAddress.textProperty().bindBidirectional(ipAddress);
        form_port.textProperty().bindBidirectional(port);
        form_results.textProperty().bindBidirectional(results);
        form_pause.textProperty().bindBidirectional(pause);
        form_mode.textProperty().bindBidirectional(modeLabel);
        form_mode.selectedProperty().bindBidirectional(mode);
        form_pause.visibleProperty().bind(form_mode.selectedProperty().not());

        form_availableApi.setItems(availableApi);
        form_availableApi.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        form_selectedApi.setItems(selectedApi);
        form_selectedApi.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        form_autoClear.selectedProperty().bindBidirectional(autoClear);



    }

    @FXML
    void form_run_OnAction(ActionEvent event) {

    }

    @FXML
    void form_browseApiFile_OnAction(ActionEvent event) {
        //выбираем файл через мастер
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите csv-файл с апи...");
        fileChooser.setInitialDirectory(new File("C:\\Users\\dshul\\Desktop\\Тестирование КП"));
        File file = fileChooser.showOpenDialog(form_browseApiFile.getScene().getWindow());
        if (file != null){
            apiFile.set(file.getAbsolutePath());
        }

        //загружаем данные из файла
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(apiFile.get()));
            Scanner scanner = new Scanner(bufferedReader);
            while (true) {
                if (!scanner.hasNext()) {
                    break;
                }
                strings.add(scanner.nextLine());
            }
            scanner.close();
            bufferedReader.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //парсим строки и заполняем список availableApi
        for (String str : strings){
            String[] strs = str.split(";", -1);
            availableApi.add(new Api(
                    strs[0].replaceAll("\\(", "").replaceAll("\\)", ""),
                    strs[1],
                    strs[2],
                    strs[3]
            ));
        }

    }


    @FXML
    void form_add_OnAction(ActionEvent event) {
        selectedApi.addAll(form_availableApi.getSelectionModel().getSelectedItems());
    }

    @FXML
    void form_remove_OnAction(ActionEvent event) {
        selectedApi.removeAll(form_selectedApi.getSelectionModel().getSelectedItems());
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
