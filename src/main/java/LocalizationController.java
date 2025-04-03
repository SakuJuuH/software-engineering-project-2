import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;

public class LocalizationController {

	private final static TranslationDAO translationDAO = new TranslationDAO();
	private Locale currentLocale;

	@FXML
	public ComboBox<String> languageComboBox;
	@FXML
	public Label titleLabel;
	@FXML
	public ListView<String> translationListView;
	@FXML
	public TextField keyTextField;
	@FXML
	public TextField translationTextField;
	@FXML
	public Button button;

	public static void initialize(Stage stage) {
		try {
			stage.setTitle("Employees job titles");
			stage.setResizable(true);
			stage.centerOnScreen();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	@FXML
	private void initialize() {
		currentLocale = new Locale("en", "US");
		setLanguage(currentLocale);

		keyTextField.setText("");
		translationTextField.setText("");

		if (translationListView == null) {
			translationListView = new ListView<>();
		}

		fetchData();
	}

	public void setLanguage(Locale locale) {
		this.currentLocale = locale;
		ResourceBundle languages;
		try {
			languages = ResourceBundle.getBundle("languages", locale);

			if(languageComboBox.getItems().isEmpty()) {
				List<Locale> availableLocales = getAvailableLocales();
				for (Locale l : availableLocales) {
					languageComboBox.getItems().add(l.getDisplayLanguage());
				}
				languageComboBox.getSelectionModel().select(locale.getDisplayLanguage());
			}

			titleLabel.setText(languages.getString("title"));
			keyTextField.setPromptText(languages.getString("key.prompt"));
			translationTextField.setPromptText(languages.getString("translation.prompt"));
			button.setText(languages.getString("button.text"));

		} catch (MissingResourceException e) {
			System.out.println("Error: " + e.getMessage());

		}
	}

	public void persistTranslation(ActionEvent actionEvent) {
		String key = keyTextField.getText();
		String language = currentLocale.getLanguage();
		String translation = translationTextField.getText();

		if (key.isEmpty() || translation.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid key");
			alert.showAndWait();
			return;
		}

		TranslationDTO translationDTO = new TranslationDTO(key, language, translation);

		translationDAO.add(translationDTO);

		keyTextField.clear();
		translationTextField.clear();

		fetchData();
	}

	public void fetchData() {
		String language = currentLocale.getLanguage();
		translationListView.getItems().clear();

			List<TranslationDTO> translations = translationDAO.getTranslationsByLanguage(language);

		if (translations != null) {
			for (TranslationDTO translationDTO : translations) {
				translationListView.getItems().add(
						translationDTO.keyCode() + ": " + translationDTO.translationText()
				);
			}
		}
	}

	private List<Locale> getAvailableLocales() {
		return List.of(
				new Locale.Builder().setLanguage("en").setRegion("US").build(),
				new Locale.Builder().setLanguage("es").setRegion("ES").build(),
				new Locale.Builder().setLanguage("fr").setRegion("FR").build(),
				new Locale.Builder().setLanguage("zh").setRegion("CN").build()
		);
	}

	public void onLanguageSelected(ActionEvent actionEvent) {
		String selectedLanguage = languageComboBox.getSelectionModel().getSelectedItem();
		if (selectedLanguage != null) {
			Locale newLocale = null;
			for (Locale l : getAvailableLocales()) {
				if (l.getDisplayLanguage().equals(selectedLanguage)) {
					newLocale = l;
					break;
				}
			}

			if (newLocale != null) {
				setLanguage(newLocale);
			}

			fetchData();
		}
	}
}
