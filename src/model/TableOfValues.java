package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableOfValues {

    private final Group group = new Group();
    private ObservableList<Point> tableData = FXCollections.observableArrayList();

    public TableOfValues(){
        TableView<Point> table = new TableView<>(tableData);

        TableColumn<Point, Double> xColumn = new TableColumn("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        xColumn.setPrefWidth(100);

        TableColumn<Point, Double> yColumn = new TableColumn("y");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        yColumn.setPrefWidth(100);

        table.getColumns().addAll(xColumn, yColumn);
        table.setPrefSize(300, 350);

        group.getChildren().addAll(table);
    }

    public void updateTable(Point point){
        tableData.add(point);
    }

    public void clearTable(){
        tableData.clear();
    }

    public Group getGroup(){
        return group;
    }

}
