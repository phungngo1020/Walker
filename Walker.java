import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class Walker extends Application {
    Canvas canvas;
	GraphicsContext gc;
	Image image;
	
	int x= 200;
	int y= 200;
	int steps=0;
	
	boolean done = false;
	boolean face = false;
	
	
		public void step1() {
			if (!done) {
				int oldX = x;
				int oldY = y;
				double r = Math.random();
				if      (r < 0.25) x -= 5;
				else if (r < 0.50) x += 5;
				else if (r < 0.75) y -= 5;
				else if (r < 1.00) y += 5;
				steps++;
				System.out.println("Step #"+steps+", r="+r+", walker moves from ("+oldX+","+oldY+") to ("+x+","+y+")");
				if (face) {
					gc.drawImage(image, x, y,50,50);
				} else {
					gc.setLineWidth(2);
					gc.strokeLine(oldX,oldY,x,y);
				}
			} // endif...
			if ((x<0) || (x>=350) || (y<0) || (y>=350)) {
				done = true;
			} // endif...		
		} // endmethod step1...	
		
	public void walk() {
		while(!done) {
			step1();
		}
	} // endmethod walk...
	
	public void reset() {
		x = 200;
		y = 200;
		done = false;
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,400,400);
		if (face) {
			gc.drawImage(image, x, y,50,50);
		} // endif...
	} // endmethod walk...
	
    @Override
    public void start(Stage primaryStage) {
        Button btn1 = new Button();
        btn1.setText("Step 1...");
        btn1.setOnAction(
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("step the walker...");
					step1();
				} // end method handle...
			}  // end new...
		); // end call...
		
		Button btn2 = new Button();
        btn2.setText("Run...");
        btn2.setOnAction(
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("walk the walker...");
					walk();
				} // end method handle...
			}  // end new...
		); // end call...
		
		Button btn3 = new Button();
        btn3.setText("Reset...");
        btn3.setOnAction(
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("resetting the walker...");
					reset();
				} // end method handle...
			}  // end new...
		); // end call...
		
		Button btn4 = new Button();
        btn4.setText("switch to face...");
        btn4.setOnAction(
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (face) {
						System.out.println("setting walker to line...");
						btn4.setText("switch to face...");
					} else {
						System.out.println("setting walker to face...");
						btn4.setText("switch to line...");						
					} // endif...
					face = !face;
					reset();
				} // end method handle...
			}  // end new...
		); // end call...
		
		
		HBox hbox = new HBox();
        BorderPane root = new BorderPane();
		
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");
		hbox.getChildren().addAll(btn1,btn2,btn3,btn4);

		canvas = new Canvas(400,400);
        gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.GREEN);
        gc.setLineWidth(3);

		image = new Image("sad.png",50,50,false,false);
		if (face) {
			gc.drawImage(image, x, y,50,50);
		} // endif...
		
        root.setBottom(hbox);
		root.setCenter(canvas);
		
		Scene scene = new Scene(root);
        primaryStage.setTitle("Walker...");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 public static void main(String[] args) {
        launch(args);
    }
}
