package main;

import Controllers.mainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        //loader.setController(this);
        Parent root = loader.load();

        Probe player = new Probe(loader.getController());



        AtomicInteger count = new AtomicInteger();

        ScheduledExecutorService exec = Executors.newScheduledThreadPool(5, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t ;
        });
        exec.scheduleAtFixedRate(() -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() {
                    return "Count: "+count.incrementAndGet();
                }
            };
            task.setOnSucceeded(event -> player.probe());
            task.run();
        }, 1, 1, TimeUnit.SECONDS);



/*
        ScheduledService<String> service = new ScheduledService<String>() {
            @Override
            protected Task<String> createTask() {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() {
                        return "Count: "+count.incrementAndGet();
                    }
                };
                return task ;
            }
        };
        service.setOnSucceeded(event -> mainController.showLabel.setText(service.getValue()));
        service.setDelay(Duration.seconds(1));
        service.setPeriod(Duration.seconds(1));
        service.start();
        /*
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(5, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t ;
        });
        AtomicInteger count = new AtomicInteger();
        Runnable task = () -> {
            System.out.println("Hello world!");
            //Platform.runLater(() -> label.setText("Count: "+count.incrementAndGet()));
            Platform.runLater(() -> mainController.showLabel.setText("update "));
            System.out.println("Hello again");
        };
        exec.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
*/
        primaryStage.setTitle("FavTrack");
        primaryStage.setScene(new Scene(root, 500, 575));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //new Controllers.mainController().update();
        launch(args);

    }
}
