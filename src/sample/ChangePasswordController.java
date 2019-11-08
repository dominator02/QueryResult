package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

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

}
