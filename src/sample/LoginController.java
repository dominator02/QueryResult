package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

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
        public String getAccount(){
                return account.getText();
        }
        @FXML
        void login(ActionEvent event) throws Exception {
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
                                //System.out.println("登录成功");
                                Stage primarystage=(Stage)login.getScene().getWindow();
                                primarystage.close();
                                Result result=new Result();
                                Stage stage=new Stage();
                                result.start(stage);
                        }else if(ClientLogin(userText,passText,ID)==3){
                                Stage secondstage=(Stage)login.getScene().getWindow();
                                secondstage.close();
                                Manager manager=new Manager();
                                Stage stage1=new Stage();
                                manager.start(stage1);
                        }
                        else {
                                password.setText("");
                                account.setText("");
                                alert("ERROR","错误！","用户名或密码错误！");

                        }
                }



        }

        /**
         *
         * @param event
         */
        @FXML
        void reset(ActionEvent event) {
                password.setText("");
                account.setText("");

        }
        public void alert(String title,String HeaderText,String ContentText){
                Alert _alert=new Alert(Alert.AlertType.INFORMATION);
                _alert.setTitle(title);
                _alert.setHeaderText(HeaderText);
                _alert.setContentText(ContentText);
                _alert.showAndWait();
        }
        public int ClientLogin(String user,String pass ,String id) throws IOException {


                SocketSingleIntance socketSingleIntance=SocketSingleIntance.getSingleIntance();
                Socket socket= socketSingleIntance.getSocket();
                OutputStream os=socket.getOutputStream();

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

                String statue="";
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String lineMessage;
                while ((lineMessage=bufferedReader.readLine())!=null)
                {
                        statue+=lineMessage;
                }

                is.close();
                os.close();

                //socket.close();
                if(statue.equals("Failed"))
                {

                        return 0;
                }
                else if(statue.equals("OKstudent")){
                        return 1;
                }else  if(statue.equals("OKteacher")){
                        return 2;
                }else if(statue.equals("OKadmin")){
                        return 3;
                }
                return 0;

        }

        public void initialize(URL url, ResourceBundle rb)
        {

        }



}

