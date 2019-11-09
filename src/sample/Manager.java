package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Manager extends Application {
    private final ObservableList<Results> data= FXCollections.observableArrayList(

    );
    private final TableView<Results> table=new TableView<>();
    final HBox hBox=new HBox();
    final HBox hBox1=new HBox();

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("manager.fxml"));
//        primaryStage.setTitle("管理界面");
//        primaryStage.setScene(new Scene(root, 639, 447));
//        primaryStage.show();
        Scene scene=new Scene(new Group());
        primaryStage.setTitle("成绩结果:");
        primaryStage.setWidth(500);
        primaryStage.setHeight(550);
        final Label label=new Label("你好，至高无上的管理员");
        label.setFont(new Font("Arial",18));

        table.setEditable(true);
        TableColumn tableColumnId=new TableColumn("ID");
        TableColumn tableColumnName=new TableColumn("Name");
        TableColumn tableColumnChinese=new TableColumn("Chinese");
        TableColumn tableColumnMaths=new TableColumn("Maths");
        TableColumn tableColumnEnglish=new TableColumn("English");
        TableColumn tableColumnDelete=new TableColumn("");
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
        tableColumnDelete.setMinWidth(50);
        tableColumnName.setCellFactory(TextFieldTableCell.<Results>forTableColumn());
        tableColumnName.setEditable(true);
        tableColumnName.setOnEditCommit(new EventHandler<CellEditEvent<Results,String >>() {
            @Override
            public void handle(CellEditEvent<Results,String> t) {
                ((Results) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
                //List<Results> list =data;
                for(Results results:data){
                    System.out.println(results.getName());
                }

            }
        });
        tableColumnChinese.setCellFactory(new Callback<TableColumn<Results,Integer>, TableCell<Results,Integer>>() {
            @Override
            public TableCell<Results,Integer> call(TableColumn<Results,Integer> tableColumn) {
                TextFieldTableCell<Results,Integer> cell=new TextFieldTableCell<>(new IntegerStringConverter());

                return cell;
            }
        });
        tableColumnChinese.setEditable(true);
        tableColumnChinese.setOnEditCommit(new EventHandler<CellEditEvent<Results,String >>() {
            @Override
            public void handle(CellEditEvent<Results,String> t) {
                ((Results) t.getTableView().getItems().get(t.getTablePosition().getRow())).setChinese(Integer.parseInt(t.getNewValue()));
            }
        });
        tableColumnEnglish.setCellFactory(new Callback<TableColumn<Results,Integer>, TableCell<Results,Integer>>() {
            @Override
            public TableCell<Results,Integer> call(TableColumn<Results,Integer> tableColumn) {
                TextFieldTableCell<Results,Integer> cell=new TextFieldTableCell<>(new IntegerStringConverter());

                return cell;
            }
        });
        tableColumnEnglish.setEditable(true);
        tableColumnEnglish.setOnEditCommit(new EventHandler<CellEditEvent<Results,String >>() {
            @Override
            public void handle(CellEditEvent<Results,String> t) {
                ((Results) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEnglish(Integer.parseInt(t.getNewValue()));
            }
        });
        tableColumnMaths.setCellFactory(new Callback<TableColumn<Results,Integer>, TableCell<Results,Integer>>() {
            @Override
            public TableCell<Results,Integer> call(TableColumn<Results,Integer> tableColumn) {
                TextFieldTableCell<Results,Integer> cell=new TextFieldTableCell<>(new IntegerStringConverter());

                return cell;
            }
        });
        tableColumnMaths.setEditable(true);
        tableColumnMaths.setOnEditCommit(new EventHandler<CellEditEvent<Results,String >>() {
            @Override
            public void handle(CellEditEvent<Results,String> t) {
                ((Results) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMath(Integer.parseInt(t.getNewValue()));

            }
        });
        tableColumnDelete.setCellFactory((tableColumn -> {
            TableCell<Results,String> cell=new TableCell<>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if(!empty){
                        Button deleteButton=new Button("删除");
                        this.setGraphic(deleteButton);
                        deleteButton.setOnMouseClicked(mouseEvent -> {
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("DeleteConfirm");
                            alert.setHeaderText("请确认");
                            alert.setContentText("您确定要删除此学生信息吗？");
                            Optional result=alert.showAndWait();
                            if(result.get()==ButtonType.OK){
                                Results results=this.getTableView().getItems().get(this.getIndex());
                                data.remove(results);
                            }


                        });
                    }
                }
            };

            return cell;
        }));

        loadData();
        table.setItems(data);
        table.getColumns().addAll(tableColumnId,tableColumnName,tableColumnChinese,tableColumnEnglish,tableColumnMaths,tableColumnDelete);

        final TextField addID =new TextField();
        addID.setPromptText("ID");
        addID.setPrefWidth(50);
        final TextField addName=new TextField();
        addName.setPromptText("Name");
        addName.setPrefWidth(100);
        final TextField addChinese=new TextField();
        addChinese.setPromptText("Chinese");
        addChinese.setPrefWidth(50);
        final TextField addEnglish=new TextField();
        addEnglish.setPromptText("English");
        addEnglish.setPrefWidth(50);
        final TextField addMaths=new TextField();
        addMaths.setPromptText("Maths");
        addMaths.setPrefWidth(50);
        final Button  addButton=new Button("添加记录");
        addButton.setOnAction((ActionEvent e)->{

            if(addID.getText().equals("")||addName.getText().equals("")||addChinese.getText().equals("")||addEnglish.getText().equals("")||addMaths.getText().equals(""))
                alert("ERROR","错误！","内容不能为空！！");

            else {
                int flag=0;
                for (Results results :data){
                    if(Integer.parseInt(addID.getText())==results.getId()){
                        flag=1;
                    }
                }
                if(flag==1){
                    alert("Error","ID错误","已存在此ID，请重新输入此ID");
                    addID.clear();
                }else {
                    data.add(new Results(Integer.parseInt(addID.getText()),
                            addName.getText(),
                            Integer.parseInt(addChinese.getText()),
                            Integer.parseInt(addEnglish.getText()),
                            Integer.parseInt(addMaths.getText())));
                    addID.clear();
                    addName.clear();
                    addChinese.clear();
                    addEnglish.clear();
                    addMaths.clear();
                }

            }



        });
        final Button submit=new Button("提交记录");
        submit.setOnAction((ActionEvent e)->{
            SocketSingleIntance socketSingleIntance= null;

            try {
                socketSingleIntance = SocketSingleIntance.getSingleIntance();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Socket socket=socketSingleIntance.getSocket();
            OutputStream os= null;
            try {
                os = socket.getOutputStream();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            PrintWriter printWriter=new PrintWriter(os);
            JSONObject root =new JSONObject();
            try{
                root.put("operator","upload");


            }catch (JSONException ex){
                ex.printStackTrace();
            }
            printWriter.println(root.toString());
            printWriter.flush();

            InputStream is= null;
            try {
                is = socket.getInputStream();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String statue="";
            String lineMessage="";
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            try {
                lineMessage=bufferedReader.readLine();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            statue=lineMessage;
            if(statue.equals("ready")){
                ObjectOutputStream objectOutputStream=null;
                try {
                    objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ListTest listTest=new ListTest();
                List<Results> list=new ArrayList<>();
                for(Results results:data)
                {
                    list.add(results);
                }
                listTest.setList(list);
                try {
                    objectOutputStream.writeObject(listTest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    objectOutputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });



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
        hBox1.getChildren().addAll(addID,addName,addChinese,addEnglish,addMaths,addButton,submit);
        hBox1.setSpacing(3);

        final VBox vBox=new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10,0,0,10));
        vBox.getChildren().addAll(label,table,hBox1,hBox);
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
            root.put("operator","adminsearch");
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
    public void alert(String title,String HeaderText,String ContentText){
        Alert _alert=new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle(title);
        _alert.setHeaderText(HeaderText);
        _alert.setContentText(ContentText);
        _alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }




}
