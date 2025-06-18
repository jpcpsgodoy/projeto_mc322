package com.futquiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

/**
 * Controlador principal do jogo, responsável por gerenciar a interface gráfica
 */
public class GameController {

    private final GameService service = new GameService();
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<?> tabelaJogosRecentes;
    @FXML
    private TableColumn<?, String> colNome;
    @FXML
    private TableColumn<?, Integer> colMeta;
    @FXML
    private TableColumn<?, Integer> colPontos;
    @FXML
    private TableColumn<?, String> colExibicao;
    @FXML
    private TableColumn<?, String> colStats;
    @FXML
    private Label labelNomeQbSorteado;
    @FXML
    private ImageView imageViewQbSorteado;

    @FXML
    public void initialize() {
        tabPane.setVisible(false);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Boas vindas ao FutQuiz!");
        alerta.setHeaderText("FutQuiz - Teste seu conhecimento sobre Quarterbacks!");
        alerta.setContentText("Neste jogo, seu objetivo é atingir uma meta de touchdowns escolhendo Quarterbacks sorteados e aplicando multiplicadores estratégicos.\n" +
                "A cada jogador sorteado, você analisa suas estatísticas e decide como usá-lo para se aproximar da meta de pontos.\n\n" +
                "Modos de Pontuação:\n" +
                "• TD Passe: touchdowns de passe do QB\n" +
                "• TD Total: soma touchdowns de passe + corridos\n\n" +
                "Tipos de Rodada:\n" +
                "• Rodada Normal: mostra as estatísticas.\n" +
                "• Rodada Desafio: oculta estatísticas para desafiar seu conhecimento.");
        alerta.showAndWait();

        tabPane.setVisible(true);
    }
}
