package sample;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LoginController {
        @FXML
        private RadioButton tea;
        @FXML
        private PasswordField password;
        @FXML
        private RadioButton mag;
        @FXML
        private RadioButton stu;
        @FXML
        private Button reset;
        @FXML
        private Button login;
        @FXML
        private TextField account;
        @FXML
        private ToggleGroup group;
        @FXML
        void login(ActionEvent event) throws Exception {
                Stage primarystage=(Stage)login.getScene().getWindow();
                primarystage.close();
                Result result=new Result();
                Stage stage=new Stage();
                result.start(stage);
        }
        @FXML
        void reset(ActionEvent event) {

        }



}

