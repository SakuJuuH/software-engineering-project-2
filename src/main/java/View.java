import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/hello-view.fxml"));
			Parent root = fxmlLoader.load();

			HelloController controller = fxmlLoader.getController();

			Scene scene = new Scene(root);

			HelloController.initialize(stage);

			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
