package chess.piece;

import static chess.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.game.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KingTest {

    @ParameterizedTest(name = "from : {0}, to : {1}")
    @CsvSource(value = {"c2,c3", "c2,d3", "c2,d2", "c2,d1", "c2,c1", "c2,b1", "c2,b2", "c2,b3"})
    @DisplayName("킹이 8방향으로 움직일 수 있는지 확인한다.")
    void canKingMove(final String from, final String to) {
        final Piece piece = new King(WHITE);

        assertThat(piece.canMove(Position.of(from), Position.of(to))).isTrue();
    }

    @ParameterizedTest(name = "from : {0}, to : {1}")
    @CsvSource(value = {"c3,c5", "c3,e5", "c3,e3", "c3,e1", "c3,c1", "c3,a1", "c3,a3", "c2,a5"})
    @DisplayName("킹의 이동경로가 아닌지 확인한다.")
    void invalidKingMove(final String from, final String to) {
        final Piece piece = new King(WHITE);

        assertThat(piece.canMove(Position.of(from), Position.of(to))).isFalse();
    }
}

