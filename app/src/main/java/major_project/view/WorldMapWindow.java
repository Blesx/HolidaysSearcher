package major_project.view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import major_project.ViewManager;
import org.controlsfx.control.WorldMapView;
import java.util.Locale;

public class WorldMapWindow implements Window {
    private final ViewManager manager;
    private Scene scene;

    private Label helpLabel;
    private WorldMapView worldMapView;
    private Button chooseButton;
    private VBox vBox;

    public WorldMapWindow(ViewManager manager) {
        this.manager = manager;
        drawScene();

    }

    private void drawScene() {
        helpLabel = new Label("Click on a country to start looking!");
        helpLabel.setFont(new Font(manager.getDefaultFontSize()));

        worldMapView = new WorldMapView();
        worldMapView.setCountrySelectionMode(WorldMapView.SelectionMode.SINGLE);

        worldMapView.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().setMaxWidth(500);

                if (!worldMapView.getSelectedCountries().isEmpty()) {
                    alert.setHeaderText("This is " + getCountryFromAbv(worldMapView.getSelectedCountries()) + "!");


                } else {
                    alert.setHeaderText("Select a country!");


                }



                alert.showAndWait();

            }

        });

        chooseButton = new Button("Choose!");
        chooseButton.setOnAction(action -> {
            if (!worldMapView.getSelectedCountries().isEmpty()) {
                String country = getCountryFromAbv(worldMapView.getSelectedCountries());
                drawCountryConfirm(country, worldMapView.getSelectedCountries().get(0));

            } else {
                System.out.println("Nothing chosen :(");

            }

        });

        chooseButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Chooses a country, which you can then check for its holidays.");

            }

        });

        vBox = new VBox(helpLabel, worldMapView, chooseButton);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox, manager.getViewWidth(), manager.getViewHeight());

    }

    @Override
    public Scene getScene() {
        return scene;

    }

    public void drawCountryConfirm(String country, WorldMapView.Country countryAbv) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Would you like to pick " + country + "? Must quit to change countries.");

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                manager.setScene(new CalendarWindow(manager, manager.getInputModelSetCountry(countryAbv.name()), manager.getOutputModel()));

            }

        });

    }

    public String getCountryFromAbv(ObservableList<WorldMapView.Country> list) {
        String countryAbv = list.get(0).toString();
        Locale locale = new Locale("", countryAbv);
        return locale.getDisplayCountry();

    }

    private void createInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMaxWidth(500);
        alert.setHeaderText(message);
        alert.showAndWait();

    }

}
