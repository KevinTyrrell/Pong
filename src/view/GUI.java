package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Collidable;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.LinkedList;


public class GUI extends Application
{
    /** Constant integers relating to core elements on the screen */
    public static final int SCENE_WIDTH = 1280, SCENE_HEIGHT = 720, PADDLE_WIDTH = 35, PADDLE_HEIGHT = 100, BORDER_SIZE = 30;
    /** Location to reset the ball each round. */
    private final Point2D.Double BALL_DEFAULT_LOCATION = new Point2D.Double(SCENE_WIDTH/ 2, SCENE_HEIGHT/ 2);

    /** The Ball which is deflected by the Paddles. */
    private Ball ball = new Ball(BALL_DEFAULT_LOCATION.getX(), BALL_DEFAULT_LOCATION.getY(), 15);
    /** The left-side Paddle. */
    private Paddle pdlLeft = new Paddle(100, SCENE_HEIGHT / 2 - PADDLE_HEIGHT / 2,
            PADDLE_WIDTH, PADDLE_HEIGHT, KeyCode.W, KeyCode.S);
    /** The right-side Paddle. */
    private Paddle pdlRight = new Paddle(SCENE_WIDTH - 100 - PADDLE_WIDTH, SCENE_HEIGHT / 2 - PADDLE_HEIGHT / 2,
            PADDLE_WIDTH, PADDLE_HEIGHT, KeyCode.UP, KeyCode.DOWN);
    /** All objects which can be collided with must be placed here. */
    private LinkedList<Collidable> collisionList = new LinkedList<>(Arrays.asList(pdlLeft, pdlRight));

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Modifications to the stage.
        primaryStage.setTitle("Pong Game");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        pdlLeft.initializeEvents(primaryStage);
        pdlRight.initializeEvents(primaryStage);

        // Exit when the user presses escape.
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE)
                System.exit(0);
        });

        // Reset ball if pressed R
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, e ->
        {
            if (e.getCode() == KeyCode.R)
            {
                ball.setCenterX(BALL_DEFAULT_LOCATION.getX());
                ball.setCenterY(BALL_DEFAULT_LOCATION.getY());
                ball.resetSpeed();
                ball.getTrajectory().setAngle(ball.getTrajectory().getRandomAngle());
            }
        });

        // Final stage preparation.
        final String cssDefault = "-fx-border-color: white;\n" + "-fx-border-width: " + BORDER_SIZE + ";\n" + "-fx-background-color: black\n";
        Pane root = new Pane(pdlLeft, pdlRight, ball);
        root.setStyle(cssDefault);
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.show();

        // Begin the primary game loop.
        ball.getTrajectory().setAngle((short) 0);
        startGame();
    }

    /**
     * Primary Game Loop which is called once per frame.
     * The game loop will move the ball, check for collisions,
     * and then will redirect the ball if necessary.
     */
    private void startGame()
    {
        new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                ball.move(collisionList);

                // TODO: Decide if we want to use the code below or not.
                /*if (ball.getCenterY() - ball.getRadius() < BORDER_SIZE)
                {
                    //ball.angle.changeAngle((short) Math.abs(ball.angle.getAngle() - 360));
                }
                // LOWER COLLISION DETECTION.
                else if (ball.getCenterY() + ball.getRadius() > SCENE_HEIGHT - BORDER_SIZE)
                {
                    //ball.angle.changeAngle((short) Math.abs(ball.angle.getAngle() - 360));
                }*/
            }
        }.start();
    }
}
