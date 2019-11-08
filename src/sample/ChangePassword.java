package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.image.SampleModel;
import java.io.IOException;
import java.net.URL;

public class ChangePassword extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
        primaryStage.setTitle("修改密码");
        primaryStage.setScene(new Scene(root, 639, 447));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }




}
