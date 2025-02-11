package chess.game;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.dto.CommandRequest;
import chess.state.Ready;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    @DisplayName("킹이 죽으면 게임이 종료된다.")
    void gameEnd() {
        Game game = new Game(Ready.start(Command.START));
        game.run(new CommandRequest("move", "b1", "c3"));
        game.run(new CommandRequest("move", "h7", "h6"));
        game.run(new CommandRequest("move", "c3", "d5"));
        game.run(new CommandRequest("move", "h6", "h5"));
        game.run(new CommandRequest("move", "d5", "c7"));
        game.run(new CommandRequest("move", "h5", "h4"));
        game.run(new CommandRequest("move", "c7", "e8"));

        assertThatThrownBy(() -> game.run(new CommandRequest("move", "h4", "h3")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임이 종료되었습니다.");
    }

    @Test
    @DisplayName("룩이 대각선으로 이동할 경우 예외를 발생한다.")
    void game() {
        Game game = new Game(Ready.start(Command.START));
        game.run(new CommandRequest("move", "b2", "b4"));
        game.run(new CommandRequest("move", "a7", "a6"));

        assertThatThrownBy(() -> game.run(new CommandRequest("move", "a1", "b2")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동이 불가능 합니다.");
    }
}
