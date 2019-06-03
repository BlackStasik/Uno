package uno;

import java.util.Objects;

public class Card {

    public Mark mark;

    public Color color;

    public Card(Mark id, Color color) {
        this.mark = id;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return mark.equals(card.mark) &&
                color == card.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, color);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + mark + '\'' +
                ", color=" + color +
                '}';
    }
}
