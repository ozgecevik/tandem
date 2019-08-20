package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Service.DataService;
import data.TandemData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ControllerDialog implements Initializable {

	
	@FXML
	private ImageView imageSuccess;
	
	@FXML
	private TextFlow textArea;
	
	
	private Integer processedItem;
	private Integer deletedItem;
	private Integer newItem;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Image img = new Image("/etc/success.png");
		imageSuccess.setImage(img);
		
		// Creating text objects
		Text text1 = new Text("\n\nProcessing Item(s): " + DataService.getInstance().getRawDataList().size());

		// Setting font to the text
		text1.setFont(new Font(18));

		// Setting color to the text
		text1.setFill(Color.DIMGREY);

		Text text2 = new Text("\nNew Item(s): " + DataService.getInstance().getNewData().size());

		// Setting font to the text
		text2.setFont(new Font(18));

		// Setting color to the text
		text2.setFill(Color.MEDIUMSEAGREEN);
		Text text3 = new Text("\nClosed Item(s): " + DataService.getInstance().getClosedData().size());

		// Setting font to the text
		text3.setFont(new Font(18));

		// Setting color to the text
		text3.setFill(Color.RED);
//
//		Text text4 = new Text("We believe in easy learning");

		// Setting font to the text
//		text4.setFont(new Font(15));
//		text4.setFill(Color.MEDIUMVIOLETRED);

		// Setting the line spacing between the text objects
		textArea.setTextAlignment(TextAlignment.CENTER);


		// Setting the line spacing
		textArea.setLineSpacing(5.0);

		// Retrieving the observable list of the TextFlow Pane
		ObservableList list = textArea.getChildren();

		// Adding cylinder to the pane
		list.addAll(text1, text2, text3);
		
	}


		
	

	
		
		

}