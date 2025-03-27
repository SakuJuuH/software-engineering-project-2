import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class HelloController {
	@FXML
	public Label lblDistance;
	@FXML
	public TextField txtDistance;
	@FXML
	public Label lblFuel;
	@FXML
	public TextField txtFuel;
	@FXML
	public Button btnCalculate;
	@FXML
	public Label lblResult;
	@FXML
	public Label lblAuthor;

	private Locale currentLocale;

	public static void initialize(Stage stage) {
		try {
			stage.setTitle("Fuel Consumption Calculator");

			stage.setResizable(true);
			stage.centerOnScreen();
		} catch (Exception e) {
			System.err.println("Error initializing application: " + e.getMessage());
		}
	}

	@FXML
	public void initialize() {
		currentLocale = new Locale("en", "US");
		setLanguage(currentLocale);

		lblResult.setText("");
	}


	private void setLanguage(Locale locale) {
		this.currentLocale = locale;
		ResourceBundle rb;

		try {
			rb = ResourceBundle.getBundle("messages", locale);
			lblAuthor.setText(rb.getString("author.label"));
			lblDistance.setText(rb.getString("distance.label"));
			lblFuel.setText(rb.getString("fuel.label"));
			btnCalculate.setText(rb.getString("calculate.button"));
			lblResult.setText(rb.getString("result.default"));
		} catch (MissingResourceException e) {
			System.out.println(e.getMessage());
			lblResult.setText("Error loading resource bundle");
		}
	}


	public void onCalculateClick(ActionEvent actionEvent) {
		try {
			double distance = Double.parseDouble(txtDistance.getText());
			double fuel = Double.parseDouble(txtFuel.getText());

			if (distance <= 0 || fuel <= 0) {
				setError(currentLocale);
				return;
			}

			double result = (fuel / distance) * 100;
			DecimalFormat df = new DecimalFormat("0.00");

			ResourceBundle rb = ResourceBundle.getBundle("messages", currentLocale);
			String resultPattern = rb.getString("result.label");
			String formattedResult = resultPattern.replace("{0}", df.format(result));

			lblResult.setText(formattedResult);
		} catch (NumberFormatException e) {
			setError(currentLocale);
		}
	}

	private void setError(Locale locale) {
		ResourceBundle rb;
		try {
			rb = ResourceBundle.getBundle("messages", locale);
			lblResult.setText(rb.getString("invalid.input"));
		} catch (MissingResourceException e) {
			System.out.println(e.getMessage());
			lblResult.setText("Error: Invalid input");
		}
	}
	public void onENClick(ActionEvent actionEvent) {
		setLanguage(new Locale("en", "US"));
	}

	public void onFRClick(ActionEvent actionEvent) {
		setLanguage(new Locale("fr", "FR"));
	}

	public void onJPClick(ActionEvent actionEvent) {
		setLanguage(new Locale("ja", "JP"));
	}

	public void onIRClick(ActionEvent actionEvent) {
		setLanguage(new Locale("fa", "IR"));
	}
}
