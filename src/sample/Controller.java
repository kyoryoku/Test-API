package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    @FXML private Button btnBrowse;
    @FXML private TextField tfApiFile;
    @FXML private TextField tfIpAddress;
    @FXML private TextField tfPort;
    @FXML private ListView<Api> lvAvailableApi;
    @FXML private ListView<Api> lvSelectedApi;
    @FXML private Button btnRun;
    @FXML private TextField tfPause;
    @FXML private Label lblPause;
    @FXML private ToggleButton tglBtnMode;
    @FXML private TextArea taResults;
    @FXML private Button btnClear;
    @FXML private CheckBox chbAutoClear;
    @FXML private Button btnDetails;
    @FXML private Button btnAdd;
    @FXML private Button btnRemove;

    private ObservableList<Api> availableApi = FXCollections.observableArrayList();
    private ObservableList<Api> selectedApi = FXCollections.observableArrayList();

    private SimpleStringProperty ipAddress = new SimpleStringProperty("127.0.0.1");
    private SimpleStringProperty port = new SimpleStringProperty("8084");
    private SimpleStringProperty apiFile = new SimpleStringProperty("D:\\testApi.csv");
    private SimpleStringProperty pause = new SimpleStringProperty("2000");
    private SimpleStringProperty results = new SimpleStringProperty();
    private SimpleStringProperty modeLabel = new SimpleStringProperty("Включить ручной режим");
    private SimpleBooleanProperty mode = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty autoClear = new SimpleBooleanProperty(true);


    private ArrayList<String> strings = new ArrayList<>();

    @FXML
    public void initialize() {
        tfApiFile.textProperty().bindBidirectional(apiFile);
        tfIpAddress.textProperty().bindBidirectional(ipAddress);
        tfPort.textProperty().bindBidirectional(port);
        taResults.textProperty().bindBidirectional(results);
        tfPause.textProperty().bindBidirectional(pause);
        tglBtnMode.textProperty().bindBidirectional(modeLabel);
        tglBtnMode.selectedProperty().bindBidirectional(mode);
        tfPause.visibleProperty().bind(tglBtnMode.selectedProperty().not());
        lblPause.visibleProperty().bind(tglBtnMode.selectedProperty().not());

        lvAvailableApi.setItems(availableApi);
        lvAvailableApi.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvSelectedApi.setItems(selectedApi);
        lvSelectedApi.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        chbAutoClear.selectedProperty().bindBidirectional(autoClear);
    }

    @FXML
    void btnRun_OnAction(ActionEvent event) throws IOException, InterruptedException {
        if (chbAutoClear.isSelected()){
            taResults.clear();
        }
        results.set(results.get() + "Начинаем отправлять запросы!" + "\n");


        //mode = false - авто
        //mode = true  - ручной
        if (mode.get()){
            Api api = lvSelectedApi.getSelectionModel().getSelectedItem();
                results.set(results.get() + "Отправка " + api.getId() + " " + api.getName() + "\n");
                results.set(results.get() + sendRequest(api));
                results.set(results.get() + "--------------------" + "\n");

        } else {
            btnRun.setDisable(true);
            for(Api api : lvSelectedApi.getItems()){
                results.set(results.get() + "Отправка " + api.getId() + " " + api.getName() + "\n");
                results.set(results.get() + sendRequest(api));
                results.set(results.get() + "--------------------" + "\n");
                Thread.sleep(Integer.parseInt(pause.get()));
            }
            btnRun.setDisable(false);
        }


        results.set(results.get() + "Все запросы отправлены!" + "\n");
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String sendRequest(Api api) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(api.getData()))
                .uri(URI.create("http://127.0.0.1:8084" + api.getUrl()))
                .setHeader("User-Agent", "HttpClient")
                .header("Content-Type", "application/json")
                .build();

        String res = "";

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            res = "response code: " + response.statusCode() + "\n" +
                  "response body: " + "\n" + response.body() + "\n";
        } catch (IOException | InterruptedException e) {
            res = "Ошибка! " + e.getMessage();
        }
        return res;
    }

    @FXML
    void btnBrowse_OnAction(ActionEvent event) {
        //выбираем файл через мастер
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите csv-файл с апи...");
        fileChooser.setInitialDirectory(new File("C:\\Users\\dshul\\Desktop\\Тестирование КП"));
        File file = fileChooser.showOpenDialog(btnBrowse.getScene().getWindow());
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
    void btnAdd_OnAction(ActionEvent event) {
        selectedApi.addAll(lvAvailableApi.getSelectionModel().getSelectedItems());
    }

    @FXML
    void btnRemove_OnAction(ActionEvent event) {
        selectedApi.removeAll(lvSelectedApi.getSelectionModel().getSelectedItems());
    }

    @FXML
    void btnDetails_OnAction(ActionEvent event) {

    }

    @FXML
    void btnClear_OnAction(ActionEvent event) {
        taResults.clear();
    }

    @FXML
    void btnMode_OnAction(ActionEvent event) {
        if(mode.get()){
            tglBtnMode.setText("Отключить ручной режим");
        } else {
            tglBtnMode.setText("Включить ручной режим");
        }

    }


}
