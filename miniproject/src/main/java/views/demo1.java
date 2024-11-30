package views;

public class demo1 extends tss {
    public void displayResult() {
        // Call the inherited method add
        int result = add(3, 4); // add() is inherited from tss
        System.out.println("The result is: " + result);
    }

    public static void main(String[] args) {
        demo1 obj = new demo1();
        obj.displayResult(); // This will display the result
    }
}
