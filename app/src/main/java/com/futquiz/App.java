package com.futquiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe principal do jogo, responsável por iniciar a interface gráfica
 *
 * @author Gustavo Henrique
 * @author João Pedro
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Inicia a interface gráfica do jogo
     *
     * @param primaryStage Janela principal
     * @throws Exception Erro ao iniciar a interface gráfica
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/logo.png")));

        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeWindow.fxml"));
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FutQuiz");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}