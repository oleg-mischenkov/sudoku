package com.mischenkov.sudoku.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mischenkov.sudoku.service.SudokuService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import static com.mischenkov.sudoku.listener.ContextVariable.SUDOKU_SERVICE;

@WebServlet("/")
public class HomeController extends HttpServlet {

    private static final String SESSION_SUDOKU_MATRIX = "sudokuMatrix";
    private static final String SESSION_SUDOKU_OUT_MATRIX = "outSudokuMatrix";
    private static final String JSON_KEY_MATRIX = "matrix";
    private static final String JSON_KEY_ERROR_MSG = "errorMessage";

    private static final Logger LOG = Logger.getLogger(HomeController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int[][] sudokuMatrix;
        var session = req.getSession(true);
        ObjectMapper mapper = new ObjectMapper();

        LOG.info("### Home Controller Start !!!");

        if (session.isNew()) {
            var sudokuService = (SudokuService) req.getServletContext().getAttribute(SUDOKU_SERVICE);
            sudokuMatrix = sudokuService.getSudoku();
            var sudokuOutMatrix = sudokuService.getSudokuSolution();

            session.setAttribute(SESSION_SUDOKU_MATRIX, sudokuMatrix);
            session.setAttribute(SESSION_SUDOKU_OUT_MATRIX, sudokuOutMatrix);

        } else {
            sudokuMatrix = (int[][]) session.getAttribute(SESSION_SUDOKU_MATRIX);
        }

        String json = mapper.writeValueAsString(sudokuMatrix);

        sendJson(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("## doPOST start!!!");
        String resultJson;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(req.getInputStream(), Map.class);

        var session = req.getSession();
        var sudokuMatrix = (int[][]) session.getAttribute(SESSION_SUDOKU_MATRIX);
        var outMatrix = (int[][]) session.getAttribute(SESSION_SUDOKU_OUT_MATRIX);

        var preValue = (String) data.get("value");
        int value;

        if (isPositiveInt(preValue)) {
            value = Integer.parseInt(preValue);
            var xPosition = (int) data.get("x");
            var yPosition = (int) data.get("y");


            if (isSudokuAnswerCorrect(xPosition, yPosition, value, outMatrix)) {
                sudokuMatrix[xPosition][yPosition] = value;

                session.setAttribute(SESSION_SUDOKU_MATRIX, sudokuMatrix);
                resultJson = mapper.writeValueAsString(Map.of(JSON_KEY_MATRIX, sudokuMatrix));
            } else {
                resultJson = mapper.writeValueAsString(Map.of(
                        JSON_KEY_MATRIX, sudokuMatrix,
                        JSON_KEY_ERROR_MSG, "Your answer [" + value + "] is incorrect."));
            }

        } else {
            resultJson = mapper.writeValueAsString(Map.of(
                    JSON_KEY_MATRIX, sudokuMatrix,
                    JSON_KEY_ERROR_MSG, "Invalid value [" + preValue + "]. You must enter only positive integers."));
        }

        sendJson(resp, resultJson);
    }

    private static void sendJson(HttpServletResponse resp, String resultJson) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(resultJson);
        resp.flushBuffer();
    }

    private boolean isSudokuAnswerCorrect(int xPosition, int yPosition, int value, int[][] outMatrix) {
        return outMatrix[xPosition][yPosition] == value;
    }

    private static boolean isPositiveInt(String value) {
        return Pattern.compile("^\\d+$").matcher(value).matches();
    }
}
