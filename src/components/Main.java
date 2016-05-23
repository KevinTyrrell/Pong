package components;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Main extends Application
{
    /** Constant integers relating to core elements on the screen */
    public static final int SCENE_WIDTH = 1280, SCENE_HEIGHT = 720, PADDLE_WIDTH = 35, PADDLE_HEIGHT = 100, BORDER_SIZE = 30;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Modifications to the stage.
        primaryStage.setTitle("Pong Game");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        /** The root pane. */
        Pane root = new Pane();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        // The three objects on the game field.
        Paddle pdlRight = new Paddle(SCENE_WIDTH - 100 - PADDLE_WIDTH, SCENE_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, KeyCode.UP, KeyCode.DOWN, scene);
        Paddle pdlLeft = new Paddle(100, SCENE_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, KeyCode.W, KeyCode.S, scene);
        Ball ball = new Ball(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, 10);

        // Exit when the user presses escape.
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE)
            {
                System.exit(0);
            }
        });

        // Final stage preparation.
        final String cssDefault = "-fx-border-color: white;\n" + "-fx-border-width: " + BORDER_SIZE + ";\n" + "-fx-background-color: black\n";
        root.setStyle(cssDefault);
        root.getChildren().addAll(pdlLeft, pdlRight, ball);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Begin the ball AI.
        startGame(ball, pdlLeft, pdlRight);
    }

    public void startGame(Ball ball, Paddle pdlLeft, Paddle pdlRight)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (ball.active)
                {
                    ball.move();
                    //TODO: Fix ball clipping issue when paddle moves up or down into the ball
                    // UPPER COLLISION DETECTION.
                    if (ball.getCenterY() - ball.getRadius() < BORDER_SIZE)
                    {
                        ball.angle.changeAngle((short) Math.abs(ball.angle.getAngle() - 360));
                    }
                    // LOWER COLLISION DETECTION.
                    else if (ball.getCenterY() + ball.getRadius() > SCENE_HEIGHT - BORDER_SIZE)
                    {
                        ball.angle.changeAngle((short) Math.abs(ball.angle.getAngle() - 360));
                    }
                    // LEFT BUMPER COLLISION DETECTION.
                    else if (ball.getCenterX() - ball.getRadius() < pdlLeft.getX() + PADDLE_WIDTH && ball.getCenterX() - ball.getRadius() > pdlLeft.getX() && ball.getCenterY() > pdlLeft.getY() && ball.getCenterY() < pdlLeft.getY() + PADDLE_HEIGHT)
                    {
                        ball.angle.changeAngle((short) Math.abs(ball.angle.getAngle() - 540));
                    }
                    // RIGHT BUMPER COLLISION DETECTION.
                    else if (ball.getCenterX() + ball.getRadius() > pdlRight.getX() && ball.getCenterX() < pdlRight.getX() + PADDLE_WIDTH && ball.getCenterY() > pdlRight.getY() && ball.getCenterY() < pdlRight.getY() + PADDLE_HEIGHT)
                    {
                        ball.angle.changeAngle((short) Math.abs(ball.angle.getAngle() - 540));
                    }
                    Tools.Sleep(20);
                }
            }
        }).start();
    }

    public static void main(String[] args) { launch(args); }
}