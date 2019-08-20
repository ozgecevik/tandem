package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Service.DataService;
import data.TandemData;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class ControllerReport implements Initializable {

	@FXML
	private ImageView export;
	
	@FXML
	private ImageView loadImage;

	@FXML
	private ImageView filterImage;
	
	@FXML
	private Label countLabel;
	
	@FXML
	private Rectangle exportRectangle;

	@FXML
	private TextField filterField;

	@FXML
	private TableView<TandemData> tableTandem;

	@FXML
	private TableColumn<TandemData, Integer> msnColumn;

	@FXML
	private TableColumn<TandemData, String> caColumn;

	@FXML
	private TableColumn<TandemData, String> natcoTypeColumn;

	@FXML
	private TableColumn<TandemData, String> referenceColumn;

	@FXML
	private TableColumn<TandemData, String> commentColumn;

	@FXML
	private TableColumn<TandemData, String> conversationColumn;

	@FXML
	private TableColumn<TandemData, String> myCommentColumn;

	@FXML
	private TableColumn<TandemData, String> detailedCommentColumn;

	@FXML
	private TableColumn<TandemData, String> statusColumn;
	
	@FXML
	private ComboBox<String> comboBox;

	private ObservableList<TandemData> list;

	private Paint i;

	@FXML
	private ProgressIndicator progressIndicator;
	
	private File saveFile;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		

//		Image img = new Image("/etc/filter.png");
//		ImageView iconView = new ImageView(img);
//		iconView.setFitHeight(15);
//		iconView.setFitWidth(15);
//		filterBtn.setGraphic(iconView);
		Image img = new Image("/etc/excel.png");
		export.setImage(img);
		Image imgload = new Image("/etc/load.png");
		loadImage.setImage(imgload);
		Image imgfilter = new Image("/etc/filter.png");
		filterImage.setImage(imgfilter);
		progressIndicator.setVisible(false);
		filterField.setPromptText("Enter filter value");
		
		comboBox.getItems().add("Main");
        comboBox.getItems().add("Close");
        comboBox.getItems().add("OW-F5");
        comboBox.getItems().add("CO-CO");
        comboBox.getItems().add("QN-NC");
        comboBox.setValue("Main");


		msnColumn.setSortable(true);
		caColumn.setSortable(true);
		natcoTypeColumn.setSortable(true);
		referenceColumn.setSortable(true);
		commentColumn.setSortable(true);
		conversationColumn.setSortable(true);
		myCommentColumn.setSortable(true);
		detailedCommentColumn.setSortable(true);
		statusColumn.setSortable(true);

		// Defines how to fill data for each cell.
		// Get value from property of UserAccount. .
		msnColumn.setCellValueFactory(new PropertyValueFactory<>("MSN"));
		caColumn.setCellValueFactory(new PropertyValueFactory<>("constituentAssembly"));
		natcoTypeColumn.setCellValueFactory(new PropertyValueFactory<>("natcoType"));
		referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
		commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
		conversationColumn.setCellValueFactory(new PropertyValueFactory<>("conversation"));
		commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
		myCommentColumn.setCellValueFactory(new PropertyValueFactory<>("myComment"));
		detailedCommentColumn.setCellValueFactory(new PropertyValueFactory<>("detailedComment"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
		
		
		addTooltipToColumnCells(commentColumn);
		addTooltipToColumnCells(conversationColumn);
		addTooltipToColumnCells(myCommentColumn);
		addTooltipToColumnCells(detailedCommentColumn);
		
		ObservableList<TandemData> list = getMainTandemList();
		tableTandem.setItems(list);

		filterTable(list);
	}

	private void filterTable(ObservableList<TandemData> list) {
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<TandemData> filteredData = new FilteredList<>(list, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tandem -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (tandem.getNatcoType() != null && tandem.getNatcoType().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches NatcoType.
				} else if (tandem.getStatus() != null && tandem.getStatus().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<TandemData> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tableTandem.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tableTandem.setItems(sortedData);

		// after assigning the items
		countLabel.setStyle("-fx-font-weight: bold");
		countLabel.textProperty().bind(Bindings.size(tableTandem.getItems()).asString("Record count: %s"));
	}

	private ObservableList<TandemData> getMainTandemList() {

		list = FXCollections.observableArrayList();
		for (TandemData td : DataService.getInstance().getMainDataList()) {
			list.add(td);
		}
		return list;
	}
	
	private ObservableList<TandemData> getCloseTandemList() {

		list = FXCollections.observableArrayList();
		for (TandemData td : DataService.getInstance().getClosedData()) {
			list.add(td);
		}
		return list;
	}
	
	private ObservableList<TandemData> getOWF5TandemList() {

		list = FXCollections.observableArrayList();
		for (TandemData td : DataService.getInstance().getMainDataList()) {
			if(td.getNatcoType().equals("OW-F5"))
				list.add(td);
		}
		return list;
	}
	
	private ObservableList<TandemData> getCOCOTandemList() {
		list = FXCollections.observableArrayList();
		for (TandemData td : DataService.getInstance().getMainDataList()) {
			if(td.getNatcoType().equals("CO-CO"))
				list.add(td);
		}
		return list;
	}
	
	private ObservableList<TandemData> getQNNCTandemList() {
		list = FXCollections.observableArrayList();
		for (TandemData td : DataService.getInstance().getMainDataList()) {
			if(td.getNatcoType().equals("QN-NC"))
				list.add(td);
		}
		return list;
	}

	@FXML
	private void exportReport() {
		
		 FileChooser fileChooser = new FileChooser();
	        fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
	                new FileChooser.ExtensionFilter("ODS files (*.ods)", "*.ods"),
	                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"),
	                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html")
	        );
	        saveFile = fileChooser.showSaveDialog(tableTandem.getScene().getWindow());

		Task<Void> task = new exportTask();

		task.setOnFailed(evt -> {
			progressIndicator.setVisible(false);
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Message");
			alert.setHeaderText("Error Occured!");
			String s ="This is an example of JavaFX 8 Dialogs... ";
//			alert.setContentText(s);
			alert.show();
			
		});
		task.setOnSucceeded(evt -> {

			progressIndicator.setVisible(false);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Message");
			alert.setHeaderText("Export Successful!");
			String s ="This is an example of JavaFX 8 Dialogs... ";
//			alert.setContentText(s);
			alert.show();

		});

		progressIndicator.setVisible(true);

		// run this on background thread to avoid blocking the application thread
		new Thread(task).start();
		
	}
	
	private <T> void addTooltipToColumnCells(TableColumn<TandemData,T> column) {

	    Callback<TableColumn<TandemData, T>, TableCell<TandemData,T>> existingCellFactory 
	        = column.getCellFactory();

	    column.setCellFactory(c -> {
	        TableCell<TandemData, T> cell = existingCellFactory.call(c);

	        Tooltip tooltip = new Tooltip();
	        // can use arbitrary binding here to make text depend on cell
	        // in any way you need:
	        tooltip.textProperty().bind(cell.itemProperty().asString());

	        cell.setTooltip(tooltip);
	        return cell ;
	    });
	}

	
	public class exportTask extends Task<Void> {


		public exportTask() {

		}

		@Override
		protected Void call() throws Exception {

			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet spreadsheet = workbook.createSheet("sample");

			Row row = spreadsheet.createRow(0);
			
			 // Styling border of cell.  
	        CellStyle styleLabel = workbook.createCellStyle();
	        CellStyle styleOWF5 = workbook.createCellStyle();
	        CellStyle styleCOCO = workbook.createCellStyle();
	        CellStyle styleQNNC = workbook.createCellStyle();
	        
	        //label
	        styleLabel.setFillForegroundColor(IndexedColors.BLUE.getIndex());
	        styleLabel.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        styleLabel.setBorderBottom(BorderStyle.THIN);  
	        styleLabel.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
	        styleLabel.setBorderRight(BorderStyle.THIN);  
	        styleLabel.setRightBorderColor(IndexedColors.BLACK.getIndex());  
	        styleLabel.setBorderLeft(BorderStyle.THIN);  
	        styleLabel.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
	        styleLabel.setBorderTop(BorderStyle.THIN);  
	        styleLabel.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        
	        //252 228 214 OW-F5
	        styleOWF5.setFillForegroundColor(IndexedColors.PINK.getIndex());
	        styleOWF5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        styleOWF5.setBorderBottom(BorderStyle.THIN);  
	        styleOWF5.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
	        styleOWF5.setBorderRight(BorderStyle.THIN);  
	        styleOWF5.setRightBorderColor(IndexedColors.BLACK.getIndex());  
	        styleOWF5.setBorderLeft(BorderStyle.THIN);  
	        styleOWF5.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
	        styleOWF5.setBorderTop(BorderStyle.THIN);  
	        styleOWF5.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        
	        //221 235 247 CO-CO
	        styleCOCO.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
	        styleCOCO.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        styleCOCO.setBorderBottom(BorderStyle.THIN);  
	        styleCOCO.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
	        styleCOCO.setBorderRight(BorderStyle.THIN);  
	        styleCOCO.setRightBorderColor(IndexedColors.BLACK.getIndex());  
	        styleCOCO.setBorderLeft(BorderStyle.THIN);  
	        styleCOCO.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
	        styleCOCO.setBorderTop(BorderStyle.THIN);  
	        styleCOCO.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        
	        //226 239 218 QN-NC
	        styleQNNC.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	        styleQNNC.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        styleQNNC.setBorderBottom(BorderStyle.THIN);  
	        styleQNNC.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
	        styleQNNC.setBorderRight(BorderStyle.THIN);  
	        styleQNNC.setRightBorderColor(IndexedColors.BLACK.getIndex());  
	        styleQNNC.setBorderLeft(BorderStyle.THIN);  
	        styleQNNC.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
	        styleQNNC.setBorderTop(BorderStyle.THIN);  
	        styleQNNC.setTopBorderColor(IndexedColors.BLACK.getIndex());
	       

			for (int j = 0; j < tableTandem.getColumns().size(); j++) {
				row.createCell(j).setCellValue(tableTandem.getColumns().get(j).getText());
				row.getCell(j).setCellStyle(styleLabel);
			}

			for (int i = 0; i < tableTandem.getItems().size(); i++) {
				row = spreadsheet.createRow(i + 1);
				for (int j = 0; j < tableTandem.getColumns().size(); j++) {
					if (tableTandem.getColumns().get(j).getCellData(i) != null) {
						row.createCell(j).setCellValue(tableTandem.getColumns().get(j).getCellData(i).toString());
						if(tableTandem.getItems().get(i).toString().contains("CO-CO"))
							row.getCell(j).setCellStyle(styleCOCO);
						else if(tableTandem.getItems().get(i).toString().contains("OW-F5"))
							row.getCell(j).setCellStyle(styleOWF5);
						else if(tableTandem.getItems().get(i).toString().contains("QN-NC"))
							row.getCell(j).setCellStyle(styleQNNC);
					} else {
						row.createCell(j).setCellValue("");
					}
					
				}
			}

			try {
				FileOutputStream fileOut = new FileOutputStream(saveFile.getAbsoluteFile());
				workbook.write(fileOut);
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
			
		}

	}
	
	
	@FXML
	private void reFillData() {
		
		this.filterField.setText("");
		
		ObservableList<TandemData> list = null;
		if(comboBox.getValue().equals("Main")) {
			list = getMainTandemList();
		}else if(comboBox.getValue().equals("Close")) {
			list = getCloseTandemList();
		}else if(comboBox.getValue().equals("OW-F5")) {
			list = getOWF5TandemList();
		}else if(comboBox.getValue().equals("CO-CO")) {
			list = getCOCOTandemList();
		}else if(comboBox.getValue().equals("QN-NC")) {
			list = getQNNCTandemList();
		}
		
		tableTandem.setItems(list);
		countLabel.textProperty().bind(Bindings.size(tableTandem.getItems()).asString("Record count: %s"));
		filterTable(list);
	}
	
}