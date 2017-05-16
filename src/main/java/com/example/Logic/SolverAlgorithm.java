package com.example.Logic;

import org.springframework.stereotype.Component;

@Component
public class SolverAlgorithm {

    static int[][] box = new int[][]{
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    int[][] checkBox = new int[][]{
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public static void main(String[] args) {
        // write your code here

    }

    /*public void solveSudoku(SudokuBox sudokuBox){
        System.out.println("det funkade att starta metoden");

        setSudokuBox(sudokuBox);
        markStartingDigits();
        sudokuSolver(0,0);
        System.out.println("Grattis! Här har du din sudoku!!");
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                System.out.print(box[i][j] + " ");
            }
            System.out.println();
        }
        //return returnSudokuBox();
    }


    public void markStartingDigits(){
        for(int i = 0; i < box.length; i++){
            for(int j = 0; j < box[i].length; j++){
                if(box[i][j]!= 0){
                    checkBox[i][j] = 10;
                }
            }
        }
    }


    public boolean sudokuSolver(int x, int y) {
        if (solved()) {
            return true;
        }

        if (checkBox[x][y] == 10) {
            return tryNextSudokuSolver(x, y);
        } else {
            for (int n = 1; n <= 9; n++) {
                if (checkNumber(x, y, n)) {
                    box[x][y] = n; // try this number
                    //print(x, y, num, n);

                    if (tryNextSudokuSolver(x, y)) {
                        return true;
                    }
                    box[x][y] = 0; // wrong number, unset
                }
            }
            return false;
        }
    }
    private boolean tryNextSudokuSolver(int x, int y) {
        if (y == 8) {
            y = 0;
            x += 1;
        } else {
            y += 1;
        }
        return sudokuSolver(x, y);
    }

    private boolean solved() {
        int count = 0;
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                if (box[i][j] != 0) {
                    count += 1;
                }
            }
        }
        return count == 81;
    }

    private void print(int x, int y, int num, int n) {
        System.out.println("Check ["+x+"][" + y + "] " + n);
        System.out.println("x: " + x + " y: " + y + " num=" + num);
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                System.out.print(box[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public boolean checkNumber(int x, int y, int num){
        return boxCondition(x,y,num) && rowCondition(x,y,num);
    }

    public boolean boxCondition(int x, int y, int num){
        int boxXOffset = ((int) x / 3) * 3;
        int boxYOffset = ((int) y / 3) * 3;

        for (int xp = 0; xp < 3; xp++) {
            for (int yp = 0; yp < 3; yp++) {
                if (box[boxXOffset + xp][boxYOffset + yp] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean rowCondition(int x, int y, int num){
        for (int i = 0; i < box.length; i++) {
            if (box[i][y] == num) {
                return false;
            }
            if (box[x][i] == num) {
                return false;
            }
        }
        return true;
    }

    public void setSudokuBox(SudokuBox sudokuBox){
    if(sudokuBox.getDigitOne() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitOne());
    }
    if(sudokuBox.getDigitTwo() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitTwo());
    }
    if(sudokuBox.getDigitThree() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitThree());
    }
    if(sudokuBox.getDigitFour() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitFour());
    }
    if(sudokuBox.getDigitFive() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitFive());
    }
    if(sudokuBox.getDigitSix() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitSix());
    }
    if(sudokuBox.getDigitSeven() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitSeven());
    }
    if(sudokuBox.getDigitEight() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitEight());
    }

    if(sudokuBox.getDigitNine() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitNine());
    }
    if(sudokuBox.getDigitTen() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitTen());
    }
    if(sudokuBox.getDigitEleven() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitEleven());
    }
    if(sudokuBox.getDigitTwelve() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitTwelve());
    }
    if(sudokuBox.getDigitThirteen() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitThirteen());
    }
    if(sudokuBox.getDigitFourteen() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitFourteen());
    }
    if(sudokuBox.getDigitFifteen() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitFifteen());
    }
    if(sudokuBox.getDigitEighteen() == ""){
        box[0][0] = 0;
    }else{
        box[0][0] = Integer.parseInt(sudokuBox.getDigitEighteen());
    }
    }

    public SudokuBox returnSudokuBox(){
    SudokuBox solvedSudokuBox = new SudokuBox();
    solvedSudokuBox.setDigitOne(String.valueOf(box[0][0]));
    solvedSudokuBox.setDigitTwo(String.valueOf(box[1][0]));
    solvedSudokuBox.setDigitThree(String.valueOf(box[2][0]));
    solvedSudokuBox.setDigitFour(String.valueOf(box[3][0]));
    solvedSudokuBox.setDigitFive(String.valueOf(box[4][0]));
    solvedSudokuBox.setDigitSix(String.valueOf(box[5][0]));
    solvedSudokuBox.setDigitSeven(String.valueOf(box[6][0]));
    solvedSudokuBox.setDigitEight(String.valueOf(box[7][0]));
    solvedSudokuBox.setDigitNine(String.valueOf(box[8][0]));
    solvedSudokuBox.setDigitTen(String.valueOf(box[0][1]));
    solvedSudokuBox.setDigitEleven(String.valueOf(box[1][1]));
    solvedSudokuBox.setDigitTwelve(String.valueOf(box[2][1]));
    solvedSudokuBox.setDigitThirteen(String.valueOf(box[3][1]));
    solvedSudokuBox.setDigitFourteen(String.valueOf(box[4][1]));
    solvedSudokuBox.setDigitFifteen(String.valueOf(box[5][1]));
    solvedSudokuBox.setDigitSixteen(String.valueOf(box[6][1]));
    solvedSudokuBox.setDigitSeventeen(String.valueOf(box[7][1]));
    solvedSudokuBox.setDigitEighteen(String.valueOf(box[8][1]));
    solvedSudokuBox.setDigitNineteen(String.valueOf(box[0][2]));
    solvedSudokuBox.setDigitTwenty(String.valueOf(box[1][2]));
    solvedSudokuBox.setDigitTwentyone(String.valueOf(box[2][2]));
    solvedSudokuBox.setDigitTwentytwo(String.valueOf(box[3][2]));
    solvedSudokuBox.setDigitTwentythree(String.valueOf(box[4][2]));
    solvedSudokuBox.setDigitTwentyfour(String.valueOf(box[5][2]));
    solvedSudokuBox.setDigitTwentyfive(String.valueOf(box[6][2]));
    solvedSudokuBox.setDigitTwentysix(String.valueOf(box[7][2]));
    solvedSudokuBox.setDigitTwentyseven(String.valueOf(box[8][2]));
    solvedSudokuBox.setDigitTwentyeight(String.valueOf(box[0][3]));
    solvedSudokuBox.setDigitTwentynine(String.valueOf(box[1][3]));
    solvedSudokuBox.setDigitThirty(String.valueOf(box[2][3]));
    solvedSudokuBox.setDigitThirtyone(String.valueOf(box[3][3]));
    solvedSudokuBox.setDigitThirtytwo(String.valueOf(box[4][3]));
    solvedSudokuBox.setDigitThirtythree(String.valueOf(box[5][3]));
    solvedSudokuBox.setDigitThirtyfour(String.valueOf(box[6][3]));
    solvedSudokuBox.setDigitThirtyfive(String.valueOf(box[7][3]));
    solvedSudokuBox.setDigitThirtysix(String.valueOf(box[8][3]));
    solvedSudokuBox.setDigitThirtyseven(String.valueOf(box[0][4]));
    solvedSudokuBox.setDigitThirtyeight(String.valueOf(box[1][4]));
    solvedSudokuBox.setDigitThirtynine(String.valueOf(box[2][4]));
    solvedSudokuBox.setDigitForty(String.valueOf(box[3][4]));
    solvedSudokuBox.setDigitFortyone(String.valueOf(box[4][4]));
    solvedSudokuBox.setDigitFortytwo(String.valueOf(box[5][4]));
    solvedSudokuBox.setDigitFortythree(String.valueOf(box[6][4]));
    solvedSudokuBox.setDigitFortyfour(String.valueOf(box[7][4]));
    solvedSudokuBox.setDigitFortyfive(String.valueOf(box[8][4]));
    solvedSudokuBox.setDigitFortysix(String.valueOf(box[0][5]));
    solvedSudokuBox.setDigitFortyseven(String.valueOf(box[1][5]));
    solvedSudokuBox.setDigitFortyeight(String.valueOf(box[2][5]));
    solvedSudokuBox.setDigitFortynine(String.valueOf(box[3][5]));
    solvedSudokuBox.setDigitFifty(String.valueOf(box[4][5]));
    solvedSudokuBox.setDigitFiftyone(String.valueOf(box[5][5]));
    solvedSudokuBox.setDigitFiftytwo(String.valueOf(box[6][5]));
    solvedSudokuBox.setDigitFiftythree(String.valueOf(box[7][5]));
    solvedSudokuBox.setDigitFiftyfour(String.valueOf(box[8][5]));
    solvedSudokuBox.setDigitFiftyfive(String.valueOf(box[0][6]));
    solvedSudokuBox.setDigitFiftysix(String.valueOf(box[1][6]));
    solvedSudokuBox.setDigitFiftyseven(String.valueOf(box[2][6]));
    solvedSudokuBox.setDigitFiftyeight(String.valueOf(box[3][6]));
    solvedSudokuBox.setDigitFiftynine(String.valueOf(box[4][6]));
    solvedSudokuBox.setDigitSixty(String.valueOf(box[5][6]));
    solvedSudokuBox.setDigitSixtyone(String.valueOf(box[6][6]));
    solvedSudokuBox.setDigitSixtytwo(String.valueOf(box[7][6]));
    solvedSudokuBox.setDigitSixtythree(String.valueOf(box[8][6]));
    solvedSudokuBox.setDigitSixtyfour(String.valueOf(box[0][7]));
    solvedSudokuBox.setDigitSixtyfive(String.valueOf(box[1][7]));
    solvedSudokuBox.setDigitSixtysix(String.valueOf(box[2][7]));
    solvedSudokuBox.setDigitSixtyseven(String.valueOf(box[3][7]));
    solvedSudokuBox.setDigitSixtyeight(String.valueOf(box[4][7]));
    solvedSudokuBox.setDigitSixtynine(String.valueOf(box[5][7]));
    solvedSudokuBox.setDigitSeventy(String.valueOf(box[6][7]));
    solvedSudokuBox.setDigitSeventyone(String.valueOf(box[7][7]));
    solvedSudokuBox.setDigitSeventytwo(String.valueOf(box[8][7]));
    solvedSudokuBox.setDigitSeventythree(String.valueOf(box[0][8]));
    solvedSudokuBox.setDigitSeventyfour(String.valueOf(box[1][8]));
    solvedSudokuBox.setDigitSeventyfive(String.valueOf(box[2][8]));
    solvedSudokuBox.setDigitSeventysix(String.valueOf(box[3][8]));
    solvedSudokuBox.setDigitSeventyseven(String.valueOf(box[4][8]));
    solvedSudokuBox.setDigitSeventyeight(String.valueOf(box[5][8]));
    solvedSudokuBox.setDigitSeventynine(String.valueOf(box[6][8]));
    solvedSudokuBox.setDigitEighty(String.valueOf(box[7][8]));
    solvedSudokuBox.setDigitEightyone(String.valueOf(box[8][8]));
    return solvedSudokuBox;
    }*/

}