package com.touchquiz.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controlador da tela inicial do jogo TouchQuiz.
 * A classe é responsável por gerenciar a interação do usuário com a tela inicial,
 * 
 * @author Larissa Palhares
 */
public class HomeController {
    @FXML
    private ImageView logo;

    @FXML
    private ImageView fundo;

    @FXML
    private Button botaoContinuar;

    @FXML
    private Button botaoRegras;
    
    /**
     * Método chamado quando a janela é carregada.
     */
    @FXML
    private void initialize() {
        inicializarImagens(logo, "/icons/logo.png");
        inicializarImagens(fundo, "/imagens/fundo.png");
    }

    /**
     * Inicializa as imagens do jogo.
     * 
     *  @param imagem A ImageView onde a imagem será exibida.
     *  @param caminho O caminho do recurso da imagem.
     */
    private void inicializarImagens(ImageView imagem, String caminho) {
        imagem.setImage(new Image(getClass().getResourceAsStream(caminho)));
    }

    /**
     * Inicializa o jogo. 
     */
    @FXML
    private void acaoBotaoContinuar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameWindow.fxml"));
            Pane root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 655, 500));
            stage.setTitle("TouchQuiz - Jogo");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
        }
    }

    /**
     * Exibe uma janela com as regras do jogo.
     */
    @FXML
    private void acaoBotaoRegras() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Regras do Jogo");
        alerta.setHeaderText("Como jogar o TouchQuiz");
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/icons/playbook.png")));
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        alerta.getDialogPane().setGraphic(imageView);
        alerta.setContentText("Neste jogo, seu objetivo é atingir uma meta de touchdowns escolhendo Quarterbacks sorteados e aplicando multiplicadores estratégicos.\n" +
            "A cada jogador sorteado, você analisa suas estatísticas e decide como usá-lo para se aproximar da meta de pontos.\n\n" +
            "Modos de Pontuação:\n" +
            "• TD Passe: touchdowns de passe do QB\n" +
            "• TD Total: soma touchdowns de passe + corridos\n\n" +
            "Tipos de Rodada:\n" +
            "• Rodada Normal: mostra as estatísticas\n" +
            "• Rodada Desafio: oculta estatísticas para desafiar seu conhecimento"
        );
        alerta.showAndWait();
    }
}
