package chess.piece;

import static chess.piece.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.game.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KnightTest {

    @ParameterizedTest(name = "from : {0}, to : {1}")
    @CsvSource(value = {"d4,e6", "d4,f5", "d4,f3", "d4,e2", "d4,c2", "d4,b3", "d4,b5", "d4,c6"})
    @DisplayName("나이트가 8방향으로 여러 칸 움직일 수 있는지 확인한다.")
    void canKnightMove(final String from, final String to) {
        final Piece piece = new Knight(WHITE);

        assertThat(piece.canMove(Position.of(from), Position.of(to))).isTrue();
    }

    @ParameterizedTest(name = "from : {0}, to : {1}")
    @CsvSource(value = {"d4,d8", "d4,h8", "d4,h4", "d4,g1", "d4,d1", "d4,a1", "d4,a4", "d4,a7"})
    @DisplayName("나이트의 이동경로가 아닌지 확인한다.")
    void invalidKnightMove(final String from, final String to) {
        final Piece piece = new Knight(WHITE);

        assertThat(piece.canMove(Position.of(from), Position.of(to))).isFalse();
    }

}
