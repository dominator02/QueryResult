package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.*;
import java.net.Socket;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class Result extends Application {
    private final ObservableList<Results> data= FXCollections.observableArrayList(

    );
    private final TableView<Results> table=new TableView<>();
    final HBox hBox=new HBox();
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("result.fxml"));
//        primaryStage.setTitle("成绩结果");
//        primaryStage.setScene(new Scene(root, 639, 447));
//        primaryStage.show();
        Scene scene=new Scene(new Group());
        primaryStage.setTitle("成绩结果:");
        primaryStage.setWidth(500);
        primaryStage.setHeight(550);
        final Label label=new Label("你的成绩如下：");
        label.setFont(new Font("Arial",18));

        table.setEditable(true);
        TableColumn tableColumnId=new TableColumn("ID");
        TableColumn tableColumnName=new TableColumn("Name");
        TableColumn tableColumnChinese=new TableColumn("Chinese");
        TableColumn tableColumnMaths=new TableColumn("Maths");
        TableColumn tableColumnEnglish=new TableColumn("English");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnChinese.setCellValueFactory(new  PropertyValueFactory<>("Chinese"));
        tableColumnMaths.setCellValueFactory(new PropertyValueFactory<>("Math"));
        tableColumnEnglish.setCellValueFactory(new PropertyValueFactory<>("English"));
        tableColumnId.setMinWidth(50);
        tableColumnName.setMinWidth(100);
        tableColumnChinese.setMinWidth(50);
        tableColumnEnglish.setMinWidth(50);
        tableColumnMaths.setMinWidth(50);
        loadData();
        table.setItems(data);
        table.getColumns().addAll(tableColumnId,tableColumnName,tableColumnChinese,tableColumnEnglish,tableColumnMaths);

        final Button modifyButton=new Button("修改密码");
        final Button logoutButton=new Button("退出");
        modifyButton.setOnAction((ActionEvent e)->{
            Stage cstage=(Stage)modifyButton.getScene().getWindow();
            cstage.close();
            ChangePassword cp=new ChangePassword();
            Stage stage=new Stage();
            try {
                cp.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        logoutButton.setOnAction((ActionEvent e)->{
            Stage cu=(Stage)logoutButton.getScene().getWindow();

            cu.close();
            System.exit(0);
        });
        hBox.getChildren().addAll(modifyButton,logoutButton);
        hBox.setSpacing(10);

        final VBox vBox=new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10,0,0,10));
        vBox.getChildren().addAll(label,table,hBox);
        ((Group)scene.getRoot()).getChildren().addAll(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    public void loadData() throws IOException, ClassNotFoundException {
        SocketSingleIntance socketSingleIntance= null;
        try {
            socketSingleIntance = SocketSingleIntance.getSingleIntance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket socket=socketSingleIntance.getSocket();
        OutputStream os=socket.getOutputStream();

        PrintWriter printWriter=new PrintWriter(os);
        JSONObject root =new JSONObject();
        try{
            root.put("operator","studentsearch");
//            root.put("id",group.getSelectedToggle().getUserData().toString());
//            root.put("account",account.getText());
            // root.put("password",pass);

        }catch (JSONException e){
            e.printStackTrace();
        }
        printWriter.println(root.toString());
        printWriter.flush();

        ObjectInputStream in=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        ListTest test=(ListTest)in.readObject();
        List<Results> list =test.getList();
        for(Results results :list){
            System.out.println(results.getName());
            data.addAll(new Results(results.getId(),results.getName(),results.getChinese(),results.getEnglish(),results.getMath()));

        }
    }
    public static void main(String[] args) {
        launch(args);
    }




}
