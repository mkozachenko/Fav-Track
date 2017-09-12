package Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import MYSHOWS.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by symph on 09.07.2017.
 */
public class mainController {

    private int duration, position;
    private long percent;
    public static boolean correction=false;
    private String episode, season, show;

    @FXML
    private TextField shownameField,seasonField, episodeField;
    @FXML
    private MenuBar menubar;
    @FXML
    private ImageView showImage;
    @FXML
    private Image img;
    @FXML
    private WebView showInfo;
    @FXML
    private ToggleGroup userRating;
    @FXML
    private MenuItem menuButton_settings, menuButton_login, menuButton_close, menuButton_about;


    @FXML
    private Button correctionButton, correctionConfirm, correctionCancel;

    @FXML
    private void clickCorrectionButton(){
        correction = true;
        shownameField.setEditable(true);
        seasonField.setEditable(true);
        episodeField.setEditable(true);
        correctionConfirm.setVisible(true);
        correctionCancel.setVisible(true);
        correctionButton.setVisible(false);
    }
    @FXML
    private void clickOnCorrectionConfirm(){
        correction = true;
        shownameField.setEditable(false);
        seasonField.setEditable(false);
        episodeField.setEditable(false);
        correctionConfirm.setVisible(false);
        correctionCancel.setVisible(false);
        correctionButton.setVisible(true);
    }
    @FXML
    private void clickOnCorrectionCancel(){
        correction = false;
        shownameField.setEditable(false);
        seasonField.setEditable(false);
        episodeField.setEditable(false);
        shownameField.setText(show);
        seasonField.setText(season);
        episodeField.setText(episode);
        correctionConfirm.setVisible(false);
        correctionCancel.setVisible(false);
        correctionButton.setVisible(true);
    }
    @FXML
    private void clickSendButton(){
        String rating, searchQuery, episodeId, season, episode;
        season = seasonField.getText();
        episode = episodeField.getText();
        /*Get user rating*/
        RadioButton chk = (RadioButton)userRating.getSelectedToggle();
        rating = chk.getText();
        /*Form MyShows query*/
        searchQuery = shownameField.getText().replace(" ", ".");
        if (season.length()>1 && !season.startsWith("0")){
            searchQuery = searchQuery+".S"+season;
        }else if (season.length()==1){
            searchQuery = searchQuery+".S0"+season;
        } /*else if(season.startsWith("0")){
            season.replaceFirst("0","");
            searchQuery = searchQuery+".S"+season;
        }*/
        if (episode.length()>1 && !episode.startsWith("0")){
            searchQuery = searchQuery+"E"+episode;
        }else if (episode.length()==1){
        searchQuery = searchQuery+"E0"+episode;
        } /*else if(season.startsWith("0")){
        season.replaceFirst("0","");
        searchQuery = searchQuery+"E"+season;
        }*/
        System.out.println(searchQuery);
        new Shows().searchByFile(searchQuery);
        episodeId = new Shows().getEpisodeId();
        new Manage().rateEpisode(episodeId,rating);
    }

    ////////////////////////////
    public void updateShowData(){
        //new Getter().getData();

        show = new Getter().getShowname();
        season = new Getter().getSeason();
        episode = new Getter().getEpisode();

        shownameField.setText(show);
        seasonField.setText(season);
        episodeField.setText(episode);
        getShowData();
    }

    public void getShowData(){
        new Shows().getById(Shows.showId);
        WebEngine webEngine = showInfo.getEngine();
        String desc = new Shows().getDescription().replace("\\r","").replace("\\n","").replace("\"","");
        String poster = new Shows().getPoster();
        img = new Image(poster);

        //webEngine.loadContent(desc);
        showImage.setImage(img);
        centerImage();
    }

    private void centerImage() {
        Image img = showImage.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = showImage.getFitWidth() / img.getWidth();
            double ratioY = showImage.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            showImage.setX((showImage.getFitWidth() - w) / 2);
            showImage.setY((showImage.getFitHeight() - h) / 2);

        }
    }

    /*Menubar actions*/
    @FXML
    private void menuButtonClose(){
        Stage stage = (Stage) menubar.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void menuButtonAbout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("aboutWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            //Stage stageOld = (Stage) exit.getScene().getWindow();
            //stageOld.hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.getIcons().add(new Image("star-icon.png"));
            stage.setTitle("О программе");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void menuButtonLogin(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            //Stage stageOld = (Stage) exit.getScene().getWindow();
            //stageOld.hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.getIcons().add(new Image("star-icon.png"));
            stage.setTitle("Настройки входа");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void menuButtonSettings(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("settingsWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            //Stage stageOld = (Stage) exit.getScene().getWindow();
            //stageOld.hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.getIcons().add(new Image("star-icon.png"));
            stage.setTitle("Настройки");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
