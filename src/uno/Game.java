package uno;

import java.util.List;

public interface Game {

    public Card getCurrent();

    public List<Card> getCards();

    public void giveCard(GamePlayer player);

}
