import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TranslationDAO {

	public void add(TranslationDTO translationDTO) {
		try (Connection conn = MariaDbConnection.getConnection()) {
			String query = "INSERT INTO localization (key_name, language_code, translation_text) VALUES (?, ?, ?)";
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, translationDTO.keyCode());
				stmt.setString(2, translationDTO.languageCode());
				stmt.setString(3, translationDTO.translationText());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<TranslationDTO> getTranslationsByLanguage(String languageCode) {
		try (Connection conn = MariaDbConnection.getConnection()) {
			String query = "SELECT * FROM localization WHERE language_code = ?";
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, languageCode);
				ResultSet rs = stmt.executeQuery();
				List<TranslationDTO> translations = new ArrayList<>();
				while (rs.next()) {
					translations.add(new TranslationDTO(rs.getString("key_name"),
					                                    rs.getString("language_code"),
					                                    rs.getString("translation_text")));
				}
				return translations;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}
