package Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import MYSHOWS.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private ImageView showImage;
    @FXML
    private Image img;
    @FXML
    private WebView showInfo;
    @FXML
    private ToggleGroup userRating;



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
        String rating, searchQuery, episodeId;
        RadioButton chk = (RadioButton)userRating.getSelectedToggle();
        rating = chk.getText();
        System.out.println(rating);
        searchQuery = shownameField.getText()+".S0"+seasonField.getText()+"E0"+episodeField.getText();
        new Shows().searchByFile(searchQuery);
        episodeId = new Shows().getEpisodeId();
        System.out.println(rating+"__"+searchQuery+"___"+episodeId);
        new Manage().rateEpisode(episodeId,rating);
    }

    ////////////////////////////
    public void updateShowData(){
        new Getter().getData();

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

}
