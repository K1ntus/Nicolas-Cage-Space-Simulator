package fr.groupe40.projet.client.window;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoaderScreen extends Preloader {

	  ProgressBar bar;
	  Stage stage;

	  private Scene createPreloaderScene() {
	    bar = new ProgressBar();
        bar.setLayoutX(380);
        bar.setLayoutY(250);
        bar.setPrefSize(60, 60);

        Pane p = new Pane();
        p.getChildren().addAll( bar);
	    return new Scene(p, 300, 150);
	  }

	  @Override
	  public void start(Stage stage) throws Exception {
	    this.stage = stage;
	    stage.setScene(createPreloaderScene());
	    stage.show();
	  }

	    @Override
	    public void handleProgressNotification(ProgressNotification pn) {
	        bar.setProgress(pn.getProgress());
	        stage.show();
	    }
	 
	    @Override
	    public void handleStateChangeNotification(StateChangeNotification evt) {
	        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
	            //stage.hide();
	        }
	    } 

	  @Override
	  public void handleApplicationNotification(PreloaderNotification info) {
	    // Check if info is ProgressNotification
	    if (info instanceof ProgressNotification) {
	      // if yes, get the info and cast it
	      ProgressNotification pn = (ProgressNotification) info;
	      // update progress
	      bar.setProgress(pn.getProgress());
	      // if this was the last progress (progress reached 1), hide preloader
	      // this is really important, if preloader isn't hide until app loader
	      // reaches the start method of application and tries to open the stage of
	      // the main app with the show() method, it will not work.
	      if (pn.getProgress() == 1.0) {
	        stage.hide();
	      }
	    }
	  }
	}