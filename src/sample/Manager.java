package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.image.SampleModel;
import java.io.IOException;
import java.net.URL;

public class Manager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("manager.fxml"));
        primaryStage.setTitle("管理界面");
        primaryStage.setScene(new Scene(root, 639, 447));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }




}
