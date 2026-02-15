import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CalculatorView extends Pane {

    MainDisplay model;
    public Button[][] buttons;// 5x4
    private String[] buttonIcons = {"1", "2", "3", "/", "4", "5", "6", "X", "7", "8", "9", "-", "0", ".", "=", "+"};

    public Button delete;
    public Button clear;
    public Button answer;
    public TextField screen;

    // getters for components
    public Button[][] getNumButtons(){return buttons;}
    public Button getEquals() {return buttons[3][4];}
    public TextField getScreen(){return screen;}
    public Button getDelete(){return delete;}
    public Button getClear(){return clear;}
    public Button getAnswer(){return answer;}


    public CalculatorView(MainDisplay initModel) {
        model = initModel;

        buttons = new Button[4][4];

        delete = new Button("DEL");
        clear = new Button("CLR");
        answer = new Button("ANS");

        setPrefSize(300,533);

        int counter = -1;
        for (int col = 0; col < 4; col++){
            for (int row = 0; row < 4;row++) {
                counter += 1;
                buttons[row][col] = new Button(buttonIcons[counter]);
                buttons[row][col].setPrefSize(60,60);
                buttons[row][col].relocate(15 + row * 70, 150 + col * 70);
                buttons[row][col].setStyle("-fx-font: 12 arial; -fx-base: rgb(65, 105, 225); " + "-fx-text-fill: rgb(0,0,0);");
                getChildren().add(buttons[row][col]);
            }
        }

        screen = new TextField();
        screen.setStyle("-fx-font: 30 arial; -fx-base: rgb(70, 130, 180); " + "-fx-text-fill: rgb(255,255,255);");
        screen.setEditable(false);
        screen.setAlignment(Pos.CENTER);
        screen.setPrefSize(280,100);
        screen.relocate(10,15);

        delete.setPrefSize(70,60);
        delete.relocate(20,440);
        delete.setStyle("-fx-font: 12 arial; -fx-base: rgb(115, 147, 179); " + "-fx-text-fill: rgb(255,255,255);");
        clear.setPrefSize(70,60);
        clear.relocate(110,440);
        clear.setStyle("-fx-font: 12 arial; -fx-base: rgb(115, 147, 179); " + "-fx-text-fill: rgb(255,255,255);");
        answer.setPrefSize(70,60);
        answer.relocate(200,440);
        answer.setStyle("-fx-font: 12 arial; -fx-base: rgb(115, 147, 179); " + "-fx-text-fill: rgb(255,255,255);");




        getChildren().addAll(screen, delete, clear, answer);
    }

    public void update(){
        if(model.getBasicDisplay().isEmpty()){
            answer.setDisable(true);
            clear.setDisable(true);
            delete.setDisable(true);
        }else {
            answer.setDisable(false);
            clear.setDisable(false);
            delete.setDisable(false);
        }

        String equation = "";
        for (int i = 0; i < model.getBasicDisplay().length();i++) {
            equation +=  model.getBasicDisplay().charAt(i);
        }
        screen.setText(equation);
    }

}
