package uno;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GamePlayer {

    public String name;

    public List<Card> cards = new ArrayList<>();

    public Game game;

    public GamePlayer(Game game, String name) {
        this.name = name;
        this.game = game;
    }

    public void sort(){
        cards.sort(Comparator.comparing(x -> x.mark));
        cards.sort(Comparator.comparing(x -> x.color));
    }


    public void addCard(Card card) {
        cards.add(card);
    }


    public void removeCard(Card card) {
        cards.remove(card);
    }

    public Optional<Card> findNextCard(){
        return cards.stream()
                .filter(card -> card.color == Color.ALL || card.mark.equals(game.getCurrent().mark) || card.color == game.getCurrent().color)
                .findAny();
    }

    public Card putCard() {
        if (findNextCard().isPresent()){
            game.getCards().add(0, findNextCard().get());
            return findNextCard().get();
        }else {
            takeCard();
            if (findNextCard().isPresent()){
                game.getCards().add(0, findNextCard().get());
                return findNextCard().get();
            }else {
                return null;
            }
        }
    }

    public Color getColor(){
        Card card = cards.stream().
                filter(c -> c.color != Color.ALL)
                .findFirst()
                .get();
        if (card != null){
            return card.color;
        }else {
            return Color.BLUE;
        }
    }

    public void takeTwoCards() {
        game.giveCard(this);
        game.giveCard(this);
    }
    public void takeFourCards() {
        game.giveCard(this);
        game.giveCard(this);
        game.giveCard(this);
        game.giveCard(this);
    }

    public void takeCard() {
        game.giveCard(this);
    }


    @Override
    public String toString() {
        return "Player{" +
                ", cards=" + cards +
                '}';
    }
}
