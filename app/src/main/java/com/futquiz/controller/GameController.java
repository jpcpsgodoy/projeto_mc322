package com.futquiz.controller;

import com.futquiz.auxiliares.GerenciadorArquivos;
import com.futquiz.exceptions.FalhaPersistenciaArquivoException;
import com.futquiz.exceptions.ModoPontuacaoInvalidoException;
import com.futquiz.exceptions.NaoFoiPossivelCarregarArquivoException;
import com.futquiz.exceptions.TipoRodadaInvalidoException;
import com.futquiz.model.Multiplicador;
import com.futquiz.model.Quarterback;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controle da interface gráfica
 *
 * @author Larissa Palhares
 * @author João Pedro
 * @author Gustavo Henrique
 */
public class GameController {

    private final GameService service = GameService.getInstance();

    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<String[]> tabelaJogosSalvos;
    @FXML
    private TableColumn<String[], String> colExibicao;
    @FXML
    private TableColumn<String[], String> colStats;
    @FXML
    private TableColumn<String[], String> colMeta;
    @FXML
    private TableColumn<String[], String> colPontos;
    @FXML
    private Label labelNomeQbSorteado;
    @FXML
    private ImageView imagemQBSorteado;
    @FXML
    private ImageView molduraQB;
    @FXML
    private VBox vboxMultiplicadores;
    @FXML
    private Button botaoSortear;

    @FXML
    private Label labelMeta;

    @FXML
    private ProgressBar barraDeProgresso;

    @FXML
    private Label labelProgresso; //

    private Quarterback qbAtual;

    private boolean abrirHistorico = false;

    public void setAbrirHistorico(boolean abrir) {
        this.abrirHistorico = abrir;
    }

    /**
     * Inicializa o jogo
     */
    @FXML
    public void initialize() {
        tabPane.setVisible(false);
        configurarTabelaJogosRecentes();
        mostrarTelaConfiguracao();
        tabPane.setVisible(true);
    }


