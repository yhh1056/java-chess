package chess.controller;

import chess.dto.request.MoveRequest;
import chess.dto.response.BoardResult;
import chess.dto.response.ErrorResponse;
import chess.service.ChessService;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ChessController {

    private final Gson gson;
    private final ChessService chessService;

    public ChessController() {
        gson = new Gson();
        chessService = new ChessService();
    }

    public ModelAndView home(final Request request, final Response response) {
        return new ModelAndView(null, "index.html");
    }

    public ModelAndView start(final Request request, final Response response) {
        response.redirect("/game/" + chessService.initBoard());
        return null;
    }

    public ModelAndView move(final Request request, final Response response) {
        final Long boardId = getBoardId(request);
        final MoveRequest moveRequest = gson.fromJson(request.body(), MoveRequest.class);
        final String from = moveRequest.getFrom();
        final String to = moveRequest.getTo();
        try {
            chessService.move(boardId, from, to);
        } catch (final Exception e) {
            response.status(400);
            return new ModelAndView(new ErrorResponse(e.getMessage()), "game.html");
        }
        response.redirect("/game/" + boardId);
        return null;
    }

    public ModelAndView game(final Request request, final Response response) {
        final Long boardId = getBoardId(request);
        final BoardResult boardResult = chessService.findBoardById(boardId);
        return new ModelAndView(boardResult, "game.html");
    }

    public ModelAndView score(final Request request, final Response response) {
        final Long boardId = getBoardId(request);
        return new ModelAndView(chessService.getScore(boardId), "game.html");
    }

    private Long getBoardId(final Request request) {
        return Long.valueOf(request.params("boardId"));
    }
}

