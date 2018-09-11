package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

  public static Properties properties;

  public static void main(String[] args) {
    properties = new Properties();
    properties.parse(args);
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/main.fxml"));
    Parent root = loader.load();
    primaryStage.setTitle("Snake");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
