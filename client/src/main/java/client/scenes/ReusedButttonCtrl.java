package client.scenes;

public abstract class ReusedButttonCtrl {

    private final MainCtrl mainCtrl;

    public ReusedButttonCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void showHome() {
        mainCtrl.showHome();
    }

    public void toggleSound() {}
}
