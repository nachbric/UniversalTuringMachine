package UniversalTuringMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// transition        t1  t2  t3   t4  t5  t6  t7   t8  t9  t10 t11 t12 t13 t14 t15 t16 t17 t18 t19  t20 t21  t22  t23  t24  t25  t26  t27  t28  t29  t30  t31  t32  t33  t34
// current State    | 0 | 0 | 1  | 1 | 2 | 2 | 3  | 3 | 4 | 4 | 5 | 5 | 6 | 6 | 7 | 7 | 8 | 8 | 9  | 9 | 10 | 10 | 11 | 11 | 12 | 12 | 13 | 13 | 14 | 14 | 15 | 15 | 16 | 16 |
// current Band Sym | _ | x | _  | x | _ | x | _  | x | _ | x | _ | x | _ | x | _ | x | _ | x | _  | x | _  | x  | _  | x  | _  | x  | _  | x  | _  | x  | _  | x  | _  | x  |
// next State       | 1 | 2 | 14 | 2 | 3 | 2 | 15 | 4 | 5 | 4 | 6 | 5 | 7 | 6 | 9 | 8 | 3 | 8 | 10 | 9 | 12 | 11 | 0  | 11 | 12 | 13 | H  | 13 | H  | 14 | 16 | 15 | H  | 16 |
// next Band Sym    | _ | _ | _  | _ | _ | x | _  | _ | _ | x | x | x | _ | x | x | x | x | x | _  | x | _  | x  | _  | x  | _  | _  | _  | _  | _  | _  | _  | _  | _  | _  |
// move dir         | R | R | R  | R | R | R | L  | R | R | R | L | R | L | L | L | L | R | L | L  | L | R  | L  | R  | L  | R  | R  | -  | R  | -  | R  | L  | L  | -  | L  |

// x := 0 // _ := 00    // L := 0 // R := 00 // - := 000

// t1  := 01001000000000000000000100100 // t2  := 0101000100100 // t3  := 0000000000000000001001000000000000000100100 // t4  := 000000000000000000101000100100 // t5  := 00010010000100100 // t6  := 00010100010100 // t7  := 00001001000000000000000010010 // t8  := 000010100000100100 // t9  := 000001001000000100100 // t10 := 000001010000010100 // t11 := 000000100100000001010 // t12 := 00000010100000010100 // t13 := 000000010010000000010010 // t14 := 000000010100000001010 // t15 := 00000000100100000000001010 // t16 := 000000001010000000001010 // t17 := 0000000001001000010100 // t18 := 0000000001010000000001010 // t19 := 000000000010010000000000010010 // t20 := 000000000010100000000001010 // t21 := 0000000000010010000000000000100100 // t22 := 000000000001010000000000001010 // t23 := 00000000000010010100100 // t24 := 0000000000001010000000000001010 // t25 := 000000000000010010000000000000100100 // t26 := 000000000000010100000000000000100100 // t27 := 000000000000001001001001000 // t28 := 0000000000000010100000000000000100100 // t29 := 0000000000000001001001001000 // t30 := 000000000000000101000000000000000100100 // t31 := 000000000000000010010000000000000000010010 // t32 := 0000000000000000101000000000000000010010 // t33 := 000000000000000001001001001000 // t34 := 000000000000000001010000000000000000010010

// tm  := 01001000000000000000000100100110101000100100110000000000000000001001000000000000000100100110000000000000000001010001001001100010010000100100110001010001010011000010010000000000000000100101100001010000010010011000001001000000100100110000010100000101001100000010010000000101011000000101000000101001100000001001000000001001011000000010100000001010110000000010010000000000101011000000001010000000001010110000000001001000010100110000000001010000000001010110000000000100100000000000100101100000000001010000000000101011000000000001001000000000000010010011000000000001010000000000001010110000000000001001010010011000000000000101000000000000101011000000000000010010000000000000100100110000000000000101000000000000001001001100000000000000100100100100011000000000000001010000000000000010010011000000000000000100100100100011000000000000000101000000000000000100100110000000000000000100100000000000000000100101100000000000000001010000000000000000100101100000000000000000100100100100011000000000000000001010000000000000000010010

// 0  := 0
// 1  := 00
// 2  := 000
// 3  := 0000
// 4  := 00000
// 5  := 000000
// 6  := 0000000
// 7  := 00000000
// 8  := 000000000
// 9  := 0000000000
// 10 := 00000000000
// 11 := 000000000000
// 12 := 0000000000000
// 13 := 00000000000000
// 14 := 000000000000000
// 15 := 0000000000000000
// 16 := 00000000000000000

