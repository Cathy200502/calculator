import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    private TextField display;

    private double num1 = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator");

        // Create the display
        display = new TextField();
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setStyle("-fx-font-size: 24px; -fx-pref-height: 50px;");

        // Create the number and operator buttons
        GridPane grid = createGrid();

        // Set up the scene and stage
        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGrid() {
        // Create the grid layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        // Place the display at the top
        grid.add(display, 0, 0, 4, 1);

        // Define buttons (numbers and operators)
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        int row = 1;
        int col = 0;

        // Add buttons to the grid
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setStyle("-fx-font-size: 20px;");
            button.setMinSize(50, 50);
            button.setOnAction(e -> handleButtonPress(label));
            grid.add(button, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        // Add clear button
        Button clearButton = new Button("C");
        clearButton.setStyle("-fx-font-size: 20px;");
        clearButton.setMinSize(50, 50);
        clearButton.setOnAction(e -> handleClear());
        grid.add(clearButton, 0, row, 4, 1);

        return grid;
    }

    // Handle button presses
    private void handleButtonPress(String label) {
        if (label.matches("[0-9]")) {  // Handle number button press
            if (startNewNumber) {
                display.clear();
                startNewNumber = false;
            }
            display.appendText(label);
        } else if (label.equals(".")) {  // Handle decimal point
            if (!display.getText().contains(".")) {
                display.appendText(label);
            }
        } else if (label.equals("=")) {  // Handle equals button
            calculateResult();
        } else if (label.equals("C")) {  // Handle clear button
            handleClear();
        } else {  // Handle operator (+, -, *, /)
            if (!display.getText().isEmpty()) {
                num1 = Double.parseDouble(display.getText());
                operator = label;
                startNewNumber = true;
            }
        }
    }

    // Handle the calculation based on the operator
    private void calculateResult() {
        if (!display.getText().isEmpty()) {
            double num2 = Double.parseDouble(display.getText());
            double result = 0;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }
            display.setText(String.valueOf(result));
            operator = "";
            startNewNumber = true;
        }
    }

    // Handle clear button
    private void handleClear() {
        display.clear();
        num1 = 0;
        operator = "";
        startNewNumber = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
