package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Service.DataService;
import data.TandemData;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Controller implements Initializable {

	@FXML
	private ImageView importMainDoc;

	@FXML
	private ImageView importRawDoc;
	
	@FXML
	private ImageView importMainImage;
	
	@FXML
	private ImageView importRawImage;

	@FXML
	private Label labelMain;

	@FXML
	private Label labelRaw;

	@FXML
	private Button updateBtn;

	@FXML
	private Button reportBtn;

	@FXML
	private TextFlow textArea;

	@FXML
	private ImageView imageSuccess;

	@FXML
	private Accordion accordion;

	@FXML
	private ProgressIndicator progressIndicator;

	private File mainTandem;
	private File rawTandem;
	private List<TandemData> rawDataList;
	private List<TandemData> mainDataList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		progressIndicator.setVisible(false);
		reportBtn.setVisible(false);
	}

	@FXML
	private void handleDragDroppedMain(DragEvent event) {
		if (event.getDragboard().hasFiles())
			event.acceptTransferModes(TransferMode.ANY);
	}

	@FXML
	private void handleDragOverMain(DragEvent event) {
		List<File> files = event.getDragboard().getFiles();
		mainTandem = files.get(0);
//    	   JOptionPane.showMessageDialog(null, files.get(0).getName(), "Tolga", JOptionPane.INFORMATION_MESSAGE);
		Image img = new Image("/etc/excel.png");
		importMainImage.setImage(img);
		labelMain.setText(files.get(0).getName());
	}

	@FXML
	private void handleDragDroppedRaw(DragEvent event) {
		if (event.getDragboard().hasFiles())
			event.acceptTransferModes(TransferMode.ANY);
	}

	@FXML
	private void handleDragOverRaw(DragEvent event) {
		List<File> files = event.getDragboard().getFiles();
		rawTandem = files.get(0);
		Image img = new Image("/etc/excel.png");
		importRawImage.setImage(img);
		labelRaw.setText(files.get(0).getName());

	}

	@FXML
	private void update() {

		Task<Void> task = new updateTask();

		task.setOnFailed(evt -> {
			progressIndicator.setVisible(false);
		});
		task.setOnSucceeded(evt -> {
//			Pane root = (Pane) FXMLLoader.load(getClass().getResource("dialog.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog.fxml"));
			Parent root = null;
			try {
				root = (Parent) fxmlLoader.load();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			ControllerDialog controllerDialog = fxmlLoader.<ControllerDialog>getController();
//			controllerDialog.setReportValue(rawDataList.size(), DataService.getInstance(), newItem);
			// Creating a scene object
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Message");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();

			stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {

				reportBtn.setVisible(true);

			});

			progressIndicator.setVisible(false);

		});

		progressIndicator.setVisible(true);

		// run this on background thread to avoid blocking the application thread
		new Thread(task).start();

	}

	public class updateTask extends Task<Void> {

		public updateTask() {

		}

		@Override
		protected Void call() throws Exception {

			try {
				// read raw data and main data
				DataService.getInstance().readRawData(rawTandem);
				DataService.getInstance().readMainData(mainTandem);

				// compare
				DataService.getInstance().compareTandemData();

				// update main excel
				DataService.getInstance().updateMainExcel(mainTandem);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	@FXML
	private void openReport() {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/application/Report.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Report");
			stage.initModality(Modality.NONE);
			stage.setScene(scene);
			stage.toBack();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}