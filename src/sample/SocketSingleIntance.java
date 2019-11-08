package sample;


import java.io.IOException;
import java.net.Socket;

//单例类：
public class SocketSingleIntance {
    private Socket socket;
    private static SocketSingleIntance  socketSingleIntance ;
    private SocketSingleIntance () throws IOException {
        this.socket=new Socket("127.0.0.1",8888);//创建socket连接
    }
    public static SocketSingleIntance  getSingleIntance() throws IOException {
        if(null==socketSingleIntance ){
            synchronized (SocketSingleIntance.class){
                if (socketSingleIntance==null)
                    socketSingleIntance =new SocketSingleIntance ();
            }

        }
        return  socketSingleIntance ;
    }
    //对外提供访问
    public Socket getSocket(){
        return this.socket;
    }
    public void Close() throws IOException {
        if(socket != null && socket.isConnected())
            this.socket.close();
    }
}



