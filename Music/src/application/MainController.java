package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private AnchorPane paneMusic;

	@FXML
	private AnchorPane pane_playlistMenu;

	@FXML
	private JFXButton playButton;

	@FXML
	private JFXButton nextButton;

	@FXML
	private JFXButton prevButton;

	@FXML
	private JFXButton addButton;

	@FXML
	private JFXButton removeButton;

	@FXML
	private JFXButton importButton;

	@FXML
	private JFXButton exportButton;

	@FXML
	private JFXButton btn_minimize;

	@FXML
	private JFXButton playlistMenu;

	@FXML
	private JFXButton btn_exit;

	@FXML
	private JFXButton btn_exit_playlist;

	@FXML
	private JFXSlider slider;

	@FXML
	private Label nowPlaying;

	@FXML
	private Label music_now;

	@FXML
	private Label music_time;

	@FXML
	private void handleButtonAction(MouseEvent event) {
		if (event.getSource() == btn_exit) {
			System.exit(0);
		} else if (event.getSource() == playlistMenu && pane_playlistMenu.isVisible() == false) {
			pane_playlistMenu.setVisible(true);
		} else if (event.getSource() == playlistMenu && pane_playlistMenu.isVisible() == true)
			pane_playlistMenu.setVisible(false);
		if (event.getSource() == btn_exit_playlist) {
			pane_playlistMenu.setVisible(false);
		}
		if (event.getSource() == btn_minimize) {
			Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		}
		if (event.getSource() == playButton) {

		}
		if (event.getSource() == nextButton) {

		}
		if (event.getSource() == prevButton) {

		}
		if (event.getSource() == addButton) {

		}
		if (event.getSource() == removeButton) {

		}
		if (event.getSource() == importButton) {

		}
		if (event.getSource() == exportButton) {

		}
		if (event.getSource() == slider) {

		}
	}
	// change lable name when music changes
	/*
	 * private void chageMusic() { nowPlaying.setText((new Date()).toString()); }
	 */

}
