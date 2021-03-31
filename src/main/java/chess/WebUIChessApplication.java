package chess;

import chess.repository.ChessRepository;
import chess.repository.ConnectionManager;
import chess.repository.RoomRepository;
import chess.service.ChessService;
import chess.service.RoomService;
import chess.web.ChessController;
import chess.web.ExceptionHandler;
import chess.web.MoveController;
import chess.web.RoomController;

import java.sql.Connection;

import static spark.Spark.*;

public class WebUIChessApplication {

    public static void main(String[] args) {
        Connection connection = ConnectionManager.makeConnection();
        ChessController chessController = initializeChessController(connection);
        RoomController roomController = initializeRoomController(connection);

        setConfiguration();
        get("/", MoveController::moveToMainPage);
        get("/chessgame/:roomId", MoveController::moveToGamePage);
        get("/result/:roomId", MoveController::moveToResultPage);
        get("/room/show", roomController::showRooms);
        post("/room/add", roomController::insertRoom);
        get("/chessgame/:roomId/show", chessController::showChessBoard);
        post("/chessgame/:roomId/move", chessController::move);
        get("/chessgame/:roomId/show/result", chessController::showResult);
        get("/chessgame/:roomId/restart", chessController::restart);
        exception(RuntimeException.class, ExceptionHandler::bindException);
    }

    private static void setConfiguration() {
        port(8080);
        staticFiles.location("/static");
    }

    private static ChessController initializeChessController(Connection connection) {
        ChessRepository chessRepository = new ChessRepository(connection);
        ChessService chessService = new ChessService(chessRepository);
        return new ChessController(chessService);
    }

    private static RoomController initializeRoomController(Connection connection) {
        RoomRepository roomRepository = new RoomRepository(connection);
        RoomService roomService = new RoomService(roomRepository);
        return new RoomController(roomService);
    }
}
