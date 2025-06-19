package com.futquiz.controller;

import com.futquiz.model.ModoPontuacao;
import com.futquiz.model.Quarterback;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;


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
    private ImageView imagemQBSorteado;

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


        mostrarTelaConfiguracao();
        tabPane.setVisible(true);
    }

    /**
     * Exibe a tela de configuração do jogo com as opções de meta, modo de pontuação e tipo de rodada
     */
    private void mostrarTelaConfiguracao() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(Event::consume);
        dialog.setTitle("Configuração do Jogo");

        Label explicacao = new Label(
                "Esta é a tela de configuração do FutQuiz.\n" +
                        "Escolha a meta de pontos, o modo de pontuação e o tipo de rodada para começar a jogar!"
        );

        explicacao.setWrapText(true);
        explicacao.setMaxWidth(300);

        ComboBox<Integer> metaBox = criarComboBox(List.of(2000, 3500, 5000));
        ComboBox<String> modoBox = criarComboBox("Tipo de Pontuação", "Passados", "Totais");
        ComboBox<String> tipoBox = criarComboBox("Tipo de Rodada", "Normal", "Desafio");

        Button iniciar = new Button("Iniciar Jogo");
        iniciar.setOnAction(e -> acaoBotaoIniciar(metaBox, modoBox, tipoBox, dialog));

        VBox box = new VBox(10, explicacao, metaBox, modoBox, tipoBox, iniciar);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        dialog.setScene(new Scene(box));
        dialog.showAndWait();
    }

    /**
     * Iniciar o jogo com as opções escolhidas na tela de configuração
     *
     * @param metaBox Combobox com as opções de meta
     * @param modoBox Combobox com as opções de modo
     * @param tipoBox Combobox com as opções de tipo
     * @param dialog  Janela de configuração
     */
    private void acaoBotaoIniciar(ComboBox<Integer> metaBox, ComboBox<String> modoBox, ComboBox<String> tipoBox, Stage dialog) {
        int meta = metaBox.getValue();
        String modoSelecionadoString = modoBox.getValue();
        String tipoRodadaSelecionado = tipoBox.getValue();
        boolean exibe;
        ModoPontuacao modo;

        //implementar exceção aqui depois e tirar esse if else feio
        if ("Passados".equals(modoSelecionadoString)) {
            modo = ModoPontuacao.TD_PASSE;
        } else if ("Totais".equals(modoSelecionadoString)) {
            modo = ModoPontuacao.TD_TOTAL;
        } else {
            Alert erroModo = new Alert(Alert.AlertType.ERROR);
            erroModo.setTitle("Erro de Seleção");
            erroModo.setContentText("Escolha entre Passados ou Totais.");
            erroModo.showAndWait();
            return;
        }

        //implementar excecao aqui depois e tirar esse if else feio
        if ("Normal".equals(tipoRodadaSelecionado)) {
            exibe = true;
        } else if ("Desafio".equals(tipoRodadaSelecionado)) {
            exibe = false;
        } else {
            Alert erroTipo = new Alert(Alert.AlertType.ERROR);
            erroTipo.setTitle("Erro de Seleção");
            erroTipo.setContentText("Escolha entre Normal ou Desafio.");
            erroTipo.showAndWait();
            return;
        }

        try {
            service.iniciarJogo(meta, modo, exibe);
            tabPane.setVisible(true);
            dialog.close();
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erro de Carregamento");
            errorAlert.setContentText(ex.getMessage());
            errorAlert.showAndWait();
        }
    }


    /**
     * Cria uma ComboBox com Strings
     *
     * @param opcoes Strings referentes às opções
     * @return ComboBox criado
     */
    private ComboBox<String> criarComboBox(String... opcoes) {
        ComboBox<String> box = new ComboBox<>();
        box.getItems().addAll(opcoes);
        box.setValue(opcoes[0]);

        return box;
    }

    /**
     * Cria uma ComboBox com ints
     *
     * @param opcoes Ints referentes às opções
     * @return ComboBox criado
     */
    private ComboBox<Integer> criarComboBox(List<Integer> opcoes) {
        ComboBox<Integer> box = new ComboBox<>();
        box.getItems().addAll(opcoes);
        box.setValue(opcoes.getFirst());

        return box;
    }

    /**
     * Sortea um quarterback e exibe na tela
     */
    @FXML
    private void botaoSortearQb() {
        Quarterback qbSorteado = service.sortearQuarterback();
        String textoLabel = qbSorteado.getNome();

        if (service.deveExibirEstatistica()) {
            int tdValor = service.getRodada().getPontuacaoQB(qbSorteado);
            textoLabel += " (" + tdValor + ")";
        }
        labelNomeQbSorteado.setText(textoLabel);

        String caminnhoImagem = "/imagens/" + qbSorteado.getId() + ".png";
        Image qbImagem = new Image(getClass().getResourceAsStream(caminnhoImagem));
        imagemQBSorteado.setImage(qbImagem);


    }
}
