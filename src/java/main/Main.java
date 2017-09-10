package main;

import Controllers.*;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.*;

public class Main extends Application {
    boolean front=false;
    String userLogin =new GetPropetries().getUserLogin();
    String userPassword = new GetPropetries().getUserPassword();
    public static FXMLLoader loader;


    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
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
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    boolean front(boolean percent){
        if (percent){
        front = true;}
        return front;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void execute(){
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
                    if(!mainController.correction){
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
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }, 1, 15, TimeUnit.SECONDS);
    }
}
