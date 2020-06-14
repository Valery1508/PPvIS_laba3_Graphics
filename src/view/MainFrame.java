package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.TableOfValues;

public class MainFrame {
    public static final int MAIN_FRAME_HEIGHT = 550;
    public static final int MAIN_FRAME_WIDTH = 800;

    private Stage stage;
    private Scene scene;
    private Graphic graphicGroup;


    public MainFrame(Stage stage) {
        this.stage = stage;
    }

    public void show(){
        BorderPane pane = new BorderPane();
        graphicGroup = new Graphic();

        TableOfValues table = new TableOfValues();
        Group tableGroup = table.getGroup();

        ParametrsForm parametersForm = new ParametrsForm(graphicGroup, table);
        Group parametersFormGroup = parametersForm.getGroup();

        VBox leftGroup = new VBox(parametersFormGroup, tableGroup);
        leftGroup.setSpacing(10);

        Group chartGroup = graphicGroup.getChartGroup();

        pane.setLeft(leftGroup);
        pane.setRight(chartGroup);

        stage.setTitle("GRAPHICS");
        stage.setMinWidth(MAIN_FRAME_WIDTH);
        stage.setMinHeight(MAIN_FRAME_HEIGHT);
        scene = new Scene(pane);
        setSceneKeyPressEvents();
        stage.setScene(scene);
        stage.show();
    }

    public void setSceneKeyPressEvents() {
        scene.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.CONTROL)) {
                graphicGroup.setChartScroll();
            }
        });
        scene.setOnKeyReleased(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.CONTROL)) {
                graphicGroup.clearChartScroll();
            }
        });
    }
}
