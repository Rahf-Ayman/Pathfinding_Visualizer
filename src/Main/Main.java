package Main;

import Main.Configurations.Constants;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Pathfinding Visualizer");
        Scene mainScene = new Scene(root, 800, 625);
        primaryStage.setScene(mainScene);
        primaryStage.getIcons().add(new Image("/Icons/version.png"));
        Constants.stopButton = (JFXButton) mainScene.lookup("#stopButton");
        

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
