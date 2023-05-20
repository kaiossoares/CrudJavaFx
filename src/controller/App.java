package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage stage;

    private static Scene homeScene;
    private static Scene createScene;
    private static Scene readScene;
    private static Scene updateScene;
    private static Scene deleteScene;

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        primaryStage.getIcons().add(new Image("cotucaLogo.png"));
        primaryStage.setTitle("CRUD ALUNOS");

        Parent fxmlHome = FXMLLoader.load(getClass().getClassLoader().getResource("view/home.fxml"));
        homeScene = new Scene(fxmlHome);

        Parent fxmlCreate = FXMLLoader.load(getClass().getClassLoader().getResource("view/create.fxml"));
        createScene = new Scene(fxmlCreate);

        Parent fxmlRead = FXMLLoader.load(getClass().getClassLoader().getResource("view/read.fxml"));
        readScene = new Scene(fxmlRead);

        Parent fxmlUpdate = FXMLLoader.load(getClass().getClassLoader().getResource("view/update.fxml"));
        updateScene = new Scene(fxmlUpdate);

        Parent fxmlDelete = FXMLLoader.load(getClass().getClassLoader().getResource("view/delete.fxml"));
        deleteScene = new Scene(fxmlDelete);

        primaryStage.setScene(homeScene);
        primaryStage.show();
    }

    public static void changeScreen(String src) {
        switch (src) {
            case "home":
                stage.setScene(homeScene);
                break;
            case "create":
                stage.setScene(createScene);
                break;
            case "read":
                stage.setScene(readScene);
                break;
            case "update":
                stage.setScene(updateScene);
                break;
            case "delete":
                stage.setScene(deleteScene);
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}