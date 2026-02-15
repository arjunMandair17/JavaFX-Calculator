import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class CalculatorApp extends Application {
    MainDisplay model;
    CalculatorView view;

    public CalculatorApp(){
        model = new MainDisplay();
        view = new CalculatorView(model);
    }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();
        aPane.getChildren().add(view);
        aPane.setStyle("-fx-font: 12 arial; -fx-base: rgb(137, 207, 240); " + "-fx-text-fill: rgb(255,255,255);");

        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();


        // event handlers

        for (int col = 0; col < 4; col++) {
            for(int row = 0; row < 4; row++) {
                view.getNumButtons()[row][col].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent aE){
                        Button curButton = (Button) aE.getSource();
                        String curOperation = curButton.getText();
                        if (!(curOperation.equals("="))) {
                            if(MainDisplay.isNumeric(model.getBasicDisplay()) && !model.getBasicDisplay().isEmpty() && Double.parseDouble(model.getBasicDisplay()) == model.getLastAnswer()) {
                                model.getDisplay().clear();
                                model.setBasicDisplay("");

                            }
                            model.add(curOperation);
                            view.update();
                        }else {
                            if(!model.getCurNum().isEmpty()) {
                                model.getDisplay().add(model.getCurNum());
                                model.setCurNum("");
                            }
                            String ans = String.valueOf(model.calculate());
                            model.setBasicDisplay(ans);
                            model.getDisplay().clear();
                            view.update();
                        }

                        view.update();
                    }
                });
            }
        }

        view.getClear().setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent aE) {
               model.setBasicDisplay("");
               model.getDisplay().clear();
               model.setCurNum("");
               view.update();
           }
        });

        view.getAnswer().setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent aE) {
               double recentAnswer = model.getLastAnswer();
               model.getDisplay().add(String.valueOf(recentAnswer));
               model.setBasicDisplay(model.getBasicDisplay() + String.valueOf(recentAnswer));
               view.update();
           }
        });

        view.getDelete().setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent aE) {
               String deleteOp = String.valueOf(model.getBasicDisplay().charAt(model.getBasicDisplay().length() - 1));
               if(MainDisplay.isNumeric(deleteOp)) {
                   // delete last character of 'curNum' string
                   String curNumNew = "";
                   for(int i = 0; i < model.getCurNum().length() - 1; i++){
                       curNumNew += String.valueOf(model.getCurNum().charAt(i));
                   }
                   model.setCurNum(curNumNew);

                   // delete last character of 'basicDisplay'
                   String basicDispNew = "";
                   for(int i = 0; i < model.getBasicDisplay().length() - 1; i++){
                       basicDispNew += String.valueOf(model.getBasicDisplay().charAt(i));
                   }
                   model.setBasicDisplay(basicDispNew);
               } else if (deleteOp.equals(" ")) {
                   // delete last character of 'basicDisplay'
                   String basicDispNew = "";
                   for(int i = 0; i < model.getBasicDisplay().length() - 1; i++){
                       basicDispNew += String.valueOf(model.getBasicDisplay().charAt(i));
                   }
                   model.setBasicDisplay(basicDispNew);
               } else {
                   // delete last String element of 'display'
                   model.getDisplay().removeLast();

                   // delete last character of 'basicDisplay'
                   String basicDispNew = "";
                   for(int i = 0; i < model.getBasicDisplay().length() - 1; i++){
                       basicDispNew += String.valueOf(model.getBasicDisplay().charAt(i));
                   }
                   model.setBasicDisplay(basicDispNew);
               }
               view.update();
            }
        });



    }

}
