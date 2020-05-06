package kellerAutomat;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static KellerAutomat<Character> kA;

    public static void main(String[] args) {

        Character[] cs = { 'D', 'D', 'O', 'D', 'D', 'O', 'O' };
        setupAutomat();
        System.out.println("test 1 (DDODDOO): " + kA.calculate(cs));

        cs = new Character[] { 'D', 'D', 'O', 'O' };
        setupAutomat();
        System.out.println("test 2 (DDOO): " + kA.calculate(cs));

        cs = new Character[] { 'D', 'D', 'O' };
        setupAutomat();
        System.out.println("test 3 (DDO): " + kA.calculate(cs));

        cs = new Character[] { '3', '4', '+', '6', '2', '+', '*' };
        System.out.print("test a ");
        calculation(cs);

        cs = new Character[] { '3', '4', '+', '*' };
        System.out.print("test b ");
        calculation(cs);

        cs = new Character[] { '8', '4', '/', '5', '6', '-', '*' };
        System.out.print("test c ");
        calculation(cs);
        
        //((5/(7-(1+1)))*3)-(1+(1+1))
        cs = new Character[] { '5', '7', '1', '1', '+', '-', '/', '3' , '*', '1', '1', '1', '+', '+', '-'};
        System.out.print("test neu ");
        calculation(cs);
  
    }

    /**
     * calculates the solution of a given function if it is calculatable
     * 
     * @param cs
     */
    private static void calculation(Character[] cs) {
        setupAutomat();
        System.out.print("(" + characterArr2Str(cs) + "): ");
        if (kA.calculate(charactersToSyms(cs))) {
            System.out.println(calculateMath(cs));
        } else {
            System.out.println("is not a correct calculation");
        }
    }

    /**
     * turns a character array in to a string
     * 
     * @param in
     * @return returns the string representation of the character array
     */ 
    private static String characterArr2Str(Character[] in) {
        String s = "";
        for (Character c : in) {
            s += c;
        }
        return s;
    }

    /**
     * calculates the solution of the given function
     * 
     * @param cs function as character array
     * @return
     */
    private static String calculateMath(Character[] cs) {
        List<String> strings = new ArrayList<>();
        for (Character c : cs) {
            strings.add(c.toString());
        }
        while (strings.size() > 1) {
            for (int i = 0; i < strings.size() - 2; i++) {
                if (stringToSym(strings.get(i)) == 'D' && stringToSym(strings.get(i + 1)) == 'D'
                        && stringToSym(strings.get(i + 2)) == 'O') {
                    strings.set(i, calcOperation(Integer.parseInt(strings.get(i)), Integer.parseInt(strings.get(i + 1)),
                            strings.get(i + 2)).toString());
                    strings.remove(i + 1);
                    strings.remove(i + 1);
                    break;
                }
            }
        }
        return strings.get(0);
    }

    /**
     * calculates the output with the given inputs and oparator
     * 
     * @param i1 input 1 as int
     * @param i2 input 2 as int
     * @param op operator as String
     * @return return calculated value if unknown operator returns dummy value -999
     */
    private static Integer calcOperation(int i1, int i2, String op) {
        switch (op) {
        case "*":
            return i1 * i2;
        case "/":
            return i1 / i2;
        case "+":
            return i1 + i2;
        case "-":
            return i1 - i2;
        default:
            return -999;
        }
    }

    /**
     * turns a character array into a Symbol array digit -> D operator -> O else ->
     * X
     * 
     * @param cs input character array
     * @return returns Symbol array as character array
     */
    private static Character[] charactersToSyms(Character[] cs) {
        Character[] temp = new Character[cs.length];
        for (int i = 0; i < cs.length; i++) {
            temp[i] = characterToSym(cs[i]);
        }
        return temp;
    }

    /**
     * turns a String into a symbol 
     * used for checking 100, -43, 123.543 
     * @param s
     * @return return s D / O / X like characterstosym function
     */
    private static Character stringToSym(String s) {
        if (s.matches("[+-]?([0-9]*[.])?[0-9]")) {
            return 'D';
        } else if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s)) {
            return 'O';
        } else {
            return 'X';
        }
    }
    
    /**
     * same as stringtosym but with single chars only
     * @param c
     * @return
     */
    private static Character characterToSym(Character c) {
        switch (c) {
        case '+':
        case '-':
        case '*':
        case '/':
            return 'O';
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            return 'D';
        default:
            return 'X';
        }
    }
    
    /**
     * adds the states and with all the transitions
     * defines the emptyStack and emptySymbol 
     */
    private static void setupAutomat() {
        Character emptyStack = '$';
        Character emptySymbol = 'e';
        kA = new KellerAutomat<>();
        kA.setup();
        kA.addToKeller(emptyStack);
        List<State<Character>> allStates = kA.getAllStates();

        for (int i = 0; i < 5; i++) {
            allStates.add(new State<>(i));
            allStates.get(i).setEmptyStackSymbol(emptyStack);
            allStates.get(i).setEmptySymbol(emptySymbol);
        }


        allStates.get(0).addTransition(allStates.get(1), 'D', emptyStack, new Character[] { emptyStack });

        allStates.get(1).addTransition(allStates.get(2), 'D', emptyStack, new Character[] { 'D' });

        allStates.get(2).addTransition(allStates.get(2), 'D', 'D', new Character[] { 'D', 'D' });
        allStates.get(2).addTransition(allStates.get(3), 'O', 'D', new Character[] { emptySymbol });

        allStates.get(3).addTransition(allStates.get(2), 'D', 'D', new Character[] { 'D', 'D' });
        allStates.get(3).addTransition(allStates.get(2), 'D', emptyStack, new Character[] { 'D' });
        allStates.get(3).addTransition(allStates.get(3), 'O', 'D', new Character[] { emptySymbol });
        allStates.get(3).addTransition(allStates.get(4), null, emptyStack, new Character[] { emptySymbol });

        allStates.get(4).setEndState(true);

        kA.setAllStates(allStates);
        kA.setCurrentState(allStates.get(0));
    }

}
