package startFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Mess management");
        primaryStage.setScene(new Scene(root, 1150, 758));
        primaryStage.maxHeightProperty().setValue(758);
        primaryStage.maxWidthProperty().setValue(1150);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
