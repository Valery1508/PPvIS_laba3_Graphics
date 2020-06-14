package view;

import controller.ArrayThread;
import controller.LinearCalculationThread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.TableOfValues;

import java.util.concurrent.ConcurrentLinkedQueue;


public class ParametrsForm {

    private Group group = new Group();

    private Thread linearCalcThread = new Thread();
    private Thread arrayCalcThread = new Thread();

    private final ConcurrentLinkedQueue<Integer> linearQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Integer> arrayQueue = new ConcurrentLinkedQueue<>();

    private Integer x = 0;
    private Integer rightLimit = 0;
    private Integer n = 0, k = 0, a=1;

    public ParametrsForm(Graphic chartGroup, TableOfValues table){

        Label limitsForLinear = new Label(" Limits for 5x+3: ");

        TextField fromLimit = new TextField();
        fromLimit.setPromptText("from");
        fromLimit.setMaxWidth(50);
        TextField toLimit = new TextField();
        toLimit.setPromptText("to");
        toLimit.setMaxWidth(50);
        HBox limitsFields = new HBox(limitsForLinear, fromLimit, toLimit);
        limitsFields.setSpacing(5);

        Label nText = new Label("  n :  ");
        TextField nField = new TextField();
        nField.setPromptText("enter n");
        HBox nFields = new HBox(nText, nField);

        Label space = new Label("  ");
        Button startButton = new Button("S t a r t");
        Button stopButton = new Button("S t o p");
        HBox buttons = new HBox(startButton, stopButton);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);

        VBox parametrs = new VBox(limitsFields, nFields, buttons);
        parametrs.setSpacing(10);

        setLinearTempLine(chartGroup);
        setArrayTempLine(chartGroup);

        startButton.setOnAction(event -> {
            if(!linearCalcThread.isAlive()){
                if(correctData(fromLimit.getText())){
                    x = Integer.parseInt(fromLimit.getText());  // left limit

                    if(correctData(toLimit.getText())){
                        if (Integer.parseInt(toLimit.getText()) >= x){
                            rightLimit = Integer.parseInt(toLimit.getText());

                            linearQueue.clear();
                            table.clearTable();

                            chartGroup.createNewLinearSeries("5x + 3", x, rightLimit);
                            linearCalcThread = new Thread(new LinearCalculationThread(x, rightLimit, linearQueue));
                            linearCalcThread.start();
                        }
                        else { errorAlert(); }
                    }
                }

                if(!arrayCalcThread.isAlive()) {
                    if (correctData(nField.getText())) {
                        n = Integer.parseInt(nField.getText());

                            arrayQueue.clear();
                            chartGroup.createNewArraySeries();
                            arrayCalcThread = new Thread(new ArrayThread(n, arrayQueue, table));
                            arrayCalcThread.start();

                    }
                }
            }
        });

        stopButton.setOnAction(event -> {
            if(!linearCalcThread.isInterrupted()){
                linearCalcThread.interrupt();
                arrayCalcThread.interrupt();
                linearQueue.clear();
                arrayQueue.clear();
                table.clearTable();
                fromLimit.clear();
                toLimit.clear();
            }
        });

        group.getChildren().addAll(parametrs);
    }

    public Group getGroup(){
        return group;
    }

    private boolean correctData(String text) {
        boolean isInteger = false;
        String numberMatcher = "^-?[0-9]*$";

        if (!text.isEmpty()) {
            if (!text.matches(numberMatcher)) {
                errorAlert();
            } else { isInteger = true; }
        }
        return isInteger;
    }

    private void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Impossible to draw a graphic");
        alert.setContentText("Enter the CORRECT data");

        alert.showAndWait();
    }

    private void setLinearTempLine(Graphic chartGroup) {
        Timeline linearTempLine = new Timeline();
        linearTempLine.setCycleCount(Timeline.INDEFINITE);
        linearTempLine.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                actionEvent -> {
                    if (!linearQueue.isEmpty()) {
                        chartGroup.updateLinearSeriesList(x, linearQueue.poll());
                        x += 1;
                    }
                }));
        linearTempLine.play();
    }

    private void setArrayTempLine(Graphic chartGroup) {
        Timeline arrayTempLine = new Timeline();
        arrayTempLine.setCycleCount(Timeline.INDEFINITE);
        arrayTempLine.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                actionEvent -> {
                    if (!arrayQueue.isEmpty()) {
                        chartGroup.updateArraySeriesList(a, arrayQueue.poll());
                        a += 1;
                    }
                }));
        arrayTempLine.play();
    }

}
