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
    for (int i = 0; i < args.length; i++) {
      if (args[i].contains("-size=")) {
        String size[] = args[i].split("=")[1].split("x");
        //Properties.WIDTH_SIZE = Integer.parseInt(size[0]);
        //Properties.HEIGHT_SIZE = Integer.parseInt(size[1]);
      }
    }
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
