# TouchQuiz

Projeto da disciplina de Programação Orientada a Objetos (MC322).

O TouchQuiz tem como objetivo criar uma experiência para fãs de futebol americano, estimulando o raciocínio estratégico através de quarterbacks e multiplicadores, onde o jogador precisa alcançar uma meta pré-estipulada multiplicando as estatísticas dos atletas. É uma ferramenta educativa e ao mesmo tempo um passatempo que testa o conhecimento do jogador sobre os atletas da posição mais importante do futebol americano.


## Como executar o programa
Pré-requisitos:
- Java 21 instalado e configurado no PATH.
- Gradle instalado (ou usar o wrapper).
- Clonar o repositório:
   - git clone https://github.com/jpcpsgodoy/projeto_mc322.git
- Compilar com ./gradle build ou gradlew.bat build e executar com ./gradlew run, gradlew.bat run ou pela extensão do Gradle na IDE.


## Estrutura atual do projeto

```
TouchQuiz/
├── .gitignore
├── app/
│   ├── historico_jogos.csv
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/touchquiz/
│       │   │       ├── App.java
│       │   │       ├── auxiliares/
│       │   │       │   └── GerenciadorArquivos.java
│       │   │       ├── controller/
│       │   │       │   ├── GameController.java
│       │   │       │   └── HomeController.java
│       │   │       ├── exceptions/
│       │   │       │   ├── FalhaPersistenciaArquivoException.java
│       │   │       │   ├── ModoPontuacaoInvalidoException.java
│       │   │       │   ├── NaoFoiPossivelCarregarArquivoException.java
│       │   │       │   └── TipoRodadaInvalidoException.java
│       │   │       ├── model/
│       │   │       │   ├── Jogada.java
│       │   │       │   ├── ModoPontuacao.java
│       │   │       │   ├── Multiplicador.java
│       │   │       │   ├── MultiplicadorFactory.java
│       │   │       │   ├── Quarterback.java
│       │   │       │   ├── Rodada.java
│       │   │       │   ├── RodadaDesafio.java
│       │   │       │   ├── RodadaFactory.java
│       │   │       │   └── RodadaNormal.java
│       │   │       ├── services/
│       │   │       │   └── GameService.java
│       │   │       └── view/
│       │   │           └── (Arquivos FXML da interface, ex: HomeWindow.fxml, GameWindow.fxml)
│       │   └── resources/
│       │       ├── dados.csv
│       │       └── (Ícones e imagens)
│       └── test/
│           └── java/
│               └── com/touchquiz/
│                   └── AppTest.java
├── gradle/
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md
```

## Funcionalidades principais
- Sortear quarterbacks aleatoriamente

- Aplicar multiplicadores estratégicos

- Meta de pontos por rodada (escolhida pelo jogador)

- Dois modos de pontuação:

   - Touchdowns passados

   - Touchdowns totais

- Dois tipos de rodada:

   - Normal (exibe estatísticas antes de aplicar multiplicador)

   - Desafio (estatísticas só aparecem após aplicar)

- Exibição de histórico com cores (verde/vermelho)

- Persistência dos resultados em arquivo

- Alertas estilizados para vitória, derrota e erros

## Escolhas de Design e Arquitetura

O projeto aplica práticas recomendadas de Programação Orientada a Objetos (POO) e alguns Design Patterns conhecidos para manter o código limpo, modular e de fácil manutenção.

- MVC (Model-View-Controller)

   - Model: Classes de negócio (Quarterback, Multiplicador, Rodada, GameService) isolam a lógica central do jogo.

   - View: Interfaces gráficas definidas com FXML (HomeWindow.fxml, GameWindow.fxml) para separação clara da apresentação.

   - Controller: Classes como HomeController e GameController interligam interação do usuário com a lógica de negócio.

Facilita manutenção e permite mudanças no layout sem alterar a lógica.

- Singleton (GameService)

   - O GameService é implementado como Singleton, garantindo uma única instância para gerenciar o estado da partida em qualquer parte do código.

Consistência de dados e controle centralizado da lógica de jogo.

- Factory (Multiplicadores e Rodadas)

   - A criação de rodadas e multiplicadores seguem uma lógica parecida com um Factory, encapsulando a criação de objetos conforme a escolha do usuário.

Simplifica a geração de objetos sem expor detalhes de implementação.