// x := 0
// _ := 00

// L := 0
// R := 00
// - := 000

public class Main {
    private static TuringMachine<Character> TM;

    public static void main(String[] args) {
        String tmString = "01001000000000000000000100100110101000100100110000000000000000001001000000000000000100100110000000000000000001010001001001100010010000100100110001010001010011000010010000000000000000100101100001010000010010011000001001000000100100110000010100000101001100000010010000000101011000000101000000101001100000001001000000001001011000000010100000001010110000000010010000000000101011000000001010000000001010110000000001001000010100110000000001010000000001010110000000000100100000000000100101100000000001010000000000101011000000000001001000000000000010010011000000000001010000000000001010110000000000001001010010011000000000000101000000000000101011000000000000010010000000000000100100110000000000000101000000000000001001001100000000000000100100100100011000000000000001010000000000000010010011000000000000000100100100100011000000000000000101000000000000000100100110000000000000000100100000000000000000100101100000000000000001010000000000000000100101100000000000000000100100100100011000000000000000001010000000000000000010010";
        String word1 = "0010000";//2*4
        String word2 = "0000000000000100000000000000000";//13*17
        String word3 = "0100000000";//1*8
        String word4 = "000001";//5*0

        setupAutomat(tmString+"111"+word1);
        run();
    }

    /**
     * uses input to decide if one step at the time or all at once should be calculated
     */
    private static void run(){
        boolean successState = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter 0 to run all at Once or 1 to run one Step at a time");
        String s = "0";
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (s.startsWith("1")) {
            System.out.println("Single step active press Return to step forward");
            while (!TM.getCurrentState().getEndState()) {
                TM.singleStep();
                try {
                    br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            successState = TM.getCurrentState().getEndState();
        } else if (s.startsWith("0")){
            successState = TM.runAll();
        }
        System.out.println("finished it "+ (successState ? "was successful the solution is: "+TM.getBand().count('x') : "failed"));
    }

    /**
     * translate an integer to a moving direction
     *
     * @param in
     * @return returns -1 / 0 / 1
     */
    private static int translateToMove(int in){
        switch (in){
            case 1:
                return -1;
            case 2:
                return 1;
            case 3:
                return 0;
        }
        return 0;
    }

    /**
     * translates integer to character only used for readability
     *
     * @param n
     * @return returns 'x' / '_'
     */
    private static Character int2Char(int n){
        switch (n){
            case 1:
                return 'x';
            case 2:
                return '_';
        }
        return null;
    }

    /**
     * adds the states and with all the transitions
     * defines the emptyBandSymbol and leaveSymAlone
     */
    private static void setupAutomat(String input) {
        Character emptyBandSym = '_';
        Character leaveSymAlone = '*';
        TM = new TuringMachine<>();
        TM.setup(emptyBandSym, leaveSymAlone);

        List<State<Character>> allStates = TM.getAllStates();

        
        String[] split = input.split("111");
        String tm = split[0];
        String inputWord = split[1];

        split = tm.split("11");
        Set<String> states = new HashSet<>();
        for (String s : split){
            states.add(s.split("1")[0]);
        }
        for (int i = 0; i < states.size()+1; i++){
            allStates.add(new State<>(i));
        }
        
        // 0^i 1 0^j 1 0^k 1 0^l 1 0^m
        // currentState 1 currentSymbol 1 nextState 1 nextSymbol 1 moveDir
        for (String s : split){
            String[] tempS = s.split("1");
            allStates.get(tempS[0].length()-1).addTransition(allStates.get(tempS[2].length()-1), int2Char(tempS[1].length()), int2Char(tempS[3].length()), translateToMove(tempS[4].length()));
        }

        String[] splitInputW = inputWord.split("1");
        for (String s : splitInputW){
            for (char c : s.toCharArray()) {
                TM.bandInput(1, 'x');
            }
            TM.bandInput(1, '_');
        }

        for (String s : splitInputW){
            for (char c : s.toCharArray()) {
                TM.bandInput(-1, leaveSymAlone);
            }
            TM.bandInput(-1, leaveSymAlone);
        }
        TM.bandInput(-1, leaveSymAlone);

        allStates.get(1).setEndState(true);
        TM.setAllStates(allStates);
        TM.setCurrentState(allStates.get(0));
    }
}
