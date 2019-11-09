package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController {

    public PasswordField oldpassword;
    public PasswordField newpassword;
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
    public void initialize(URL url, ResourceBundle rb)  {


    }

    public void confirmmodify(ActionEvent actionEvent) throws Exception {
        String op=oldpassword.getText();
        String np=newpassword.getText();
        if(op.equals("")||np.equals(""))
        {
            alert("ERROR","错误！","密码不能为空！！");
            oldpassword.setText("");
            newpassword.setText("");
        }else {
            function(op,np);
        }

    }
    public void function(String op,String np) throws Exception {
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
//        String op=oldpassword.getText();
//        String np=newpassword.getText();

        PrintWriter printWriter=new PrintWriter(os);
        JSONObject root =new JSONObject();
        try{
            root.put("operator","changepassword");
            root.put("password",op);
            root.put("newpassword",np);


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
        InputStream is=socket.getInputStream();

        String statue="";
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
        String lineMessage=bufferedReader.readLine();
//        while ((lineMessage=bufferedReader.readLine())!=null)
//        {
//            statue+=lineMessage;
//        }
        statue=lineMessage;
        if(statue.equals("Failed"))
        {
            alert("ERROR","错误！","输入的原密码错误，请重试！");
            oldpassword.setText("");
            newpassword.setText("");
        }
        else {
            alert("success","修改成功！","修改成功！！！");
            Stage cstage=(Stage)goback.getScene().getWindow();
            cstage.close();
            Result re=new Result();
            Stage stage=new Stage();
            re.start(stage);
        }
    }
    public void alert(String title,String HeaderText,String ContentText){
        Alert _alert=new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle(title);
        _alert.setHeaderText(HeaderText);
        _alert.setContentText(ContentText);
        _alert.showAndWait();
    }
}
