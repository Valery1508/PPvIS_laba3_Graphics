package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;


public class Graphic {

    public static final int UPPER_BOUND_X = 100;
    public static final int UPPER_BOUND_Y = 100;

    private NumberAxis xAxis = new NumberAxis("x", 0, UPPER_BOUND_X, 5);
    private NumberAxis yAxis = new NumberAxis("y", 0, UPPER_BOUND_Y, 5);

    private ObservableList<XYChart.Data<Integer, Integer>> linearSeriesData = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data<Integer, Integer>> arraySeriesData = FXCollections.observableArrayList();

    private ObservableList<XYChart.Series<Integer, Integer>> seriesList = FXCollections.observableArrayList();

    private final LineChart lineChart = new LineChart(xAxis, yAxis, seriesList);
    private final Group chartGroup = new Group();
    private int zoom = 0;
    private double mouseOnChartX;
    private double mouseOnChartY;

    public Graphic(){

        lineChart.setLayoutY(20);
        setChartPressAndDragEvents();

        Button zoomUp = new Button(" + ");
        zoomUp.setOnAction(actionEvent -> zoomUpAction());
        Button zoomDown = new Button(" - ");
        zoomDown.setOnAction(event -> zoomDownAction());

        HBox zoomBox = new HBox(zoomUp, zoomDown);
        zoomBox.setLayoutX(235);
        zoomBox.setLayoutY(460);
        zoomBox.setSpacing(10);

        chartGroup.getChildren().addAll(lineChart, zoomBox);
    }

    public Group getChartGroup(){
        return chartGroup;
    }

    public void createNewLinearSeries(String name, Integer leftThreshold, Integer rightThreshold) {
        String seriesName = "y = " + name + ", x ∈ [" + leftThreshold + "; " + rightThreshold + "]";
        linearSeriesData = FXCollections.observableArrayList();
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>(seriesName, linearSeriesData);
        seriesList.add(series);
    }

    public void createNewArraySeries() {
        String seriesName = "Array";
        arraySeriesData = FXCollections.observableArrayList();
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>(seriesName, arraySeriesData);
        seriesList.add(series);
    }

    public void updateLinearSeriesList(Integer x, Integer y){
        linearSeriesData.add(new XYChart.Data<>(x, y));
    }

    public void updateArraySeriesList(Integer x, Integer y){
        arraySeriesData.add(new XYChart.Data<>(x, y));
    }

    public void zoomUpAction(){
        if (zoom < 5) {
            xAxis.setLowerBound(xAxis.getLowerBound() + xAxis.getTickUnit());
            xAxis.setUpperBound(xAxis.getUpperBound() - xAxis.getTickUnit());
            yAxis.setLowerBound(yAxis.getLowerBound() + yAxis.getTickUnit());
            yAxis.setUpperBound(yAxis.getUpperBound() - yAxis.getTickUnit());
            zoom += 1;
        }
    }

    public void zoomDownAction(){
        xAxis.setLowerBound(xAxis.getLowerBound() - xAxis.getTickUnit());
        xAxis.setUpperBound(xAxis.getUpperBound() + xAxis.getTickUnit());
        yAxis.setLowerBound(yAxis.getLowerBound() - yAxis.getTickUnit());
        yAxis.setUpperBound(yAxis.getUpperBound() + yAxis.getTickUnit());
        zoom -= 1;
    }

    private void setChartPressAndDragEvents() {
        lineChart.setOnMousePressed(mouseEvent -> {
            mouseOnChartX = mouseEvent.getX();
            mouseOnChartY = mouseEvent.getY();
        });
        lineChart.setOnMouseDragged(mouseEvent -> {
            xAxis.setLowerBound(xAxis.getLowerBound() + (mouseOnChartX - mouseEvent.getX()) / 1);
            xAxis.setUpperBound(xAxis.getUpperBound() + (mouseOnChartX - mouseEvent.getX()) / 1);
            mouseOnChartX = mouseEvent.getX();

            yAxis.setLowerBound(yAxis.getLowerBound() + (mouseEvent.getY() - mouseOnChartY) * 1);
            yAxis.setUpperBound(yAxis.getUpperBound() + (mouseEvent.getY() - mouseOnChartY) * 1);
            mouseOnChartY = mouseEvent.getY();
        });
    }

    public void setChartScroll() {
        lineChart.setOnScroll(scrollEvent -> {
            if(scrollEvent.getDeltaY() > 0) {
                zoomUpAction();
            }
            if (scrollEvent.getDeltaY() < 0) {
                zoomDownAction();
            }
        });
    }

    public void clearChartScroll() {
        lineChart.setOnScroll(scrollEvent -> {});
    }

}
