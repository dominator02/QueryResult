package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultController {

    public Label lable;
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
    public void initialize(URL url, ResourceBundle rb)
    {


    }

}
