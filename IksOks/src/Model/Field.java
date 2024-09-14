package Model;

import Util.MathRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Field {

    private String[][] fields = new String[3][3];
    private String[][] solutions = {
            {"00","01","02"},{"10","11","12"},{"20","21","22"},{"00","10","20"},
            {"01","11","21"},{"02","12","22"},{"00","11","22"},{"20","11","02"}

    };
    private List<String> botMoves = new ArrayList<>();
    private List<String> playerMoves = new ArrayList<>();

    public Field() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = "-";
            }
        }
    }

    public Field(String field) {}

    public String[][] getFields() {
        return fields;
    }


    public String[][] getSolutions() {
        return solutions;
    }

    public void printFields() {

        System.out.println("--------------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(fields[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void playGameOpponent() {
        boolean validMove = false;
        while (!validMove) {
            int[] randomPositions = MathRandom.generateRandomNumbers();
            String convertToStringPosition = String.valueOf(randomPositions[0]) + String.valueOf(randomPositions[1]);

            if (!botMoves.contains(convertToStringPosition) && !playerMoves.contains(convertToStringPosition)) {
                fields[randomPositions[0]][randomPositions[1]] = "x";
                botMoves.add(convertToStringPosition);
                validMove = true;
            }
        }

        this.printFields();

    }

    //recursion
//    public void playGameOpponent() {
//        System.out.println("Here opponent");
//
//        int[] randomPositions = generateValidBotMove();
//
//        String convertToStringPosition = String.valueOf(randomPositions[0]) + String.valueOf(randomPositions[1]);
//
//        fields[randomPositions[0]][randomPositions[1]] = "x";
//        botMoves.add(convertToStringPosition);
//
//        this.printFields();
//    }
//
//    private int[] generateValidBotMove() {
//        int[] randomPositions = MathRandom.generateRandomNumbers();
//        String position = String.valueOf(randomPositions[0]) + String.valueOf(randomPositions[1]);
//
//=        if (botMoves.contains(position) || playerMoves.contains(position)) {
//            return generateValidBotMove();
//        }
//
//        return randomPositions;
//    }



    public void playGame() {
        boolean gameOver = false;
        System.out.println("Game is about to start! You are playing against bot.");
        System.out.println("You are O, and bot is X.");
        System.out.println("When it's your turn to play, you will be asked to enter field you would like");
        System.out.println("For example: 11, 12 and 13. 11 is the top left corner, and 12 is first line, second element.");
        System.out.println("First number is index of row, and second is index of column.");
        System.out.println("Good luck!");

        while (!gameOver) {
            this.playGameOpponent();

            if (checkForWinner(botMoves)) {
                System.out.println("You lost!");
                gameOver = true;
                break;
            }

            if (botMoves.size() + playerMoves.size() == 9) {
                gameOver = true;
                System.out.println("Scores are equal!");
                break;
            }

            this.playAndValidateUserInput();

            if (checkForWinner(playerMoves)) {
                System.out.println("You won!");
                gameOver = true;
            }

            if (botMoves.size() + playerMoves.size() == 9) {
                gameOver = true;
                System.out.println("Scores are equal!");
            }
        }
    }


    private boolean checkForWinner(List<String> moves) {

//        System.out.println(Arrays.toString(solutions[0]) + " solutions");

        for (String[] solution : solutions) {
            if (moves.contains(solution[0]) &&
                    moves.contains(solution[1]) &&
                    moves.contains(solution[2])) {
                return true;
            }
        }
        return false;
    }


    public void playAndValidateUserInput() {
        boolean validMove = false;
        Scanner scannerMove = new Scanner(System.in);
        String regexMove = "^[1-3]{2}$";

        while (!validMove) {
            System.out.println("Enter your move: ");
            String move = scannerMove.nextLine();

            if (move.length() != 2) {
                System.out.println("Invalid input length. Please enter exactly two digits between 1 and 3.");
                continue;
            }

            if (!move.matches(regexMove)) {
                System.out.println("Please enter a valid move (two digits between 1 and 3)!");
                continue;
            }

            String moveForMatrix;
            try {
                moveForMatrix = String.valueOf(Integer.parseInt(move.substring(0, 1)) - 1) +
                        String.valueOf(Integer.parseInt(move.substring(1, 2)) - 1);

                if (botMoves.contains(moveForMatrix) || playerMoves.contains(moveForMatrix)) {
                    System.out.println("That field is already taken by computer or you! Please enter again!");
                    continue;
                } else {
                    fields[Integer.parseInt(move.substring(0, 1)) - 1][Integer.parseInt(move.substring(1, 2)) - 1] = "o";
                    playerMoves.add(moveForMatrix);
                    validMove = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter exactly two digits between 1 and 3.");
            }

            this.printFields();
        }
    }


}