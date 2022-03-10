package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import static client.Config.timeAnswerReveal;
import static com.google.inject.Guice.createInjector;

public class AnswerRevealCtrl extends ReusedButtonCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    ProgressBar pgBarReveal;

    @FXML
    Label questionTracker;


    @Inject
    public AnswerRevealCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBarReveal, timeAnswerReveal, 1);
    }

    public void updateQuestionTracker() {
        mainCtrl.updateQuestionTracker(questionTracker, false);
    }


}
