package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController {

    @FXML
    private Button goback;

    @FXML
    void goback(ActionEvent event) throws Exception{
        Stage cstage=(Stage)goback.getScene().getWindow();
        cstage.close();
        Result re=new Result();
        Stage stage=new Stage();
        re.start(stage);
    }
    public void initialize(URL url, ResourceBundle rb)
    {

    }

}
