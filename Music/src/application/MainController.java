package application;

import java.io.File;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {
	FileChooser browser = new FileChooser();
	ArrayList<File> playlist = new ArrayList<File>();
	MediaPlayer mediaPlayer;
	Media sound;
	private int currPlay = -1;
	private Duration duration;
	private Duration currentTime;
	String time;

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
	private JFXSlider volumeSlider;

	@FXML
	private Label nowPlaying;

	@FXML
	private Label music_now;

	@FXML
	private Label music_time;

	@FXML
	private JFXListView<Label> list_view;

	@FXML
	private FontAwesomeIconView play_icon;

	@FXML
	private FontAwesomeIconView pause_icon;

	@FXML
	private void handleButtonAction(MouseEvent event) {
		if (event.getSource() == btn_exit) {// DONE
			System.exit(0);
		} else if (event.getSource() == playlistMenu && pane_playlistMenu.isVisible() == false) {
			pane_playlistMenu.setVisible(true);
		} else if (event.getSource() == playlistMenu && pane_playlistMenu.isVisible() == true)
			pane_playlistMenu.setVisible(false);

		if (event.getSource() == btn_exit_playlist) {// DONE
			pane_playlistMenu.setVisible(false);
		}

		if (event.getSource() == btn_minimize) {// DONE
			Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		}

		if (event.getSource() == playButton && mediaPlayer != null) { // DONEEEE
			Status status = mediaPlayer.getStatus();
			if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
				mediaPlayer.play();
				play_icon.setVisible(false);
				pause_icon.setVisible(true);
			} else {
				play_icon.setVisible(true);
				pause_icon.setVisible(false);
				mediaPlayer.pause();
			}
		}

		if (event.getSource() == nextButton) {// DONEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
			int index = list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem());
			if (index + 1 >= playlist.size()) {
				index = -1;
			}
			mediaPlayer.stop();
			sound = new Media(playlist.get(index + 1).toURI().toString());
			mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setOnReady(() -> {
				duration = mediaPlayer.getMedia().getDuration();
				music_time.setText(formatTime(duration, duration).split("/")[0]);
			});
			mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
			mediaPlayer.currentTimeProperty().addListener((ov) -> {
				updateValues();
			});

			currentTime = mediaPlayer.getCurrentTime();

			mediaPlayer.play();
			slider.setValue(0);
			play_icon.setVisible(false);
			pause_icon.setVisible(true);
			nowPlaying.setText(playlist.get(index + 1).getName());
			list_view.getSelectionModel().select(index + 1);

		}

		if (event.getSource() == prevButton) { // DONEEEEEEEEEEEEEEEEEEEEEEEEE
			int index = list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem());
			if (index - 1 < 0) {
				index = list_view.getItems().size();
			}
			mediaPlayer.stop();
			sound = new Media(playlist.get(index - 1).toURI().toString());
			mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setOnReady(() -> {
				duration = mediaPlayer.getMedia().getDuration();
				music_time.setText(formatTime(duration, duration).split("/")[0]);
			});
			mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
			mediaPlayer.currentTimeProperty().addListener((ov) -> {
				updateValues();
			});

			currentTime = mediaPlayer.getCurrentTime();

			mediaPlayer.play();
			slider.setValue(0);
			play_icon.setVisible(false);
			pause_icon.setVisible(true);
			nowPlaying.setText(playlist.get(index - 1).getName());
			list_view.getSelectionModel().select(index - 1);
		}

		if (event.getSource() == addButton) { // DONE
			configureFileChooser(browser);
			Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
			File file = browser.showOpenDialog(stage);
			if (file != null && !playlist.stream().anyMatch((a) -> {
				return file.getName().equals(a.getName());
			})) {
				playlist.add(file);
				list_view.getItems().add(new Label(file.getName()));
			}
		}

		if (event.getSource() == removeButton && mediaPlayer != null) { // DONE
			mediaPlayer.stop();
			mediaPlayer = null;
			music_time.setText("00:00");
			music_now.setText("00:00");
			slider.setValue(0);
			play_icon.setVisible(true);
			pause_icon.setVisible(false);
			nowPlaying.setText(" ");
			playlist.remove(list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem()));
			list_view.getItems().remove(list_view.getSelectionModel().getSelectedItem());
		}

		if (event.getSource() == importButton) { // ?

		}

		if (event.getSource() == exportButton) { // ?

		}

		if (event.getSource() == slider && mediaPlayer != null) {
			mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
		}

		if (event.getSource() == volumeSlider) {
			mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
		}

		if (event.getSource() == list_view) { // DONEEEEEE
			int selPlay = list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem());
			if (currPlay == selPlay) {
				sound = new Media(
						playlist.get(list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem()))
								.toURI().toString());
				if (mediaPlayer != null) {
					mediaPlayer.stop();
				}

				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setOnReady(() -> {
					duration = mediaPlayer.getMedia().getDuration();
					music_time.setText(formatTime(duration, duration).split("/")[0]);
				});
				mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
				mediaPlayer.currentTimeProperty().addListener((ov) -> {
					updateValues();
				});

				currentTime = mediaPlayer.getCurrentTime();

				mediaPlayer.play();
				slider.setValue(0);
				play_icon.setVisible(false);
				pause_icon.setVisible(true);
				nowPlaying.setText(playlist
						.get(list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem())).getName());

			} else {
				currPlay = selPlay;
			}
		}
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Add Music");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Music\\"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3", "*.mp3"),
				new FileChooser.ExtensionFilter("WAV", "*.wav"));
	}

	protected void updateValues() {
		if (music_now != null && slider != null && volumeSlider != null && duration != null && mediaPlayer != null) {
			Platform.runLater(new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					currentTime = mediaPlayer.getCurrentTime();
					music_now.setText(formatTime(currentTime, duration).split("/")[0]);
					slider.setDisable(duration.isUnknown());
					if (!slider.isDisabled() && duration.greaterThan(Duration.ZERO) && !slider.isValueChanging()) {
						slider.setValue(currentTime.divide(duration).toMillis() * 100.0);
					}
				}
			});
		}
	}

	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}

}
