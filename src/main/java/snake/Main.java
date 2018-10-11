package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import snake.controller.Controller;
import snake.view.MainView;

/**
 * Главный класс который запускает всю программу.
 */
public class Main extends Application {

  public static Properties properties;
  private static boolean problemProperties;

  /**
   * Инициализация программы.
   *
   * @param args - агрументы которые приходят в программу.
   */
  public static void main(String[] args) {
    properties = new Properties();
    problemProperties = properties.parse(args);
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/main.fxml"));
    Parent root = loader.load();
    MainView mainView = loader.getController();
    new Controller(mainView);
    if (problemProperties) {
      mainView.showError();
    }
    primaryStage.setTitle("Snake");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
