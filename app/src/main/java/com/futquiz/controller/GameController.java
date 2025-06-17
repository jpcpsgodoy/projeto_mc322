package com.futquiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;

/**
 * Controlador principal do jogo, responsável por gerenciar a interface gráfica
 */
public class GameController {
    
    @FXML
    private TabPane tabPane;

    private final GameService service = new GameService();

    @FXML
    public void initialize() {
        tabPane.setVisible(false);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Bem-vindo ao FutQuiz!");
        alerta.setHeaderText("FutQuiz - Teste seu conhecimento sobre Quarterbacks!");
        alerta.setContentText(
                "Modos de Pontuação:\n" +
                        "• TD Passe: touchdowns de passe do QB\n" +
                        "• TD Total: soma touchdowns de passe + corridos\n\n" +
                        "Rodada Normal: mostra as estatísticas.\n" +
                        "Rodada Desafio: oculta estatísticas para desafiar seu conhecimento.");
        alerta.showAndWait();

        tabPane.setVisible(true);
    }
}
