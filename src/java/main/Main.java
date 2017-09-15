package main;

import Controllers.*;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.concurrent.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application {
    boolean front = false;
    String userLogin = new GetPropetries().getUserLogin();
    String userPassword = new GetPropetries().getUserPassword();
    public static FXMLLoader loader;
    private static Logger logger = Logger.getLogger(Main.class.getName());
    private static FileHandler fh;


    public static void main(String[] args) {

        /*try {
            File extPropFile = new File(extFolder+propFileName);
            FileInputStream is = new FileInputStream(propFileName);
            if(!extPropFile.exists()){
                FileUtils.copyInputStreamToFile(is, extPropFile);
                is.close();
            } else {is.close();}
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainWindow.fxml"));
        MyShowsOAuth.getToken(userLogin, userPassword);
        /*if(new GetPropetries().getAutoLogin()) {
            loader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
            MyShowsOAuth.getToken(userLogin, userPassword);
        } else {
            loader = new FXMLLoader(getClass().getClassLoader().getResource("loginWindow.fxml"));
        }*/
        Parent root = loader.load();
        execute();

        /*ScheduledFuture<?> countdown = exec.schedule(new Runnable() {
            @Override
            public void run() {
                // do the thing
                System.out.println("Scheduled once!");
            }}, 2, TimeUnit.SECONDS);*/
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                primaryStage.setMaximized(false);
        });
        primaryStage.setResizable(false);
        primaryStage.setTitle("FavTrack");
        primaryStage.getIcons().add(new Image("images/star-icon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    boolean front(boolean percent) {
        if (percent) {
            front = true;
        }
        return front;
    }

    private static void execute() {
        Probe controller = new Probe(loader.getController());
        controller.rememberMe();
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(5, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        exec.scheduleAtFixedRate(() -> {
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() {
                    boolean end;
                    System.out.println(controller.percentage());
                    end = controller.percentage() >= 95.0;
                    return end;
                }
            };
            Task<Object> task2 = new Task<Object>() {
                @Override
                public String call() {
                    if (!mainController.correction) {
                        controller.showname();
                        System.out.println("SHOWTIME");
                    }
                    return null;
                }
            };
            /////////////////
            //task2.setOnSucceeded(event -> controller.showname());
            task.run();
            try {
                if (task.get()) {
                    task.cancel();
                    //front(task.get());
                    task2.run();
                } else {
                    task2.cancel();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger(logger, "severe", e.getMessage());
            } catch (ExecutionException e) {
                e.printStackTrace();
                logger(logger, "severe", e.getMessage());
            }
        }, 1, 15, TimeUnit.SECONDS);
    }

    public static void logger(Logger logger, String level, String message) {
        //Logger logger = Logger.getLogger();
        try {
            fh = new FileHandler("./logging.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (level) {
            case "info":
                logger.info("\n"+message+"\n");
                fh.close();
                break;
            case "error":
                logger.severe("\n"+message+"\n");
                fh.close();
                break;
        }
    }
}
