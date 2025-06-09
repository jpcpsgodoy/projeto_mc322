# FutQuiz

Projeto da disciplina de Programação Orientada a Objetos (MC322).  
Trata-se de um jogo baseado em estatísticas de jogadores (quarterbacks).

## Estrutura atual do projeto

```
PROJETO_MC322/
├── .gradle/
├── .gitignore
├── .gitattributes
├── build.gradle
├── gradle/
│   └── wrapper/
├── gradlew
├── gradlew.bat
├── README.md
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/futquiz/
│   │   │       ├── auxiliares/
│   │   │       ├── controller/
│   │   │       ├── exceptions/
│   │   │       ├── model/
│   │   │       └── view/
│   │   │       └── App.java
│   │   └── resources/
│   └── test/
```


## Branches existentes

- `dev` (ramo de desenvolvimento principal)
- `feature/model-partida`
- `feature/controller-jogo`
- `feature/view-jogo`
- `feature/persistencia-arquivo`
- `feature/carregar-estatisticas`


## Clonar o projeto

1. Acesse o repositório no GitHub.
2. Copie o link HTTPS (ex: `https://github.com/usuario/projeto_mc322.git`)
3. No terminal:

```
git clone https://github.com/usuario/projeto_mc322.git
cd projeto_mc322
```


## Como trabalhar no projeto

### Verifique a branch atual

```
git branch
```

Se estiver em `main`, mude para `dev`:

```
git checkout dev
```

### Vá para a branch da sua tarefa

```
git checkout feature/nome-da-feature
```

---

### Adicionar e commitar alterações

```
git add .
git commit -m "descrição objetiva do que foi feito"
```

---

### Subir alterações para o GitHub

```
git push origin feature/nome-da-feature
```

---

### Criar Pull Request (PR)

1. Acesse o repositório no GitHub
2. Vá na aba **Pull requests**
3. Clique em **New pull request**
4. Selecione:
   - base: `dev`
   - compare: `feature/nome-da-feature`
5. Envie o PR para que outro membro revise

---

### Atualizar branch com mudanças da `dev`

Se outras features foram integradas na `dev`, atualize a branch:

```
git checkout dev
git pull origin dev
git checkout feature/sua-branch
git merge dev
```

Resolva conflitos se aparecerem, depois continue o trabalho.

---

## Progresso
- As pastas model, view e controller indicam o padrão de projeto MVC, restam 2

## Proteções ativas

- `main`: protegida contra push direto
- `dev`: protegida contra push direto
- Toda contribuição deve ser feita via Pull Request (PR)
