package uno;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

import static uno.Mark.*;
import static uno.Mark.PF;

public class GameForThreeController implements Game{

    public List<GamePlayer> players = new ArrayList<>();

    public boolean isChangeColor = false;

    public boolean isWin = false;

    public Card currentCard;

    public Stack<Card> deck = new Stack<>();
    List<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE));
    List<Mark> marks = new ArrayList<>(Arrays.asList(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, PASS, TURN, CC, PT, PF));

    public void init(){
        players.add(new GamePlayer(this,"Gamer"));
        players.add(new GamePlayer(this,"P1"));
        players.add(new GamePlayer(this,"P2"));
        setDeck();
        giveCards();
    }

    public void setDeck(){
        for (int c = 0;c < colors.size(); c++){
            for (int i = 0; i < 10; i++){
                deck.add(new Card(marks.get(i), colors.get(c)));
            }
            for (int i = 1; i < 10; i++){
                deck.add(new Card(marks.get(i), colors.get(c)));
            }
            for (int i = 0; i < 2; i++){
                deck.add(new Card(PASS, colors.get(c)));
                deck.add(new Card(TURN, colors.get(c)));
                deck.add(new Card(PT, colors.get(c)));
            }
            deck.add(new Card(CC, Color.ALL));
            deck.add(new Card(PF, Color.ALL));
        }
        Collections.shuffle(deck);
    }

    public void giveCards(){
        for (int i = 0; i < 7; i++){
            players.stream()
                    .forEach(player -> player.addCard(deck.pop()));
        }
        currentCard = deck.pop();
        deck.add(0, currentCard);
        if(currentCard.color.equals(Color.ALL)){
            currentCard.color = Color.RED;
        }
    }

    public void giveCard(GamePlayer player) {
        player.addCard(deck.pop());
    }

    public Card getCurrent() {
        return currentCard;
    }

    public boolean isCorrect(Card card){
        if(card.color == Color.ALL || card.mark == currentCard.mark || card.color == currentCard.color){
            return true;
        }
        return false;
    }

    @Override
    public List<Card> getCards() {
        return deck;
    }

    public void reverse(){
        GamePlayer player = players.get(1);
        players.set(1, players.get(2));
        players.set(2, player);
    }

    @FXML
    Button current;

    @FXML
    HBox bottom;

    @FXML
    VBox left;

    @FXML
    VBox right;

    public String getImages(Card card){

        String images = "resource/";

        switch (card.color){
            case RED:   images += "red/"; break;
            case BLUE:  images += "blue/"; break;
            case GREEN:  images += "green/"; break;
            case YELLOW: images += "yellow/"; break;
        }

        switch (card.mark){
            case ZERO:      images += "zero.png"; break;
            case ONE:       images += "one.png"; break;
            case TWO:       images += "two.png"; break;
            case THREE:     images += "three.png"; break;
            case FOUR:      images += "four.png"; break;
            case FIVE:      images += "five.png"; break;
            case SIX:       images += "six.png"; break;
            case SEVEN:     images += "seven.png"; break;
            case EIGHT:     images += "eight.png"; break;
            case NINE:      images += "nine.png"; break;
            case PT:        images += "add-two.png"; break;
            case PASS:      images += "stop.png"; break;
            case TURN:      images += "reverse.png"; break;
            case CC:        images += "exchange.png"; break;
            case PF:        images += "exchange-one.png"; break;
        }

        Card color = new Card(CC, Color.ALL);
        Card four = new Card(PF, Color.ALL);

        if(card.equals(color) || card.equals(four)){
            switch (card.mark){
                case CC: images = "resource/exchange.png"; break;
                case PF: images = "resource/exchange-one.png"; break;
            }
        }
        return images;
    }


    public Button createButton(int size){
        String card = "resource/outer-side.png";
        Button button = new Button();
        button.setStyle("-fx-graphic: url("+ card +") ; -fx-padding: 0;");

        if(size <= 7){
            button.setMinWidth(10 * size);
            button.setMinHeight(10 * size);
        }else {
            button.setMinWidth(500 / size);
            button.setMinHeight(500 / size);
        }
        return button;
    }

    public void gamerButtonsInit(){
        players.get(0).sort();
        bottom.getChildren().clear();
        players.stream()
                .filter(player -> player.name.equals("Gamer"))
                .findFirst()
                .get()
                .cards.stream()
                .forEach(x -> {
                    Button button = new Button();
                    button.setId(x.mark + "\n" + x.color);
                    button.setStyle("-fx-graphic: url("+ getImages(x) +"); -fx-background-size: 150px 200px; -fx-padding: 0;");
                    if(players.get(0).cards.size() <= 7){
                        button.setMinWidth(10 * players.get(0).cards.size());
                        button.setMinHeight(10 * players.get(0).cards.size());
                    }else {
                        button.setMinWidth(500 / players.get(0).cards.size());
                        button.setMinHeight(500 / players.get(0).cards.size());
                    }
                    button.setOnMouseClicked(this::onMouseClick);
                    bottom.getChildren().add(button);
                });
    }

    public void p1ButtonsInit(){
        int size  = players.stream()
                .filter(player -> player.name.equals("P1"))
                .findFirst()
                .get()
                .cards.size();
        left.getChildren().clear();
        players.stream()
                .filter(player -> player.name.equals("P1"))
                .findFirst()
                .get()
                .cards.stream()
                .forEach(x -> left.getChildren().add(createButton(size)));
    }

    public void p2ButtonsInit(){
        int size  = players.stream()
                .filter(player -> player.name.equals("P2"))
                .findFirst()
                .get()
                .cards.size();
        right.getChildren().clear();
        players.stream()
                .filter(player -> player.name.equals("P2"))
                .findFirst()
                .get()
                .cards.stream()
                .forEach(x -> right.getChildren().add(createButton(size)));
    }

    @FXML
    public void initializeCardButton(){
        gamerButtonsInit();
        p1ButtonsInit();
        p2ButtonsInit();
    }

    public boolean isWin(GamePlayer player){
        String win = "resource/win.png";
        String lose = "resource/lose.png";
        if (player.cards.size() == 0){
            current.setMinHeight(233);
            current.setMinWidth(500);
            current.setLayoutX(500);
            if (player.name.equals("Gamer")){
                isWin = true;
                current.setStyle("-fx-background-image: url(" + win + ");-fx-background-size: 500px 233px; -fx-padding: 0; -fx-background-repeat: no-repeat; -fx-background-position: center;");
                return false;
            }else {
                isWin = true;
                current.setStyle("-fx-background-image: url(" + lose + ");-fx-background-size: 500px 233px; -fx-padding: 0; -fx-background-repeat: no-repeat; -fx-background-position: center;");
                return false;
            }
        }
        return true;
    }

    private void onMouseClick(MouseEvent event){
        if (isWin){
            newGame();
        }
        Node source = (Node)event.getSource();
        Button button = (Button) source;
        String c = button.getId();
        Card card = new Card(Mark.valueOf(c.split("\n")[0]), Color.valueOf(c.split("\n")[1]));
        boolean isContain = players.stream()
                .filter(player -> player.name.equals("Gamer"))
                .anyMatch(player -> player.cards.contains(card));
        if(isCorrect(card) && isContain){
            players.stream()
                    .filter(player -> player.name.equals("Gamer"))
                    .findFirst()
                    .get()
                    .removeCard(card);
            currentCard = card;
            deck.add(0, card);
            setCurrentButton(currentCard);
            updateCards();
            if (isWin(players.get(0))){
                startNewCircle();
            }

        }
    }

    public void p1Move(){
        Card card = players.get(1).putCard();
        if (isWin){
            newGame();
        }
        if(card != null){
            if (card.color == Color.ALL){
                card.color = players.get(1).getColor();
            }
            currentCard = card;
            players.get(1).removeCard(card);
            updateCards();

            if (isWin(players.get(1))) {
                switch (card.mark) {
                    case TURN:
                        reverse();
                        break;
                    case PT:
                        players.get(2).takeTwoCards();
                        break;
                    case PF:
                        players.get(2).takeFourCards();
                        break;
                    case PASS:
                        break;
                    default:
                        p2Move();
                }
            }
        }else {
            if (isWin(players.get(1))){
                p2Move();
            }
        }
    }

    public void p2Move(){
        Card card = players.get(2).putCard();
        if (isWin){
            newGame();
        }

        if(card != null){
            if (card.color == Color.ALL){
                card.color = players.get(2).getColor();
            }
            currentCard = card;
            players.get(2).removeCard(card);
            updateCards();

            if (isWin(players.get(2))){
                switch (card.mark){
                    case TURN: reverse(); p1Move();break;
                    case PT: players.get(0).takeTwoCards(); p1Move(); break;
                    case PF: players.get(0).takeFourCards(); p1Move(); break;
                    case PASS: p1Move(); break;
                    default: break;
                }
            }
        }
    }


    private void startNewCircle() {

        if(currentCard.color != Color.ALL){

            switch (currentCard.mark){
                case TURN: reverse(); p1Move();break;
                case PT: players.get(1).takeTwoCards();p2Move(); break;
                case PF: players.get(1).takeFourCards(); p2Move(); break;
                case PASS: p2Move(); break;
                default: p1Move();
            }

        }else {
            isChangeColor = true;
        }
    }

    public void setCurrentButton(Card card){
        current.setStyle("-fx-background-image: url(" + getImages(card) +");-fx-background-size: 150px 200px; -fx-padding: 0; -fx-background-repeat: no-repeat; -fx-background-position: center;");
    }

    public void updateCards(){
        initializeCardButton();
        setCurrentButton(deck.get(0));
    }


    @FXML
    public void setRed(){
        if(isChangeColor){
            currentCard.color = Color.RED;
            updateCards();
            isChangeColor = false;
            startNewCircle();
        }
    }

    @FXML
    public void setGreen(){
        if(isChangeColor){
            currentCard.color = Color.GREEN;
            updateCards();
            isChangeColor = false;
            startNewCircle();
        }
    }

    @FXML
    public void setYellow(){
        if(isChangeColor){
            currentCard.color = Color.YELLOW;
            updateCards();
            isChangeColor = false;
            startNewCircle();
        }
    }

    @FXML
    public void setBlue(){
        if(isChangeColor){
            currentCard.color = Color.BLUE;
            updateCards();
            isChangeColor = false;
            startNewCircle();
        }
    }

    @FXML
    public void getNext(){
        players.stream()
                .filter(player -> player.name.equals("Gamer"))
                .findFirst()
                .get()
                .takeCard();
        Optional<Card> card = players.get(0).findNextCard();
        if (card.isPresent()){
            currentCard = card.get();
            deck.add(0, card.get());
            players.get(0).removeCard(card.get());
            setCurrentButton(currentCard);
            updateCards();
            startNewCircle();
        }
        startNewCircle();
        updateCards();
    }

    public void initialize() {
        init();

        initializeCardButton();

        setCurrentButton(currentCard);
    }

    public void newGame(){
        if (isWin){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("main.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.stage.setScene(new Scene(root, 850, 450));
            Main.stage.show();
        }
    }

}
