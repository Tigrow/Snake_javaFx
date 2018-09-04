package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
  public static int HEIGHT_SIZE = 20;
  public static int WIDTH_SIZE = 20;

  public static void main(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].contains("-size=")) {
        String size[] = args[i].split("=")[1].split("x");
        WIDTH_SIZE = Integer.parseInt(size[0]);
        HEIGHT_SIZE = Integer.parseInt(size[1]);
      }
    }
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("resources/main.fxml"));
    primaryStage.setTitle("Snake");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
