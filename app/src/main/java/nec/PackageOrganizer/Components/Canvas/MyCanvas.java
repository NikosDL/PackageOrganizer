package nec.PackageOrganizer.Components.Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class MyCanvas
        extends Canvas {

    private GraphicsContext gc;

    public MyCanvas() {
        super();
        this.setMouseTransparent(true);

        gc = this.getGraphicsContext2D();
    }

    public boolean connectPoints(
            double startX,
            double startY,
            double endX,
            double endY) {
        
//        System.out.println("connectPoints() is using: " + startX.getValue() + "\n"
//                + startY.getValue() + "\n"
//                + endX.getValue() + "\n"
//                + endY.getValue() + "\n");

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
//        gc.setLineCap(StrokeLineCap.ROUND);
//        gc.setLineJoin(StrokeLineJoin.ROUND);

//        SimpleDoubleProperty halfX = new SimpleDoubleProperty();
//        if (startX.getValue() < endX.getValue()) {
//            halfX.bind(endX.subtract(startX).divide(2.0).add(startX));
//        } else {
//            halfX.bind(startX.subtract(endX).divide(2.0).add(endX));
//        }

//        DoubleProperty[] xPointsProp = new DoubleProperty[]{
//            startX, halfX, halfX, endX
//        };
        double[] xPoints = new double[]{
//            startX.get(), halfX.get(), halfX.get(), endX.get()
            0, endX
        };

//        
//        SimpleDoubleProperty halfY = new SimpleDoubleProperty();
//        if (startY.getValue() < endY.getValue()) {
//            halfY.bind(endY.subtract(startY).divide(2.0).add(startY));
//        } else {
//            halfY.bind(startY.subtract(endY).divide(2.0).add(endY));
//        }
//        
//        DoubleProperty[] yPointsProp = new DoubleProperty[]{
//            startY, startY, endY, endY
//        };
        double[] yPoints = new double[]{
//            startY.get(), startY.get(), endY.get(), endY.get()
            0, endY
        };
//
        gc.clearRect(0, 0, this.widthProperty().get(), this.heightProperty().get());
//        gc.strokePolyline(xPoints, yPoints, 4);
        if ((endX > startX) && (endY > startY)) {
            gc.strokeLine(startX, startY, endX, endY);
            return true;
        }
        
        if ((endX > startX) && (endY < startY)) {
            gc.strokeLine(startX, endY, endX, startY);
            return true;
        }
        
        if ((endX < startX) && (endY > startY)) {
            gc.strokeLine(endX, startY, startX, endY);
            return true;
        }
        
        if ((endX < startX) && (endY < startY)) {
            gc.strokeLine(endX, endY, startX, startY);
            return true;
        }
        
        return false;

//        endX.subscribe(e -> {
//            gc.clearRect(0, 0, this.widthProperty().get(), this.heightProperty().get());
//            gc.strokePolyline(xPoints, yPoints, 4);
//            System.out.println("Polyline should be there???");
//        });
//        DoubleProperty endXYProp = new SimpleDoubleProperty();
//        endXYProp.bind(endX.multiply(endY));
//        endX.addListener((observed, oldVal, newVal) -> {
//            System.out.println(String.valueOf(Math.abs(oldVal.doubleValue() - newVal.doubleValue())));
//            if (Math.abs(oldVal.doubleValue() - newVal.doubleValue()) > 5) {
//                gc.clearRect(0, 0, this.widthProperty().get(), this.heightProperty().get());
//                gc.strokePolyline(xPoints, yPoints, 4);
//            }
//        });
////
//        endY.subscribe(e -> {
//            gc.clearRect(0, 0, this.widthProperty().get(), this.heightProperty().get());
//            gc.strokePolyline(xPoints, xPoints, 4);
//            System.out.println("Polyline should be there???");
//        });

//        endY.addListener((observed, oldVal, newVal) -> {
//            if (Math.abs(oldVal.doubleValue() - newVal.doubleValue()) > 5) {
//                gc.clearRect(0, 0, this.widthProperty().get(), this.heightProperty().get());
//                gc.strokePolyline(xPoints, yPoints, 4);
//            }
//        });
    }

    public GraphicsContext getGc() {
        return this.gc;
    }
//
//    public void activateConnection(SimpleDoubleProperty startX,
//            SimpleDoubleProperty startY,
//            SimpleDoubleProperty endX,
//            SimpleDoubleProperty endY) {
//
//    }
}
