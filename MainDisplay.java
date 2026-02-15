import java.util.*;

public class MainDisplay {
    private double lastAnswer;
    private List display;
    private String basicDisplay;
    private static String curNum = "";

    public MainDisplay() {
        lastAnswer = 0;
        display = new LinkedList<String>();
        basicDisplay = "";

    }

    public void add(String operation) {
        if (basicDisplay.isEmpty()) {
            basicDisplay = operation;
        }else if (MainDisplay.isNumeric(operation) || operation.equals(".")){
            basicDisplay += operation;
        }else {
            basicDisplay += " " + operation + " ";
        }
        // tokenizes the input into a string to turn it into prefix notation later
        if(MainDisplay.isNumeric(operation) || operation.equals(".")){
            curNum += operation;
        }else if (operation.equals("X") || operation.equals("/") || operation.equals("+") || operation.equals("-")){
            if (!curNum.isEmpty()) {
                display.add(curNum);
                curNum = "";
            }
            display.add(String.valueOf(operation));
        }
    }

    public List getDisplay(){
        return display;
    }

    public String getBasicDisplay() {return basicDisplay;}

    public String getCurNum() {return curNum;}

    public double getLastAnswer(){return lastAnswer;}

    public void setCurNum(String str){curNum = str;}

    public void setBasicDisplay(String str) {
        basicDisplay = str;
    }

    public double calculate() {
        // Shunting Yard algorithm
        Stack<String> myStack = new Stack<>();
        Queue<String> myQueue = new ArrayDeque<>();
        for (int i = 0; i < display.size();i++) {
            // Shunting Yard Algorithm
            String token = (String) display.get(i);

            if (MainDisplay.isNumeric((String) display.get(i))) {
                myQueue.add(token);
            } else {
                if (token.equals(")")) {

                    while(!myStack.isEmpty() && !myStack.peek().equals(")")) {
                        myQueue.add(myStack.pop());
                    }
                    if(myStack.peek().equals(")")) {
                        myStack.pop();
                    }

                } else if (!myStack.isEmpty() && MainDisplay.hasHigherPrecedence(token, myStack.peek()).equals("false")) {
                    myQueue.add(myStack.pop());
                    myStack.push(token);
                }else {
                    myStack.push(token);
                }
            }
        }

        while(!myStack.isEmpty()) {
            myQueue.add(myStack.pop());
        }

        // solve postfix notation
        Stack<String> answer = new Stack<>();

        while(!myQueue.isEmpty()) {
            if (MainDisplay.isNumeric(myQueue.peek())) {
                answer.push(myQueue.peek());
                myQueue.remove();
            } else {
                double num1 = Double.parseDouble(answer.pop());
                double num2 = Double.parseDouble(answer.pop());
                if (myQueue.peek().equals("+")) {
                    answer.push(String.valueOf(num2 + num1));
                    myQueue.remove();
                } else if (myQueue.peek().equals("-")) {
                    answer.push(String.valueOf(num2 - num1));
                    myQueue.remove();
                } else if (myQueue.peek().equals("/")) {
                    answer.push(String.valueOf(num2 / num1));
                    myQueue.remove();
                } else {
                    answer.push(String.valueOf(num2 * num1));
                    myQueue.remove();
                }
            }
        }
        lastAnswer = Double.parseDouble((String) answer.pop());
        return lastAnswer;
    }

    public static boolean isNumeric(String string) {
        try {
            Double num = Double.parseDouble(string);
        }catch (NumberFormatException nFe) {
            return false;
        }
        return true;
    }

    public static String hasHigherPrecedence(Object str1, Object str2) {
        if (!(str1 instanceof String && str2 instanceof String)) {
            return "false";
        }

        if((str1.equals("+") || str1.equals("-")) && (str2.equals("/") || str2.equals("X"))) {
            return "false";
        }else if ((str1.equals(str2))||(str1.equals("/") && str2.equals("X"))||(str1.equals("X") && str2.equals("/"))||(str1.equals("-") && str2.equals("+"))||(str1.equals("+") && str2.equals("-"))) {
            return "equal";
        }else{
            return "true";
        }
    }

}
