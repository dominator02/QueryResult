package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultController {


    private TableColumn<Results, Integer> id;
    public TableColumn<Results,String> name;
    public TableColumn<Results, Integer> Chinese;
    public TableColumn<Results, Integer> Maths;
    public TableColumn<Results, Integer> English;
    public TableView gradesresult;
    private final ObservableList<Results> data= FXCollections.observableArrayList();
    @FXML
    private Button modify;
    @FXML
    private Button out;
    @FXML
    void modify(ActionEvent event) throws Exception {
        Stage cstage=(Stage)modify.getScene().getWindow();
        cstage.close();
        ChangePassword cp=new ChangePassword();
        Stage stage=new Stage();
        cp.start(stage);

    }
    @FXML
    void out(ActionEvent event) throws IOException {
        Stage cu=(Stage)out.getScene().getWindow();
        SocketSingleIntance socketSingleIntance=SocketSingleIntance.getSingleIntance();
        socketSingleIntance.Close();
        cu.close();
        System.exit(0);
    }
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<Results, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Chinese.setCellValueFactory(new PropertyValueFactory<>("Chinese"));
        Maths.setCellValueFactory(new PropertyValueFactory<>("Maths"));
        English.setCellValueFactory(new PropertyValueFactory<>("English"));

        gradesresult.setEditable(true);
        SocketSingleIntance socketSingleIntance= null;
        try {
            socketSingleIntance = SocketSingleIntance.getSingleIntance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket socket=socketSingleIntance.getSocket();
        OutputStream os= null;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter=new PrintWriter(os);
        JSONObject root =new JSONObject();
        try{
            root.put("operator","ready");


        }catch (JSONException e){
            e.printStackTrace();
        }
        printWriter.println(root.toString());
        printWriter.flush();
//        try {
//            socket.shutdownOutput();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ObjectInputStream in= null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListTest test= null;
        try {
            test = (ListTest)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Results> list =test.getList();
        for(Results results :list){
            data.addAll(new Results(results.getId(),results.getName(),results.getChinese(),results.getEnglish(),results.getMath()));


        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gradesresult.setItems(data);
    }


    

}
