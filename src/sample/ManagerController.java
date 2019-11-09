package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController {
    public TableView managecontent;
    public TableColumn idcolumn;
    public TableColumn namecolumn;
    public TableColumn Chinesecolumn;
    public TableColumn Mathscolumn;
    public TableColumn Englishcolumn;
    private final ObservableList<Results> data= FXCollections.observableArrayList();
    public TableColumn mColumnSelect;

    public void initialize(URL url, ResourceBundle rb) throws IOException, ClassNotFoundException {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        Chinesecolumn.setCellValueFactory(new PropertyValueFactory<>("Chinese"));
        Mathscolumn.setCellValueFactory(new PropertyValueFactory<>("Maths"));
        Englishcolumn.setCellValueFactory(new PropertyValueFactory<>("English"));
        managecontent.setEditable(true);
        SocketSingleIntance socketSingleIntance = SocketSingleIntance.getSingleIntance();
        Socket socket = socketSingleIntance.getSocket();
        OutputStream os = socket.getOutputStream();

        PrintWriter printWriter = new PrintWriter(os);
        JSONObject root = new JSONObject();
        try {
            root.put("operator", "ready");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        printWriter.write(root.toString());
        printWriter.flush();
        socket.shutdownOutput();
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        ListTest test = (ListTest) in.readObject();
        List<Results> list = test.getList();
        for (Results results : list) {
            data.addAll(new Results(results.getId(), results.getName(), results.getChinese(), results.getEnglish(), results.getMath()));


        }
        in.close();
        os.close();
        managecontent.setItems(data);

    }
}

