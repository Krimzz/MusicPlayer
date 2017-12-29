package application;

import java.io.File;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
	FileChooser browser = new FileChooser();
	ArrayList<File> playlist = new ArrayList<File>();
	MediaPlayer mediaPlayer;
	Media sound;
	private int currPlay = -1;
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
	private JFXListView<Label> list_view;
	
	@FXML
	private FontAwesomeIconView play_icon;
	
	@FXML
	private void handleButtonAction(MouseEvent event) {
		if (event.getSource() == btn_exit) {//DONE
			System.exit(0);
		} else if (event.getSource() == playlistMenu && pane_playlistMenu.isVisible() == false) {
			pane_playlistMenu.setVisible(true);
		} else if (event.getSource() == playlistMenu && pane_playlistMenu.isVisible() == true)
			pane_playlistMenu.setVisible(false);

		if (event.getSource() == btn_exit_playlist) {//DONE
			pane_playlistMenu.setVisible(false);
		}
		
		if (event.getSource() == btn_minimize) {//DONE
			Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		}

		if (event.getSource() == playButton && mediaPlayer != null) {  //DONEEEE
			Status status = mediaPlayer.getStatus();
			if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
				mediaPlayer.play();
				//playButton.setGraphic();
			} 
			else 
			{
					mediaPlayer.pause();
			}
		}

		if (event.getSource() == nextButton) {

		}

		if (event.getSource() == prevButton) {

		}

		if (event.getSource() == addButton) {   //DONE
			configureFileChooser(browser);
			Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
			File file = browser.showOpenDialog(stage);
			if (file != null && !playlist.stream().anyMatch((a) -> {return file.getName().equals(a.getName());})) {
				playlist.add(file);
				list_view.getItems().add(new Label(file.getName()));
			}	
		}

		if (event.getSource() == removeButton) {   //DONE
			playlist.remove(list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem()));
			list_view.getItems().remove(list_view.getSelectionModel().getSelectedItem());
			mediaPlayer.stop();
			mediaPlayer = null;
			nowPlaying.setText(" ");
		}

		if (event.getSource() == importButton) {

		}

		if (event.getSource() == exportButton) {

		}

		if (event.getSource() == slider) {
			//music_now.setText(mediaPlayer.getCurrentTime().toString());
		}
		
		if (event.getSource() == list_view) { //DONEEEEEE
			int selPlay = list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem());
			if(currPlay == selPlay) {
				sound = new Media(playlist.get(list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem())).toURI().toString());
				if(mediaPlayer != null)
					mediaPlayer.stop();
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.play();
				nowPlaying.setText(playlist.get(list_view.getItems().indexOf(list_view.getSelectionModel().getSelectedItem())).getName());
				//music_now.setText(mediaPlayer.getCurrentTime());
			}
			
			else
			{
				currPlay = selPlay;
			}
		}
		/*slider.valueProperty().addListener(new InvalidationListener() {
		    public void invalidated(Observable ov) {
		        if (slider.isValueChanging()) {
		            // multiply duration by percentage calculated by slider position
		            if(duration!=null) {
		                mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
		            }
		            updateValues();
		       }
		    }*/

	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Add Music");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Music\\"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3", "*.mp3"),new FileChooser.ExtensionFilter("WAV", "*.wav"));
	}
	
	
	// change lable name when music changes
	/*
	 * private void chageMusic() { nowPlaying.setText((new Date()).toString()); }
	 */

}
