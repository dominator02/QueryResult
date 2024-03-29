package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.json.JSONObject;
import org.json.JSONException;

public class Controller implements Initializable {
    public PasswordField password;
    public TextField account;
    @FXML
    public Button login;
    public RadioButton adm;
    public RadioButton tea;
    @FXML
      ToggleGroup group=new ToggleGroup();
    @FXML
    private Button reset;
     @FXML
     private RadioButton stu;
     

    @FXML
    protected  void Toggle(ActionEvent event) throws IOException {

        String userText=account.getText();
        String passText=password.getText();
        String ID=group.getSelectedToggle().getUserData().toString();
        if(userText.equals("")||passText.equals(""))
        {
            alert("ERROR","错误！","账号或密码不能为空！！");

        }
        else {
            System.out.println(userText+"  "+passText);
            System.out.println(ID);
            if(ClientLogin(userText,passText,ID)==1)
            {
                System.out.println("登录成功");
            }
            else{
                password.setText("");
                account.setText("");
                alert("ERROR","错误！","用户名或密码错误！");

            }
        }



    }
    //弹出窗口
    public void alert(String title,String HeaderText,String ContentText){
        Alert _alert=new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle(title);
        _alert.setHeaderText(HeaderText);
        _alert.setContentText(ContentText);
        _alert.showAndWait();
    }

    public int ClientLogin(String user,String pass ,String id) throws IOException {


        Socket socket=new Socket("127.0.0.1",8888);
        OutputStream os=socket.getOutputStream();
//        os.write(("login#"+id+"#"+user+"#"+pass).getBytes());
        PrintWriter printWriter=new PrintWriter(os);
        JSONObject root =new JSONObject();
        try{
            root.put("operator","login");
            root.put("id",id);
            root.put("account",user);
            root.put("password",pass);

        }catch (JSONException e){
            e.printStackTrace();
        }
        printWriter.write(root.toString());
        printWriter.flush();
        socket.shutdownOutput();

        InputStream is=socket.getInputStream();
//        byte[] buf = new byte[1024];
//        int len = is.read(buf);
//        String str2 = new String(buf,0,len);
        String str2="";
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
        String lineMessage;
        while ((lineMessage=bufferedReader.readLine())!=null)
        {
            str2+=lineMessage;
        }

        is.close();
        os.close();

        socket.close();
        if(str2.equals("Failed"))
        {
            return 0;
        }
        else{
            return 1;
        }

    }
    public void initialize(URL url, ResourceBundle rb)
    {

    }


    public void Reset(ActionEvent actionEvent) {
        password.setText("");
        account.setText("");
    }

    public void Search(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Socket socket=new Socket("127.0.0.1",8888);
        OutputStream os=socket.getOutputStream();

        PrintWriter printWriter=new PrintWriter(os);
        JSONObject root =new JSONObject();
        try{
            root.put("operator","search");
//            root.put("id",group.getSelectedToggle().getUserData().toString());
//            root.put("account",account.getText());
           // root.put("password",pass);

        }catch (JSONException e){
            e.printStackTrace();
        }
        printWriter.write(root.toString());
        printWriter.flush();
        socket.shutdownOutput();
        ObjectInputStream in=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        ListTest test=(ListTest)in.readObject();
        List<Results> list =test.getList();
        for(Results results :list){
            System.out.println(results.getName());

        }
        in.close();
        os.close();
    }
}
