package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import static com.google.inject.Guice.createInjector;
import static client.Config.*;

public class InfoCtrl extends ReusedButtonCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    Label hintExplainer;
    @FXML
    Label timeExplainer;
    @FXML
    Label doublePointsExplainer;
    @FXML
    ImageView music;


    @Inject
    public InfoCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void setHintExplainer() {
        hintExplainer.setText("Eliminates an incorrect\nanswer from multiple\nchoice questions");
    }

    public void setTimeExplainer() {
        timeExplainer.setText(String.format("Reduces opponents\ntime by %d%%", timeReductionPercentage));
    }

    public void setDoublePointsExplainerExplainer() {
        doublePointsExplainer.setText("Doubles your points\nfor the current question");
    }


    public void toggleSound(){
        mainCtrl.toggleSound();
    }
}