    /**
     * Configura a tabela de jogos recentes que mostrará oss dados das ultimas rodadas
     */
    private void configurarTabelaJogosRecentes() {
        colExibicao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        colStats.setCellValueFactory(data -> new SimpleStringProperty("TDs " + data.getValue()[1]));
        colMeta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        colPontos.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        colPontos.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                    setStyle("");
                } else {
                    setText(item);
                    String[] row = getTableView().getItems().get(getIndex());
                    Tooltip tooltip = new Tooltip(row[4]);
                    setTooltip(tooltip);
                    int pontos = Integer.parseInt(item);
                    int meta = Integer.parseInt(row[2]);
                    if (pontos >= meta) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            }
        });

        try {
            List<String[]> historico = GerenciadorArquivos.carregarHistoricoJogos();
            tabelaJogosSalvos.setItems(FXCollections.observableArrayList(historico));
        } catch (FalhaPersistenciaArquivoException e) {
            // Informa o usuário sobre o erro crítico
            String header = "Você cometeu uma falta!";
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", header, e.getMessage(), "/icons/erro(falta).png");
        }

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

        ImageView imagemConfig = criarImageView("/icons/config.png", 100, 100);
        Label explicacao = new Label("Esta é a tela de configuração do TouchQuiz. Escolha a meta de pontos, o modo de pontuação e o tipo de rodada para começar a jogar! ");
        explicacao.setWrapText(true);
        explicacao.setMaxWidth(300);

        ComboBox<Integer> metaBox = criarComboBox(service.obterMetasDisponiveis());
        ComboBox<String> modoBox = criarComboBox("Tipo de Pontuação", "Passados", "Totais");
        ComboBox<String> tipoBox = criarComboBox("Tipo de Rodada", "Normal", "Desafio");

        Button iniciar = new Button("Iniciar Jogo");
        iniciar.setOnAction(e -> acaoBotaoIniciar(metaBox, modoBox, tipoBox, dialog));

        VBox box = new VBox(10, imagemConfig, explicacao, metaBox, modoBox, tipoBox, iniciar);
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
        String modoSelecionado = modoBox.getValue();
        String tipoRodadaSelecionado = tipoBox.getValue();

        try {
            service.iniciarJogo(meta, modoSelecionado, tipoRodadaSelecionado);
            configurarTelaRodada();
            dialog.close();
        } catch (NaoFoiPossivelCarregarArquivoException |
                 ModoPontuacaoInvalidoException |
                 TipoRodadaInvalidoException ex) {
            String header = "Você cometeu uma falta!";
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", header, ex.getMessage(), "/icons/erro(falta).png");
        }
    }

    /**
     * Configura a tela da rodada
     */
    private void configurarTelaRodada() {
        inicializaMolduraQB();
        int metaAtual = service.getRodada().getMeta();
        labelMeta.setText("Meta: " + metaAtual);
        construirMultiplicadores();
        limparExibicaoQB();
        atualizarProgresso(metaAtual, 0);
        tabPane.setVisible(true);
    }


    /**
     * Constrói os botões de multiplicadores e os exibe na tela ao lado de seu fator
     */
    private void construirMultiplicadores() {
        vboxMultiplicadores.getChildren().clear();
        for (Multiplicador m : service.getMultiplicadoresDisponiveis()) {
            Button botao = new Button("Aplicar");
            Label label = new Label(m.getValor() + "x");

            HBox linha = new HBox(10, botao, label);
            linha.setAlignment(Pos.CENTER_LEFT);
            botao.setOnAction(e -> aplicarMultiplicador(botao, label, m));
            botao.setDisable(qbAtual == null);
            vboxMultiplicadores.getChildren().add(linha);
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
     * Sorteia um quarterback, exibe na tela seu nome e imagem e desabilita o botão de sortear
     */
    @FXML
    private void acaoBotaoSortear() {
        qbAtual = service.sortearQuarterback();
        String textoQb = qbAtual.getNome();
        if (service.deveExibirEstatistica()) {
            textoQb += " (" + service.getRodada().getPontuacaoQB(qbAtual) + ")";
        }

        labelNomeQbSorteado.setText(textoQb);
        imagemQBSorteado.setImage(new Image(getClass().getResourceAsStream("/imagens/" + qbAtual.getId() + ".png")));
        atualizarEstadoMultiplicadores(true);
        botaoSortear.setDisable(true);
    }

    /**
     * Limpa a exibição do quarterback após a aplicação de um multiplicador e habilita o botão de sortear
     */
    private void limparExibicaoQB() {
        imagemQBSorteado.setImage(new Image(getClass().getResourceAsStream("/icons/qb_generico.png")));
        labelNomeQbSorteado.setText(null);
        qbAtual = null;
        atualizarEstadoMultiplicadores(false);
        botaoSortear.setDisable(false);
    }

    /**
     * Inicializa a moldura do quarterback
     */
    private void inicializaMolduraQB() {
        molduraQB.setImage(new Image(getClass().getResourceAsStream("/icons/moldura_qb.png")));
    }

    /**
     * Aplica um multiplicador ao quarterback
     *
     * @param botao         Botão de aplicação
     * @param label         Label referente ao multiplicador
     * @param multiplicador Multiplicador a ser aplicado
     */
    private void aplicarMultiplicador(Button botao, Label label, Multiplicador multiplicador) {
        if (qbAtual == null) return;
        int pontos = service.aplicarMultiplicador(qbAtual, multiplicador);
        label.setText(multiplicador.getValor() + "x - " + qbAtual.getNome() + " (" + pontos + ")");
        botao.setDisable(true);
        botao.setText("Aplicado");
        atualizarProgresso(service.getRodada().getMeta(), service.getRodada().getPontosAcumulados());

        if (service.jogoAcabou()) {
            String mensagem = service.getMensagemResultado();
            if (service.jogadorVenceu()) {
                String header = "Parabéns, você venceu!";
                mostrarAlerta(Alert.AlertType.INFORMATION, "Vitória!", header, mensagem, "/icons/vitoria.png");
            } else {
                String header = "Que pena, vocé perdeu!";
                mostrarAlerta(Alert.AlertType.INFORMATION, "Derrota.", header, mensagem, "/icons/derrota.png");
            }
            botaoSortear.setDisable(true);
            //atualiza tabela em tempo real
            configurarTabelaJogosRecentes();
            // mostrar tela para reiniciar ou voltar à tela de configuração
            mostrarTelaNovoJogo();
        }

        limparExibicaoQB();
    }

    /**
     * Atualiza o estado dos botões de multiplicadores
     *
     * @param habilitar Habilitar ou desabilitar os botões
     */
    private void atualizarEstadoMultiplicadores(boolean habilitar) {
        for (int i = 0; i < vboxMultiplicadores.getChildren().size(); i++) {
            HBox linha = (HBox) vboxMultiplicadores.getChildren().get(i);
            Button botao = (Button) linha.getChildren().get(0);
            Label label = (Label) linha.getChildren().get(1);

            boolean jaUsado = label.getText().contains("-");
            botao.setDisable(jaUsado || !habilitar);
        }
    }

    /**
     * Atualiza o label e a barra de progresso com a pontuação acumulada e a porcentagem da meta
     *
     * @param meta             Meta da rodada
     * @param pontosAcumulados Pontuação acumulada na rodada
     */
    private void atualizarProgresso(int meta, int pontosAcumulados) {
        double porcentagem = (double) pontosAcumulados / meta * 100;
        if (porcentagem < 100) {
            barraDeProgresso.setProgress(porcentagem / 100);
            labelProgresso.setText(String.format("%.2f%%: %d", porcentagem, pontosAcumulados));
        } else {
            barraDeProgresso.setProgress(1);
            labelProgresso.setText("100%: " + pontosAcumulados);
        }
    }

    @FXML
    private void mostrarTelaNovoJogo() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Novo Jogo");
        alert.setHeaderText("Iniciar Novo Jogo");
        alert.setContentText("Deseja iniciar um novo jogo?");
        ImageView imagem = criarImageView("/icons/reiniciar.png", 40, 40);
        alert.getDialogPane().setGraphic(imagem);

        Stage stage = (Stage) tabPane.getScene().getWindow();
        alert.initOwner(stage);

        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");

        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        alert.showAndWait().ifPresent(resposta -> {
            if (resposta == botaoSim) {
                mostrarTelaConfiguracao();
            } else if (resposta == botaoNao) {
                voltarParaTelaInicial();
            }
        });
    }

    /**
     * Retorna para a tela inicial do jogo.
     */
    private void voltarParaTelaInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeWindow.fxml"));
            Scene scene = new Scene(loader.load(), 500, 400);
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("ToucQuiz");
            stage.show();
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível retornar à tela inicial.", e.getMessage(), "/icons/erro(falta).png");
        }
    }


    /**
     * Exibe a tela de reinício do jogo, perguntando se o jogador deseja reiniciar com as mesmas configurações ou mudar as configurações.
     * Se o jogador escolher "Sim", reinicia o jogo com as mesmas configurações.
     * Se escolher "Não", exibe a tela de configuração novamente.
     * Se escolher "Cancelar", não faz nada e retorna à tela atual.
     */
    @FXML
    private void acaoBotaoReiniciar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reiniciar Jogo");
        alert.setHeaderText("Confirmação de Reinício");
        alert.setContentText("Você deseja reiniciar a rodada atual com as mesmas configurações?");
        ImageView imagem = criarImageView("/icons/reiniciar.png", 40, 40);
        alert.getDialogPane().setGraphic(imagem);

        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Mudar Configurações");
        ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(botaoSim, botaoNao, botaoCancelar);

        alert.showAndWait().ifPresent(resposta -> {
            if (resposta == botaoSim) {
                try {
                    service.reiniciarJogoMesmasConfigs();
                    configurarTelaRodada();
                } catch (NaoFoiPossivelCarregarArquivoException |
                         ModoPontuacaoInvalidoException |
                         TipoRodadaInvalidoException ex) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível reiniciar!", ex.getMessage(), "/icons/erro(falta).png");
                }
            } else if (resposta == botaoNao) {
                mostrarTelaConfiguracao();
            }
        });
    }


    /**
     * Exibe a tela de ajuda com dicas de jogo.
     */
    @FXML
    private void acaoBotaoAjuda() {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Dicas de Jogo", "Estratégia para a Rodada",
                "Você tem 8 quarterbacks e precisa superar a meta de pontos que definiu no início da rodada.\n\n" +
                        "Cada jogador possui uma estatística de touchdowns, que pode ser total ou passados, dependendo da rodada que você escolheu.\n\n" +
                        "Para cada jogador sorteado, você aplicará um multiplicador: quanto maior o multiplicador, maior o impacto no seu total de pontos.\n\n" +
                        "Dica: Guarde os multiplicadores mais altos para os jogadores com melhores estatísticas. Essa estratégia é fundamental para bater a meta!\n\n" +
                        "No modo Normal, você verá a estatística do jogador antes de aplicar o multiplicador.\n" +
                        "No modo Desafiador, você terá que confiar no seu conhecimento - a estatística só será revelada depois da escolha!",
                "/icons/lamp.png", 60, 50);
    }

    /**
     * Exibe um alerta customizado com imagem, título e mensagem.
     *
     * @param tipo          Tipo do alerta (INFORMATION, ERROR, etc)
     * @param titulo        Título da janela
     * @param header        Texto do cabeçalho
     * @param msg           Texto do conteúdo
     * @param caminhoImagem Caminho da imagem para exibir
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String header, String msg, String caminhoImagem) {
        Alert alerta = new Alert(tipo);
        ImageView imageView = criarImageView(caminhoImagem, 100, 100);
        alerta.getDialogPane().setGraphic(imageView);
        alerta.setTitle(titulo);
        alerta.setHeaderText(header);
        alerta.setContentText(msg);
        Stage owner = (Stage) tabPane.getScene().getWindow();
        alerta.initOwner(owner);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.showAndWait();
    }

    /**
     * Exibe um alerta customizado com imagem, título, mensagem. A imagem pode ter tamanho personalizado.
     *
     * @param tipo          Tipo do alerta (INFORMATION, ERROR, etc)
     * @param titulo        Título da janela
     * @param header        Texto do cabeçalho
     * @param msg           Texto do conteúdo
     * @param caminhoImagem Caminho da imagem para exibir
     * @param altura        Altura da imagem
     * @param largura       Largura da imagem
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String header, String msg, String caminhoImagem, int altura, int largura) {
        Alert alerta = new Alert(tipo);
        ImageView imageView = criarImageView(caminhoImagem, altura, largura);
        alerta.getDialogPane().setGraphic(imageView);
        alerta.setTitle(titulo);
        alerta.setHeaderText(header);
        alerta.setContentText(msg);
        Stage owner = (Stage) tabPane.getScene().getWindow();
        alerta.initOwner(owner);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.showAndWait();
    }

    /**
     * Cria um ImageView ajustado para o tamanho desejado.
     *
     * @param caminho Caminho do recurso de imagem
     * @param altura  Altura em pixels
     * @param largura Largura em pixels
     * @return ImageView pronto para uso
     */
    private ImageView criarImageView(String caminho, double altura, double largura) {
        Image imagem = new Image(getClass().getResourceAsStream(caminho));
        ImageView imageView = new ImageView(imagem);
        imageView.setFitHeight(altura);
        imageView.setFitWidth(largura);
        return imageView;
    }

}