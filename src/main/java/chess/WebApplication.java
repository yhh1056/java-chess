package chess;

import static spark.Spark.*;

import chess.dto.CommandRequest;
import chess.dto.response.BoardResult;
import chess.dto.response.PieceResult;
import chess.game.Command;
import chess.game.Game;
import chess.piece.Color;
import chess.status.Ready;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.Map;
import java.util.stream.Collectors;

public class WebApplication {
    public static void main(String[] args) {
        port(8082);
        staticFileLocation("/static");

        final Game game = new Game(Ready.start(Command.START));

        get("/", (req, res) -> render(new BoardResult(game.getBoard().getValue()).getValue(), "index.html"));

        post("/move", (req, res) -> {
            final String from = req.queryParams("from");
            final String to = req.queryParams("to");
            game.run(new CommandRequest("move", from, to));
            res.redirect("/");
            return null;
        });

        get("/score", (req, res) -> {
            final Map<Color, Double> score = game.run(new CommandRequest("status"));
            final Map<String, Double> scoreResult = score.keySet()
                    .stream()
                    .collect(Collectors.toMap(Enum::name, score::get));
            return new ModelAndView(scoreResult, "index.html");
        }, new JsonTransformer());
    }

    private static String render(final Map<String, PieceResult> model, final String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
