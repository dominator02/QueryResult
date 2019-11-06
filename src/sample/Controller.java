package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public PasswordField pass;
    public TextField user;
    @FXML
    public Button logup;
    @FXML
    private Button login;

    @FXML
    protected  void setLogin(ActionEvent event) throws IOException {

        String userText=user.getText();
        String passText=pass.getText();
        System.out.println(userText+"  "+passText);
        if(ClientLogin(userText,passText)==1)
        {
            System.out.println("登录成功");
        }
        else{
            System.out.println("用户名或密码错误");
        }


    }
    public int ClientLogin(String user,String pass) throws IOException {
        Socket socket=new Socket("127.0.0.1",8888);
        OutputStream os=socket.getOutputStream();
        os.write((user+" "+pass).getBytes());
//        OutputStream out=socket.getOutputStream();
//        ObjectOutputStream oos=new ObjectOutputStream(out);
//        Password password=new Password(user, pass);
//        oos.writeObject(password);


        InputStream is=socket.getInputStream();
        byte[] buf = new byte[1024];
        int len = is.read(buf);
        String str2 = new String(buf,0,len);
        is.close();
        os.close();

        socket.close();
        if(str2.equals("ok"))
        {
            return 1;
        }
        else{
            return 0;
        }

    }
    public void initialize(URL url, ResourceBundle rb)
    {

    }


}
