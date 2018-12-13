import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TCPClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(true);
        stage.setOnCloseRequest(event -> {
            System.exit(0);
            Platform.exit();
        });

        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client.fxml"));
        Parent root = loader.load();
        ClientController tela = loader.getController();
        tela.stage = stage;


        Scene scene = new Scene(root);
        stage.setTitle("Anglo RPG");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
	}

}