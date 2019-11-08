package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class ResultController {

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
    void out(ActionEvent event){
        Stage cu=(Stage)out.getScene().getWindow();
        cu.close();
    }

}
