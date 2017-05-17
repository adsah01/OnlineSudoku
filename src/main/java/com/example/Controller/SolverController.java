package com.example.Controller;

import com.example.Model.SudokuBox;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


/**
 * Styr upp annoteringar sen!!
 */
@Controller
public class SolverController {

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

    int[][] hintBox = new int[][]{
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

    @GetMapping("/")
    public ModelAndView hello(){

        SudokuBox sudokuBox = new SudokuBox("","","","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        return new ModelAndView("PuzzelTemplate")
                .addObject("box", sudokuBox);

    }

    @PostMapping("/updatedPuzzel")
    public ModelAndView sudokuToTest(@RequestParam String action, @Valid SudokuBox sudokuBox, BindingResult bru){
        if (bru.hasErrors()){
            if(action.equals("Rensa brädet")){
                resetBoxes(true);
                return new ModelAndView("PuzzelTemplate")
                        .addObject("box", returnSudokuBox(box));
            }
            return new ModelAndView("PuzzelTemplate")
                    .addObject("badBox", "Check your box kid, it's not correct..")
                    .addObject("box", sudokuBox);
        }
        setSudokuBox(sudokuBox);
        markStartingDigits();

        if(action.equals("Rensa brädet")){
            resetBoxes(true);
            return new ModelAndView("PuzzelTemplate")
                    .addObject("box", returnSudokuBox(box));
        }

        if(checkCheckBox()){
            if(sudokuSolver(0,0)){
                if(action.equals("Lös min Sudoku!")){
                    return new ModelAndView("PuzzelTemplate")
                            .addObject("box", returnSudokuBox(box));
                }


                return new ModelAndView("PuzzelTemplate")
                        .addObject("box", returnHint());
            }
        }

        resetBoxes(false);
        return new ModelAndView("PuzzelTemplate")
                .addObject("badBox", "Check your box kid, it's not correct..")
                .addObject("box", sudokuBox);

    }

    public void markStartingDigits(){
        for(int i = 0; i < box.length; i++){
            for(int j = 0; j < box[i].length; j++){
                if(box[i][j]!= 0){
                    checkBox[i][j] = 10;
                    hintBox[i][j] = box[i][j];
                }
            }
        }
    }

    public boolean checkCheckBox(){
        boolean possibleSudoku = true;
        boolean possibleBox = true;
        boolean possibleRow = true;

        for(int i = 0; i < hintBox.length; i++){
            for(int j = 0; j < hintBox[i].length; j++){
                if(hintBox[i][j] != 0){
                    possibleBox = possibleBox(i, j);
                    possibleRow = possibleRow(i, j);
                }
            }
        }if(!possibleBox || !possibleRow){
            possibleSudoku = false;
        }
        return possibleSudoku;
    }

    public boolean possibleBox(int i, int j){
        int boxXOffset = ((int) i / 3) * 3;
        int boxYOffset = ((int) j / 3) * 3;

        for (int xp = 0; xp < 3; xp++) {
            for (int yp = 0; yp < 3; yp++) {
                if(boxXOffset + xp != i || boxYOffset + yp != j){
                    if (hintBox[boxXOffset + xp][boxYOffset + yp] == hintBox[i][j]) {
                        return false;
                    }
                }
            }
        }return true;

    }

    public boolean possibleRow(int i, int j){
        for (int k = 0; k < box.length; k++) {
            if(k != i){
                if (hintBox[k][j] == hintBox[i][j]) {
                    return false;
                }
            }
            if(k != j){
                if (hintBox[i][k] == hintBox[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetBoxes(boolean resetBox){
        for(int i = 0; i < box.length; i++){
            for(int j = 0; j < box[i].length; j++){
                if(box[i][j]!= 0){
                    checkBox[i][j] = 0;
                    hintBox[i][j] = 0;
                    if(resetBox){
                        box[i][j] = 0;
                    }
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

    private void print(int x, int y /*, int num, int n*/) {
        /*System.out.println("Check ["+x+"][" + y + "] " + n);
        System.out.println("x: " + x + " y: " + y + " num=" + num);*/
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                System.out.print(box[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public boolean checkNumber(int x, int y, int n){
        return boxCondition(x,y,n) && rowCondition(x,y,n);
    }

    public boolean boxCondition(int x, int y, int n){
        int boxXOffset = ((int) x / 3) * 3;
        int boxYOffset = ((int) y / 3) * 3;

        for (int xp = 0; xp < 3; xp++) {
            for (int yp = 0; yp < 3; yp++) {
                if (box[boxXOffset + xp][boxYOffset + yp] == n) {
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

    public SudokuBox returnHint(){

        for(int i = 0; i < box.length; i++){
            for(int j = 0; j < box[i].length; j++){
                if(checkBox[i][j] == 0){
                    hintBox[i][j] = box[i][j];
                    return returnSudokuBox(hintBox);
                }
            }
        }return returnSudokuBox(checkBox);
    }

    public void setSudokuBox(SudokuBox sudokuBox){
        //Setting checkBox and hintBox
        for(int i = 0; i < checkBox.length; i++){
            for(int j = 0; j <checkBox[i].length; j++){
                checkBox[i][j] = 0;
                hintBox[i][j] = 0;
            }
        }
        //Row one
        if(sudokuBox.getDigitOne().equals("")){
            box[0][0] = 0;
        }else if(!sudokuBox.getDigitOne().equals("")){
            box[0][0] = Integer.parseInt(sudokuBox.getDigitOne());
        }
        if(sudokuBox.getDigitTwo().equals("")){
            box[0][1] = 0;
        }else if(!sudokuBox.getDigitTwo().equals("")){
            box[0][1] = Integer.parseInt(sudokuBox.getDigitTwo());
        }
        if(sudokuBox.getDigitThree().equals("")){
            box[0][2] = 0;
        }else if(!sudokuBox.getDigitThree().equals("")){
            box[0][2] = Integer.parseInt(sudokuBox.getDigitThree());
        }
        if(sudokuBox.getDigitFour().equals("")){
            box[0][3] = 0;
        }else if(!sudokuBox.getDigitFour().equals("")){
            box[0][3] = Integer.parseInt(sudokuBox.getDigitFour());
        }
        if(sudokuBox.getDigitFive().equals("")){
            box[0][4] = 0;
        }else if(!sudokuBox.getDigitFive().equals("")){
            box[0][4] = Integer.parseInt(sudokuBox.getDigitFive());
        }
        if(sudokuBox.getDigitSix().equals("")){
            box[0][5] = 0;
        }else if(!sudokuBox.getDigitSix().equals("")){
            box[0][5] = Integer.parseInt(sudokuBox.getDigitSix());
        }
        if(sudokuBox.getDigitSeven().equals("")){
            box[0][6] = 0;
        }else if(!sudokuBox.getDigitSeven().equals("")){
            box[0][6] = Integer.parseInt(sudokuBox.getDigitSeven());
        }
        if(sudokuBox.getDigitEight().equals("")){
            box[0][7] = 0;
        }else if(!sudokuBox.getDigitEight().equals("")){
            box[0][7] = Integer.parseInt(sudokuBox.getDigitEight());
        }
        if(sudokuBox.getDigitNine().equals("")){
            box[0][8] = 0;
        }else if(!sudokuBox.getDigitNine().equals("")){
            box[0][8] = Integer.parseInt(sudokuBox.getDigitNine());
        }

        //Row two

        if(sudokuBox.getDigitTen().equals("")){
            box[1][0] = 0;
        }else if(!sudokuBox.getDigitTen().equals("")){
            box[1][0] = Integer.parseInt(sudokuBox.getDigitTen());
        }
        if(sudokuBox.getDigitEleven().equals("")){
            box[1][1] = 0;
        }else if(!sudokuBox.getDigitEleven().equals("")){
            box[1][1] = Integer.parseInt(sudokuBox.getDigitEleven());
        }
        if(sudokuBox.getDigitTwelve().equals("")){
            box[1][2] = 0;
        }else if(!sudokuBox.getDigitTwelve().equals("")){
            box[1][2] = Integer.parseInt(sudokuBox.getDigitTwelve());
        }
        if(sudokuBox.getDigitThirteen().equals("")){
            box[1][3] = 0;
        }else if(!sudokuBox.getDigitThirteen().equals("")){
            box[1][3] = Integer.parseInt(sudokuBox.getDigitThirteen());
        }
        if(sudokuBox.getDigitFourteen().equals("")){
            box[1][4] = 0;
        }else if(!sudokuBox.getDigitFourteen().equals("")){
            box[1][4] = Integer.parseInt(sudokuBox.getDigitFourteen());
        }
        if(sudokuBox.getDigitFifteen().equals("")){
            box[1][5] = 0;
        }else if(!sudokuBox.getDigitFifteen().equals("")){
            box[1][5] = Integer.parseInt(sudokuBox.getDigitFifteen());
        }
        if(sudokuBox.getDigitSixteen().equals("")){
            box[1][6] = 0;
        }else if(!sudokuBox.getDigitSixteen().equals("")){
            box[1][6] = Integer.parseInt(sudokuBox.getDigitSixteen());
        }
        if(sudokuBox.getDigitSeventeen().equals("")){
            box[1][7] = 0;
        }else if(!sudokuBox.getDigitSeventeen().equals("")){
            box[1][7] = Integer.parseInt(sudokuBox.getDigitSeventeen());
        }
        if(sudokuBox.getDigitEighteen().equals("")){
            box[1][8] = 0;
        }else if(!sudokuBox.getDigitEighteen().equals("")){
            box[1][8] = Integer.parseInt(sudokuBox.getDigitEighteen());
        }

        //Row three

        if(sudokuBox.getDigitNineteen().equals("")){
            box[2][0] = 0;
        }else if(!sudokuBox.getDigitNineteen().equals("")){
            box[2][0] = Integer.parseInt(sudokuBox.getDigitNineteen());
        }
        if(sudokuBox.getDigitTwenty().equals("")){
            box[2][1] = 0;
        }else if(!sudokuBox.getDigitTwenty().equals("")){
            box[2][1] = Integer.parseInt(sudokuBox.getDigitTwenty());
        }
        if(sudokuBox.getDigitTwentyone().equals("")){
            box[2][2] = 0;
        }else if(!sudokuBox.getDigitTwentyone().equals("")){
            box[2][2] = Integer.parseInt(sudokuBox.getDigitTwentyone());
        }
        if(sudokuBox.getDigitTwentytwo().equals("")){
            box[2][3] = 0;
        }else if(!sudokuBox.getDigitTwentytwo().equals("")){
            box[2][3] = Integer.parseInt(sudokuBox.getDigitTwentytwo());
        }
        if(sudokuBox.getDigitTwentythree().equals("")){
            box[2][4] = 0;
        }else if(!sudokuBox.getDigitTwentythree().equals("")){
            box[2][4] = Integer.parseInt(sudokuBox.getDigitTwentythree());
        }
        if(sudokuBox.getDigitTwentyfour().equals("")){
            box[2][5] = 0;
        }else if(!sudokuBox.getDigitTwentyfour().equals("")){
            box[2][5] = Integer.parseInt(sudokuBox.getDigitTwentyfour());
        }
        if(sudokuBox.getDigitTwentyfive().equals("")){
            box[2][6] = 0;
        }else if(!sudokuBox.getDigitTwentyfive().equals("")){
            box[2][6] = Integer.parseInt(sudokuBox.getDigitTwentyfive());
        }
        if(sudokuBox.getDigitTwentysix().equals("")){
            box[2][7] = 0;
        }else if(!sudokuBox.getDigitTwentysix().equals("")){
            box[2][7] = Integer.parseInt(sudokuBox.getDigitTwentysix());
        }
        if(sudokuBox.getDigitTwentyseven().equals("")){
            box[2][8] = 0;
        }else if(!sudokuBox.getDigitTwentyseven().equals("")){
            box[2][8] = Integer.parseInt(sudokuBox.getDigitTwentyseven());
        }

        //Row four

        if(sudokuBox.getDigitTwentyeight().equals("")){
            box[3][0] = 0;
        }else if(!sudokuBox.getDigitTwentyeight().equals("")){
            box[3][0] = Integer.parseInt(sudokuBox.getDigitTwentyeight());
        }
        if(sudokuBox.getDigitTwentynine().equals("")){
            box[3][1] = 0;
        }else if(!sudokuBox.getDigitTwentynine().equals("")){
            box[3][1] = Integer.parseInt(sudokuBox.getDigitTwentynine());
        }
        if(sudokuBox.getDigitThirty().equals("")){
            box[3][2] = 0;
        }else if(!sudokuBox.getDigitThirty().equals("")){
            box[3][2] = Integer.parseInt(sudokuBox.getDigitThirty());
        }
        if(sudokuBox.getDigitThirtyone().equals("")){
            box[3][3] = 0;
        }else if(!sudokuBox.getDigitThirtyone().equals("")){
            box[3][3] = Integer.parseInt(sudokuBox.getDigitThirtyone());
        }
        if(sudokuBox.getDigitThirtytwo().equals("")){
            box[3][4] = 0;
        }else if(!sudokuBox.getDigitThirtytwo().equals("")){
            box[3][4] = Integer.parseInt(sudokuBox.getDigitThirtytwo());
        }
        if(sudokuBox.getDigitThirtythree().equals("")){
            box[3][5] = 0;
        }else if(!sudokuBox.getDigitThirtythree().equals("")){
            box[3][5] = Integer.parseInt(sudokuBox.getDigitThirtythree());
        }
        if(sudokuBox.getDigitThirtyfour().equals("")){
            box[3][6] = 0;
        }else if(!sudokuBox.getDigitThirtyfour().equals("")){
            box[3][6] = Integer.parseInt(sudokuBox.getDigitThirtyfour());
        }
        if(sudokuBox.getDigitThirtyfive().equals("")){
            box[3][7] = 0;
        }else if(!sudokuBox.getDigitThirtyfive().equals("")){
            box[3][7] = Integer.parseInt(sudokuBox.getDigitThirtyfive());
        }
        if(sudokuBox.getDigitThirtysix().equals("")){
            box[3][8] = 0;
        }else if(!sudokuBox.getDigitThirtysix().equals("")){
            box[3][8] = Integer.parseInt(sudokuBox.getDigitThirtysix());
        }


        //Row five

        if(sudokuBox.getDigitThirtyseven().equals("")){
            box[4][0] = 0;
        }else if(!sudokuBox.getDigitThirtyseven().equals("")){
            box[4][0] = Integer.parseInt(sudokuBox.getDigitThirtyseven());
        }
        if(sudokuBox.getDigitThirtyeight().equals("")){
            box[4][1] = 0;
        }else if(!sudokuBox.getDigitThirtyeight().equals("")){
            box[4][1] = Integer.parseInt(sudokuBox.getDigitThirtyeight());
        }
        if(sudokuBox.getDigitThirtynine().equals("")){
            box[4][2] = 0;
        }else if(!sudokuBox.getDigitThirtynine().equals("")){
            box[4][2] = Integer.parseInt(sudokuBox.getDigitThirtynine());
        }
        if(sudokuBox.getDigitForty().equals("")){
            box[4][3] = 0;
        }else if(!sudokuBox.getDigitForty().equals("")){
            box[4][3] = Integer.parseInt(sudokuBox.getDigitForty());
        }
        if(sudokuBox.getDigitFortyone().equals("")){
            box[4][4] = 0;
        }else if(!sudokuBox.getDigitFortyone().equals("")){
            box[4][4] = Integer.parseInt(sudokuBox.getDigitFortyone());
        }
        if(sudokuBox.getDigitFortytwo().equals("")){
            box[4][5] = 0;
        }else if(!sudokuBox.getDigitFortytwo().equals("")){
            box[4][5] = Integer.parseInt(sudokuBox.getDigitFortytwo());
        }
        if(sudokuBox.getDigitFortythree().equals("")){
            box[4][6] = 0;
        }else if(!sudokuBox.getDigitFortythree().equals("")){
            box[4][6] = Integer.parseInt(sudokuBox.getDigitFortythree());
        }
        if(sudokuBox.getDigitFortyfour().equals("")){
            box[4][7] = 0;
        }else if(!sudokuBox.getDigitFortyfour().equals("")){
            box[4][7] = Integer.parseInt(sudokuBox.getDigitFortyfour());
        }
        if(sudokuBox.getDigitFortyfive().equals("")){
            box[4][8] = 0;
        }else if(!sudokuBox.getDigitFortyfive().equals("")){
            box[4][8] = Integer.parseInt(sudokuBox.getDigitFortyfive());
        }

        //Row six

        if(sudokuBox.getDigitFortysix().equals("")){
            box[5][0] = 0;
        }else if(!sudokuBox.getDigitFortysix().equals("")){
            box[5][0] = Integer.parseInt(sudokuBox.getDigitFortysix());
        }
        if(sudokuBox.getDigitFortyseven().equals("")){
            box[5][1] = 0;
        }else if(!sudokuBox.getDigitFortyseven().equals("")){
            box[5][1] = Integer.parseInt(sudokuBox.getDigitFortyseven());
        }
        if(sudokuBox.getDigitFortyeight().equals("")){
            box[5][2] = 0;
        }else if(!sudokuBox.getDigitFortyeight().equals("")){
            box[5][2] = Integer.parseInt(sudokuBox.getDigitFortyeight());
        }if(sudokuBox.getDigitFortynine().equals("")){
            box[5][3] = 0;
        }else if(!sudokuBox.getDigitFortynine().equals("")){
            box[5][3] = Integer.parseInt(sudokuBox.getDigitFortynine());
        }if(sudokuBox.getDigitFifty().equals("")){
            box[5][4] = 0;
        }else if(!sudokuBox.getDigitFifty().equals("")){
            box[5][4] = Integer.parseInt(sudokuBox.getDigitFifty());
        }if(sudokuBox.getDigitFiftyone().equals("")){
            box[5][5] = 0;
        }else if(!sudokuBox.getDigitFiftyone().equals("")){
            box[5][5] = Integer.parseInt(sudokuBox.getDigitFiftyone());
        }if(sudokuBox.getDigitFiftytwo().equals("")){
            box[5][6] = 0;
        }else if(!sudokuBox.getDigitFiftytwo().equals("")){
            box[5][6] = Integer.parseInt(sudokuBox.getDigitFiftytwo());
        }if(sudokuBox.getDigitFiftythree().equals("")){
            box[5][7] = 0;
        }else if(!sudokuBox.getDigitFiftythree().equals("")){
            box[5][7] = Integer.parseInt(sudokuBox.getDigitFiftythree());
        }if(sudokuBox.getDigitFiftyfour().equals("")){
            box[5][8] = 0;
        }else if(!sudokuBox.getDigitFiftyfour().equals("")) {
            box[5][8] = Integer.parseInt(sudokuBox.getDigitFiftyfour());
        }

        //Row seven

        if(sudokuBox.getDigitFiftyfive().equals("")){
            box[6][0] = 0;
        }else if(!sudokuBox.getDigitFiftyfive().equals("")){
            box[6][0] = Integer.parseInt(sudokuBox.getDigitFiftyfive());
        }
        if(sudokuBox.getDigitFiftysix().equals("")){
            box[6][1] = 0;
        }else if(!sudokuBox.getDigitFiftysix().equals("")){
            box[6][1] = Integer.parseInt(sudokuBox.getDigitFiftysix());
        }
        if(sudokuBox.getDigitFiftyseven().equals("")){
            box[6][2] = 0;
        }else if(!sudokuBox.getDigitFiftyseven().equals("")){
            box[6][2] = Integer.parseInt(sudokuBox.getDigitFiftyseven());
        }if(sudokuBox.getDigitFiftyeight().equals("")){
            box[6][3] = 0;
        }else if(!sudokuBox.getDigitFiftyeight().equals("")){
            box[6][3] = Integer.parseInt(sudokuBox.getDigitFiftyeight());
        }if(sudokuBox.getDigitFiftynine().equals("")){
            box[6][4] = 0;
        }else if(!sudokuBox.getDigitFiftynine().equals("")){
            box[6][4] = Integer.parseInt(sudokuBox.getDigitFiftynine());
        }if(sudokuBox.getDigitSixty().equals("")){
            box[6][5] = 0;
        }else if(!sudokuBox.getDigitSixty().equals("")){
            box[6][5] = Integer.parseInt(sudokuBox.getDigitSixty());
        }if(sudokuBox.getDigitSixtyone().equals("")){
            box[6][6] = 0;
        }else if(!sudokuBox.getDigitSixtyone().equals("")){
            box[6][6] = Integer.parseInt(sudokuBox.getDigitSixtyone());
        }if(sudokuBox.getDigitSixtytwo().equals("")){
            box[6][7] = 0;
        }else if(!sudokuBox.getDigitSixtytwo().equals("")){
            box[6][7] = Integer.parseInt(sudokuBox.getDigitSixtytwo());
        }if(sudokuBox.getDigitSixtythree().equals("")){
            box[6][8] = 0;
        }else if(!sudokuBox.getDigitSixtythree().equals("")){
            box[6][8] = Integer.parseInt(sudokuBox.getDigitSixtythree());
        }


        //Row eight

        if(sudokuBox.getDigitSixtyfour().equals("")){
            box[7][0] = 0;
        }else if(!sudokuBox.getDigitSixtyfour().equals("")){
            box[7][0] = Integer.parseInt(sudokuBox.getDigitSixtyfour());
        }
        if(sudokuBox.getDigitSixtyfive().equals("")){
            box[7][1] = 0;
        }else if(!sudokuBox.getDigitSixtyfive().equals("")){
            box[7][1] = Integer.parseInt(sudokuBox.getDigitSixtyfive());
        }
        if(sudokuBox.getDigitSixtysix().equals("")){
            box[7][2] = 0;
        }else if(!sudokuBox.getDigitSixtysix().equals("")){
            box[7][2] = Integer.parseInt(sudokuBox.getDigitSixtysix());
        }if(sudokuBox.getDigitSixtyseven().equals("")){
            box[7][3] = 0;
        }else if(!sudokuBox.getDigitSixtyseven().equals("")){
            box[7][3] = Integer.parseInt(sudokuBox.getDigitSixtyseven());
        }if(sudokuBox.getDigitSixtyeight().equals("")){
            box[7][4] = 0;
        }else if(!sudokuBox.getDigitSixtyeight().equals("")){
            box[7][4] = Integer.parseInt(sudokuBox.getDigitSixtyeight());
        }if(sudokuBox.getDigitSixtynine().equals("")){
            box[7][5] = 0;
        }else if(!sudokuBox.getDigitSixtynine().equals("")){
            box[7][5] = Integer.parseInt(sudokuBox.getDigitSixtynine());
        }if(sudokuBox.getDigitSeventy().equals("")){
            box[7][6] = 0;
        }else if(!sudokuBox.getDigitSeventy().equals("")){
            box[7][6] = Integer.parseInt(sudokuBox.getDigitSeventy());
        }if(sudokuBox.getDigitSeventyone().equals("")){
            box[7][7] = 0;
        }else if(!sudokuBox.getDigitSeventyone().equals("")){
            box[7][7] = Integer.parseInt(sudokuBox.getDigitSeventyone());
        }if(sudokuBox.getDigitSeventytwo().equals("")){
            box[7][8] = 0;
        }else if(!sudokuBox.getDigitSeventytwo().equals("")){
            box[7][8] = Integer.parseInt(sudokuBox.getDigitSeventytwo());
        }

        //Row nine

        if(sudokuBox.getDigitSeventythree().equals("")){
            box[8][0] = 0;
        }else if(!sudokuBox.getDigitSeventythree().equals("")){
            box[8][0] = Integer.parseInt(sudokuBox.getDigitSeventythree());
        }
        if(sudokuBox.getDigitSeventyfour().equals("")){
            box[8][1] = 0;
        }else if(!sudokuBox.getDigitSeventyfour().equals("")){
            box[8][1] = Integer.parseInt(sudokuBox.getDigitSeventyfour());
        }
        if(sudokuBox.getDigitSeventyfive().equals("")){
            box[8][2] = 0;
        }else if(!sudokuBox.getDigitSeventyfive().equals("")){
            box[8][2] = Integer.parseInt(sudokuBox.getDigitSeventyfive());
        }
        if(sudokuBox.getDigitSeventysix().equals("")){
            box[8][3] = 0;
        }else if(!sudokuBox.getDigitSeventysix().equals("")){
            box[8][3] = Integer.parseInt(sudokuBox.getDigitSeventysix());
        }
        if(sudokuBox.getDigitSeventyseven().equals("")){
            box[8][4] = 0;
        }else if(!sudokuBox.getDigitSeventyseven().equals("")){
            box[8][4] = Integer.parseInt(sudokuBox.getDigitSeventyseven());
        }
        if(sudokuBox.getDigitSeventyeight().equals("")){
            box[8][5] = 0;
        }else if(!sudokuBox.getDigitSeventyeight().equals("")){
            box[8][5] = Integer.parseInt(sudokuBox.getDigitSeventyeight());
        }
        if(sudokuBox.getDigitSeventynine().equals("")){
            box[8][6] = 0;
        }else if(!sudokuBox.getDigitSeventynine().equals("")){
            box[8][6] = Integer.parseInt(sudokuBox.getDigitSeventynine());
        }
        if(sudokuBox.getDigitEighty().equals("")){
            box[8][7] = 0;
        }else if(!sudokuBox.getDigitEighty().equals("")){
            box[8][7] = Integer.parseInt(sudokuBox.getDigitEighty());
        }
        if(sudokuBox.getDigitEightyone().equals("")){
            box[8][8] = 0;
        }else if(!sudokuBox.getDigitEightyone().equals("")){
            box[8][8] = Integer.parseInt(sudokuBox.getDigitEightyone());
        }

    }

    public SudokuBox returnSudokuBox(int[][] puzzelBox){
        SudokuBox returnSudokuBox = new SudokuBox();
        //Row one
        if(puzzelBox[0][0] == 0){
            returnSudokuBox.setDigitOne("");
        }
        else if((puzzelBox[0][0] != 0)){
            returnSudokuBox.setDigitOne(String.valueOf(puzzelBox[0][0]));
        }
        if(puzzelBox[0][1] == 0){
            returnSudokuBox.setDigitTwo("");
        }
        else if((puzzelBox[0][1] != 0)){
            returnSudokuBox.setDigitTwo(String.valueOf(puzzelBox[0][1]));
        }

        if(puzzelBox[0][2] == 0){
            returnSudokuBox.setDigitThree("");
        }
        else if((puzzelBox[0][2] != 0)){
            returnSudokuBox.setDigitThree(String.valueOf(puzzelBox[0][2]));
        }

        if(puzzelBox[0][3] == 0){
            returnSudokuBox.setDigitFour("");
        }
        else if((puzzelBox[0][3] != 0)){
            returnSudokuBox.setDigitFour(String.valueOf(puzzelBox[0][3]));
        }
        if(puzzelBox[0][4] == 0){
            returnSudokuBox.setDigitFive("");
        }
        else if((puzzelBox[0][4] != 0)){
            returnSudokuBox.setDigitFive(String.valueOf(puzzelBox[0][4]));
        }
        if(puzzelBox[0][5] == 0){
            returnSudokuBox.setDigitSix("");
        }
        else if((puzzelBox[0][5] != 0)){
            returnSudokuBox.setDigitSix(String.valueOf(puzzelBox[0][5]));
        }
        if(puzzelBox[0][6] == 0){
            returnSudokuBox.setDigitSeven("");
        }
        else if((puzzelBox[0][6] != 0)){
            returnSudokuBox.setDigitSeven(String.valueOf(puzzelBox[0][6]));
        }
        if(puzzelBox[0][7] == 0){
            returnSudokuBox.setDigitEight("");
        }
        else if((puzzelBox[0][7] != 0)){
            returnSudokuBox.setDigitEight(String.valueOf(puzzelBox[0][7]));
        }
        if(puzzelBox[0][8] == 0){
            returnSudokuBox.setDigitNine("");
        }
        else if((puzzelBox[0][8] != 0)){
            returnSudokuBox.setDigitNine(String.valueOf(puzzelBox[0][8]));
        }

        //Row two

        if(puzzelBox[1][0] == 0){
            returnSudokuBox.setDigitTen("");
        }
        else if((puzzelBox[1][0] != 0)){
            returnSudokuBox.setDigitTen(String.valueOf(puzzelBox[1][0]));
        }
        if(puzzelBox[1][1] == 0){
            returnSudokuBox.setDigitEleven("");
        }
        else if((puzzelBox[1][1] != 0)){
            returnSudokuBox.setDigitEleven(String.valueOf(puzzelBox[1][1]));
        }
        if(puzzelBox[1][2] == 0){
            returnSudokuBox.setDigitTwelve("");
        }
        else if((puzzelBox[1][2] != 0)){
            returnSudokuBox.setDigitTwelve(String.valueOf(puzzelBox[1][2]));
        }
        if(puzzelBox[1][3] == 0){
            returnSudokuBox.setDigitThirteen("");
        }
        else if((puzzelBox[1][3] != 0)){
            returnSudokuBox.setDigitThirteen(String.valueOf(puzzelBox[1][3]));
        }
        if(puzzelBox[1][4] == 0){
            returnSudokuBox.setDigitFourteen("");
        }
        else if((puzzelBox[1][4] != 0)){
            returnSudokuBox.setDigitFourteen(String.valueOf(puzzelBox[1][4]));
        }
        if(puzzelBox[1][5] == 0){
            returnSudokuBox.setDigitFifteen("");
        }
        else if((puzzelBox[1][5] != 0)){
            returnSudokuBox.setDigitFifteen(String.valueOf(puzzelBox[1][5]));
        }
        if(puzzelBox[1][6] == 0){
            returnSudokuBox.setDigitSixteen("");
        }
        else if((puzzelBox[1][6] != 0)){
            returnSudokuBox.setDigitSixteen(String.valueOf(puzzelBox[1][6]));
        }
        if(puzzelBox[1][7] == 0){
            returnSudokuBox.setDigitSeventeen("");
        }
        else if((puzzelBox[1][7] != 0)){
            returnSudokuBox.setDigitSeventeen(String.valueOf(puzzelBox[1][7]));
        }
        if(puzzelBox[1][8] == 0){
            returnSudokuBox.setDigitEighteen("");
        }
        else if((puzzelBox[1][8] != 0)){
            returnSudokuBox.setDigitEighteen(String.valueOf(puzzelBox[1][8]));
        }

        //row three

        if(puzzelBox[2][0] == 0){
            returnSudokuBox.setDigitNineteen("");
        }
        else if((puzzelBox[2][0] != 0)){
            returnSudokuBox.setDigitNineteen(String.valueOf(puzzelBox[2][0]));
        }
        if(puzzelBox[2][1] == 0){
            returnSudokuBox.setDigitTwenty("");
        }
        else if((puzzelBox[2][1] != 0)){
            returnSudokuBox.setDigitTwenty(String.valueOf(puzzelBox[2][1]));
        }
        if(puzzelBox[2][2] == 0){
            returnSudokuBox.setDigitTwentyone("");
        }
        else if((puzzelBox[2][2] != 0)){
            returnSudokuBox.setDigitTwentyone(String.valueOf(puzzelBox[2][2]));
        }
        if(puzzelBox[2][3] == 0){
            returnSudokuBox.setDigitTwentytwo("");
        }
        else if((puzzelBox[2][3] != 0)){
            returnSudokuBox.setDigitTwentytwo(String.valueOf(puzzelBox[2][3]));
        }
        if(puzzelBox[2][4] == 0){
            returnSudokuBox.setDigitTwentythree("");
        }
        else if((puzzelBox[2][4] != 0)){
            returnSudokuBox.setDigitTwentythree(String.valueOf(puzzelBox[2][4]));
        }
        if(puzzelBox[2][5] == 0){
            returnSudokuBox.setDigitTwentyfour("");
        }
        else if((puzzelBox[2][5] != 0)){
            returnSudokuBox.setDigitTwentyfour(String.valueOf(puzzelBox[2][5]));
        }
        if(puzzelBox[2][6] == 0){
            returnSudokuBox.setDigitTwentyfive("");
        }
        else if((puzzelBox[2][6] != 0)){
            returnSudokuBox.setDigitTwentyfive(String.valueOf(puzzelBox[2][6]));
        }
        if(puzzelBox[2][7] == 0){
            returnSudokuBox.setDigitTwentysix("");
        }
        else if((puzzelBox[2][7] != 0)){
            returnSudokuBox.setDigitTwentysix(String.valueOf(puzzelBox[2][7]));
        }
        if(puzzelBox[2][8] == 0){
            returnSudokuBox.setDigitTwentyseven("");
        }
        else if((puzzelBox[2][8] != 0)){
            returnSudokuBox.setDigitTwentyseven(String.valueOf(puzzelBox[2][8]));
        }

        //Row four

        if(puzzelBox[3][0] == 0){
            returnSudokuBox.setDigitTwentyeight("");
        }
        else if((puzzelBox[3][0] != 0)){
            returnSudokuBox.setDigitTwentyeight(String.valueOf(puzzelBox[3][0]));
        }
        if(puzzelBox[3][1] == 0){
            returnSudokuBox.setDigitTwentynine("");
        }
        else if((puzzelBox[3][1] != 0)){
            returnSudokuBox.setDigitTwentynine(String.valueOf(puzzelBox[3][1]));
        }
        if(puzzelBox[3][2] == 0){
            returnSudokuBox.setDigitThirty("");
        }
        else if((puzzelBox[3][2] != 0)){
            returnSudokuBox.setDigitThirty(String.valueOf(puzzelBox[3][2]));
        }
        if(puzzelBox[3][3] == 0){
            returnSudokuBox.setDigitThirtyone("");
        }
        else if((puzzelBox[3][3] != 0)){
            returnSudokuBox.setDigitThirtyone(String.valueOf(puzzelBox[3][3]));
        }
        if(puzzelBox[3][4] == 0){
            returnSudokuBox.setDigitThirtytwo("");
        }
        else if((puzzelBox[3][4] != 0)){
            returnSudokuBox.setDigitThirtytwo(String.valueOf(puzzelBox[3][4]));
        }
        if(puzzelBox[3][5] == 0){
            returnSudokuBox.setDigitThirtythree("");
        }
        else if((puzzelBox[3][5] != 0)){
            returnSudokuBox.setDigitThirtythree(String.valueOf(puzzelBox[3][5]));
        }
        if(puzzelBox[3][6] == 0){
            returnSudokuBox.setDigitThirtyfour("");
        }
        else if((puzzelBox[3][6] != 0)){
            returnSudokuBox.setDigitThirtyfour(String.valueOf(puzzelBox[3][6]));
        }
        if(puzzelBox[3][7] == 0){
            returnSudokuBox.setDigitThirtyfive("");
        }
        else if((puzzelBox[3][7] != 0)){
            returnSudokuBox.setDigitThirtyfive(String.valueOf(puzzelBox[3][7]));
        }
        if(puzzelBox[3][8] == 0){
            returnSudokuBox.setDigitThirtysix("");
        }
        else if((puzzelBox[3][8] != 0)){
            returnSudokuBox.setDigitThirtysix(String.valueOf(puzzelBox[3][8]));
        }

        //Row five

        if(puzzelBox[4][0] == 0){
            returnSudokuBox.setDigitThirtyseven("");
        }
        else if((puzzelBox[4][0] != 0)){
            returnSudokuBox.setDigitThirtyseven(String.valueOf(puzzelBox[4][0]));
        }
        if(puzzelBox[4][1] == 0){
            returnSudokuBox.setDigitThirtyeight("");
        }
        else if((puzzelBox[4][1] != 0)){
            returnSudokuBox.setDigitThirtyeight(String.valueOf(puzzelBox[4][1]));
        }
        if(puzzelBox[4][2] == 0){
            returnSudokuBox.setDigitThirtynine("");
        }
        else if((puzzelBox[4][2] != 0)){
            returnSudokuBox.setDigitThirtynine(String.valueOf(puzzelBox[4][2]));
        }
        if(puzzelBox[4][3] == 0){
            returnSudokuBox.setDigitForty("");
        }
        else if((puzzelBox[4][3] != 0)){
            returnSudokuBox.setDigitForty(String.valueOf(puzzelBox[4][3]));
        }
        if(puzzelBox[4][4] == 0){
            returnSudokuBox.setDigitFortyone("");
        }
        else if((puzzelBox[4][4] != 0)){
            returnSudokuBox.setDigitFortyone(String.valueOf(puzzelBox[4][4]));
        }
        if(puzzelBox[4][5] == 0){
            returnSudokuBox.setDigitFortytwo("");
        }
        else if((puzzelBox[4][5] != 0)){
            returnSudokuBox.setDigitFortytwo(String.valueOf(puzzelBox[4][5]));
        }
        if(puzzelBox[4][6] == 0){
            returnSudokuBox.setDigitFortythree("");
        }
        else if((puzzelBox[4][6] != 0)){
            returnSudokuBox.setDigitFortythree(String.valueOf(puzzelBox[4][6]));
        }
        if(puzzelBox[4][7] == 0){
            returnSudokuBox.setDigitFortyfour("");
        }
        else if((puzzelBox[4][7] != 0)){
            returnSudokuBox.setDigitFortyfour(String.valueOf(puzzelBox[4][7]));
        }
        if(puzzelBox[4][8] == 0){
            returnSudokuBox.setDigitFortyfive("");
        }
        else if((puzzelBox[4][8] != 0)){
            returnSudokuBox.setDigitFortyfive(String.valueOf(puzzelBox[4][8]));
        }

        //Row six

        if(puzzelBox[5][0] == 0){
            returnSudokuBox.setDigitFortysix("");
        }
        else if((puzzelBox[5][0] != 0)){
            returnSudokuBox.setDigitFortysix(String.valueOf(puzzelBox[5][0]));
        }
        if(puzzelBox[5][1] == 0){
            returnSudokuBox.setDigitFortyseven("");
        }
        else if((puzzelBox[5][1] != 0)){
            returnSudokuBox.setDigitFortyseven(String.valueOf(puzzelBox[5][1]));
        }
        if(puzzelBox[5][2] == 0){
            returnSudokuBox.setDigitFortyeight("");
        }
        else if((puzzelBox[5][2] != 0)){
            returnSudokuBox.setDigitFortyeight(String.valueOf(puzzelBox[5][2]));
        }
        if(puzzelBox[5][3] == 0){
            returnSudokuBox.setDigitFortynine("");
        }
        else if((puzzelBox[5][3] != 0)){
            returnSudokuBox.setDigitFortynine(String.valueOf(puzzelBox[5][3]));
        }
        if(puzzelBox[5][4] == 0){
            returnSudokuBox.setDigitFifty("");
        }
        else if((puzzelBox[5][4] != 0)){
            returnSudokuBox.setDigitFifty(String.valueOf(puzzelBox[5][4]));
        }
        if(puzzelBox[5][5] == 0){
            returnSudokuBox.setDigitFiftyone("");
        }
        else if((puzzelBox[5][5] != 0)){
            returnSudokuBox.setDigitFiftyone(String.valueOf(puzzelBox[5][5]));
        }
        if(puzzelBox[5][6] == 0){
            returnSudokuBox.setDigitFiftytwo("");
        }
        else if((puzzelBox[5][6] != 0)){
            returnSudokuBox.setDigitFiftytwo(String.valueOf(puzzelBox[5][6]));
        }
        if(puzzelBox[5][7] == 0){
            returnSudokuBox.setDigitFiftythree("");
        }
        else if((puzzelBox[5][7] != 0)){
            returnSudokuBox.setDigitFiftythree(String.valueOf(puzzelBox[5][7]));
        }
        if(puzzelBox[5][8] == 0){
            returnSudokuBox.setDigitFiftyfour("");
        }
        else if((puzzelBox[5][8] != 0)){
            returnSudokuBox.setDigitFiftyfour(String.valueOf(puzzelBox[5][8]));
        }

        //Row seven

        if(puzzelBox[6][0] == 0){
            returnSudokuBox.setDigitFiftyfive("");
        }
        else if((puzzelBox[6][0] != 0)){
            returnSudokuBox.setDigitFiftyfive(String.valueOf(puzzelBox[6][0]));
        }
        if(puzzelBox[6][1] == 0){
            returnSudokuBox.setDigitFiftysix("");
        }
        else if((puzzelBox[6][1] != 0)){
            returnSudokuBox.setDigitFiftysix(String.valueOf(puzzelBox[6][1]));
        }
        if(puzzelBox[6][2] == 0){
            returnSudokuBox.setDigitFiftyseven("");
        }
        else if((puzzelBox[6][2] != 0)){
            returnSudokuBox.setDigitFiftyseven(String.valueOf(puzzelBox[6][2]));
        }
        if(puzzelBox[6][3] == 0){
            returnSudokuBox.setDigitFiftyeight("");
        }
        else if((puzzelBox[6][3] != 0)){
            returnSudokuBox.setDigitFiftyeight(String.valueOf(puzzelBox[6][3]));
        }
        if(puzzelBox[6][4] == 0){
            returnSudokuBox.setDigitFiftynine("");
        }
        else if((puzzelBox[6][4] != 0)){
            returnSudokuBox.setDigitFiftynine(String.valueOf(puzzelBox[6][4]));
        }
        if(puzzelBox[6][5] == 0){
            returnSudokuBox.setDigitSixty("");
        }
        else if((puzzelBox[6][5] != 0)){
            returnSudokuBox.setDigitSixty(String.valueOf(puzzelBox[6][5]));
        }
        if(puzzelBox[6][6] == 0){
            returnSudokuBox.setDigitSixtyone("");
        }
        else if((puzzelBox[6][6] != 0)){
            returnSudokuBox.setDigitSixtyone(String.valueOf(puzzelBox[6][6]));
        }
        if(puzzelBox[6][7] == 0){
            returnSudokuBox.setDigitSixtytwo("");
        }
        else if((puzzelBox[6][7] != 0)){
            returnSudokuBox.setDigitSixtytwo(String.valueOf(puzzelBox[6][7]));
        }
        if(puzzelBox[6][8] == 0){
            returnSudokuBox.setDigitSixtythree("");
        }
        else if((puzzelBox[6][8] != 0)){
            returnSudokuBox.setDigitSixtythree(String.valueOf(puzzelBox[6][8]));
        }

        //Row eight

        if(puzzelBox[7][0] == 0){
            returnSudokuBox.setDigitSixtyfour("");
        }
        else if((puzzelBox[7][0] != 0)){
            returnSudokuBox.setDigitSixtyfour(String.valueOf(puzzelBox[7][0]));
        }
        if(puzzelBox[7][1] == 0){
            returnSudokuBox.setDigitSixtyfive("");
        }
        else if((puzzelBox[7][1] != 0)){
            returnSudokuBox.setDigitSixtyfive(String.valueOf(puzzelBox[7][1]));
        }
        if(puzzelBox[7][2] == 0){
            returnSudokuBox.setDigitSixtysix("");
        }
        else if((puzzelBox[7][2] != 0)){
            returnSudokuBox.setDigitSixtysix(String.valueOf(puzzelBox[7][2]));
        }
        if(puzzelBox[7][3] == 0){
            returnSudokuBox.setDigitSixtyseven("");
        }
        else if((puzzelBox[7][3] != 0)){
            returnSudokuBox.setDigitSixtyseven(String.valueOf(puzzelBox[7][3]));
        }
        if(puzzelBox[7][4] == 0){
            returnSudokuBox.setDigitSixtyeight("");
        }
        else if((puzzelBox[7][4] != 0)){
            returnSudokuBox.setDigitSixtyeight(String.valueOf(puzzelBox[7][4]));
        }
        if(puzzelBox[7][5] == 0){
            returnSudokuBox.setDigitSixtynine("");
        }
        else if((puzzelBox[7][5] != 0)){
            returnSudokuBox.setDigitSixtynine(String.valueOf(puzzelBox[7][5]));
        }
        if(puzzelBox[7][6] == 0){
            returnSudokuBox.setDigitSeventy("");
        }
        else if((puzzelBox[7][6] != 0)){
            returnSudokuBox.setDigitSeventy(String.valueOf(puzzelBox[7][6]));
        }
        if(puzzelBox[7][7] == 0){
            returnSudokuBox.setDigitSeventyone("");
        }
        else if((puzzelBox[7][7] != 0)){
            returnSudokuBox.setDigitSeventyone(String.valueOf(puzzelBox[7][7]));
        }
        if(puzzelBox[7][8] == 0){
            returnSudokuBox.setDigitSeventytwo("");
        }
        else if((puzzelBox[7][8] != 0)){
            returnSudokuBox.setDigitSeventytwo(String.valueOf(puzzelBox[7][8]));
        }

        //Row nine

        if(puzzelBox[8][0] == 0){
            returnSudokuBox.setDigitSeventythree("");
        }
        else if((puzzelBox[8][0] != 0)){
            returnSudokuBox.setDigitSeventythree(String.valueOf(puzzelBox[8][0]));
        }
        if(puzzelBox[8][1] == 0){
            returnSudokuBox.setDigitSeventyfour("");
        }
        else if((puzzelBox[8][1] != 0)){
            returnSudokuBox.setDigitSeventyfour(String.valueOf(puzzelBox[8][1]));
        }
        if(puzzelBox[8][2] == 0){
            returnSudokuBox.setDigitSeventyfive("");
        }
        else if((puzzelBox[8][2] != 0)){
            returnSudokuBox.setDigitSeventyfive(String.valueOf(puzzelBox[8][2]));
        }
        if(puzzelBox[8][3] == 0){
            returnSudokuBox.setDigitSeventysix("");
        }
        else if((puzzelBox[8][3] != 0)){
            returnSudokuBox.setDigitSeventysix(String.valueOf(puzzelBox[8][3]));
        }
        if(puzzelBox[8][4] == 0){
            returnSudokuBox.setDigitSeventyseven("");
        }
        else if((puzzelBox[8][4] != 0)){
            returnSudokuBox.setDigitSeventyseven(String.valueOf(puzzelBox[8][4]));
        }
        if(puzzelBox[8][5] == 0){
            returnSudokuBox.setDigitSeventyeight("");
        }
        else if((puzzelBox[8][5] != 0)){
            returnSudokuBox.setDigitSeventyeight(String.valueOf(puzzelBox[8][5]));
        }
        if(puzzelBox[8][6] == 0){
            returnSudokuBox.setDigitSeventynine("");
        }
        else if((puzzelBox[8][6] != 0)){
            returnSudokuBox.setDigitSeventynine(String.valueOf(puzzelBox[8][6]));
        }
        if(puzzelBox[8][7] == 0){
            returnSudokuBox.setDigitEighty("");
        }
        else if((puzzelBox[8][7] != 0)){
            returnSudokuBox.setDigitEighty(String.valueOf(puzzelBox[8][7]));
        }
        if(puzzelBox[8][8] == 0){
            returnSudokuBox.setDigitEightyone("");
        }
        else if((puzzelBox[8][8] != 0)){
            returnSudokuBox.setDigitEightyone(String.valueOf(puzzelBox[8][8]));
        }

        return returnSudokuBox;
    }

}
