package Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import data.TandemData;

public class DataService {

	private ArrayList<TandemData> rawDataList;

	private ArrayList<TandemData> mainDataList;

	private ArrayList<TandemData> newData;

	private ArrayList<TandemData> recentlyClosedData;
	
	private ArrayList<TandemData> closedData;

	private ArrayList<TandemData> remainSameData;

	private static DataService instance;

	private DataService() {
	}

	public static DataService getInstance() {

		if (instance == null) {
			instance = new DataService();
		}
		return instance;
	}

	public void readRawData(File rawTandem) throws IOException {

		this.rawDataList = new ArrayList<TandemData>();
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(rawTandem);
		Workbook wb = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name
		String fileExtensionName = rawTandem.getName().substring(rawTandem.getName().indexOf("."));

		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class
			wb = new XSSFWorkbook(inputStream);

		}

		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class
			wb = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		Sheet sheet = wb.getSheetAt(0);

		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Create a loop over all the rows of excel file to read it
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);

			// Create Raw Data
			TandemData rawData = new TandemData();
			if (row.getCell(0) != null) {
				if (row.getCell(0).getCellType().equals(CellType.STRING))
					rawData.setNcStatus(row.getCell(0).getStringCellValue());
				else if (row.getCell(0).getCellType().equals(CellType.NUMERIC))
					rawData.setNcStatus(row.getCell(0).getNumericCellValue() + "");
			}
			if (row.getCell(1) != null)
				rawData.setMSN((int) row.getCell(1).getNumericCellValue());
			if (row.getCell(2) != null)
				rawData.setConstituentAssembly(row.getCell(2).getStringCellValue());
			if (row.getCell(3) != null)
				rawData.setNatcoType(row.getCell(3).getStringCellValue());
			if (row.getCell(4) != null) {
				if (row.getCell(4).getCellType().equals(CellType.STRING))
					rawData.setReference(row.getCell(4).getStringCellValue());
				else if (row.getCell(4).getCellType().equals(CellType.NUMERIC))
					rawData.setReference(row.getCell(4).getNumericCellValue() + "");
			}
			if (row.getCell(5) != null)
				rawData.setDescription(row.getCell(5).getStringCellValue());
			if (row.getCell(6) != null)
				rawData.setEscalationPriority(row.getCell(6).getStringCellValue());
			if (row.getCell(7) != null)
				rawData.setEscalationStatus(row.getCell(7).getStringCellValue());
			if (row.getCell(8) != null)
				rawData.setComment(row.getCell(8).getStringCellValue());
			if (row.getCell(9) != null)
				rawData.setConversation(row.getCell(9).getStringCellValue());
			if (row.getCell(10) != null)
				rawData.setDeadlineDate(row.getCell(10).getDateCellValue());
			if (row.getCell(11) != null)
				rawData.setTag(row.getCell(11).getStringCellValue());
			this.rawDataList.add(rawData);

		}
	}

	public void readMainData(File mainTandem) throws IOException {

		this.mainDataList = new ArrayList<TandemData>();

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(mainTandem);
		Workbook wb = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name
		String fileExtensionName = mainTandem.getName().substring(mainTandem.getName().indexOf("."));

		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class
			try {
				wb = new XSSFWorkbook(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class
			wb = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		Sheet sheet = wb.getSheet("maintandem");

		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Create a loop over all the rows of excel file to read it
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);

			// Create Main Data
			TandemData mainData = new TandemData();
			if (row.getCell(0) != null)
				mainData.setMSN((int) row.getCell(0).getNumericCellValue());
			if (row.getCell(1) != null)
				mainData.setConstituentAssembly(row.getCell(1).getStringCellValue());
			if (row.getCell(2) != null)
				mainData.setNatcoType(row.getCell(2).getStringCellValue());
			if (row.getCell(3) != null) {
				if (row.getCell(3).getCellType().equals(CellType.STRING))
					mainData.setReference(row.getCell(3).getStringCellValue());
				else if (row.getCell(3).getCellType().equals(CellType.NUMERIC))
					mainData.setReference(row.getCell(3).getNumericCellValue() + "");
			}
			if (row.getCell(4) != null)
				mainData.setDescription(row.getCell(4).getStringCellValue());
			if (row.getCell(5) != null)
				mainData.setComment(row.getCell(5).getStringCellValue());
			if (row.getCell(6) != null)
				mainData.setOldConversation(row.getCell(6).getStringCellValue());
			if (row.getCell(7) != null)
				mainData.setConversation(row.getCell(7).getStringCellValue());
			if (row.getCell(8) != null)
				mainData.setMyComment(row.getCell(8).getStringCellValue());
			if (row.getCell(9) != null)
				mainData.setDetailedComment(row.getCell(9).getStringCellValue());
			if (row.getCell(10) != null)
				mainData.setStatus(row.getCell(10).getStringCellValue());

			this.mainDataList.add(mainData);
			System.out.println("i" + i);
		}
			
			//read closed tab
			this.closedData = new ArrayList<TandemData>();
			// Read sheet inside the workbook by its name
			Sheet sheet2 = wb.getSheet("closed");
			
			// Find number of rows in excel file
			int rowCount2 = sheet2.getLastRowNum() - sheet2.getFirstRowNum();
			System.out.println(rowCount2);
			// Create a loop over all the rows of excel file to read it
			for (int j = 1; j < rowCount2 + 1; j++) {
				Row row2 = sheet2.getRow(j);

				// Create Main Data
				TandemData closedData = new TandemData();
				if (row2.getCell(0) != null)
					closedData.setMSN((int) row2.getCell(0).getNumericCellValue());
				if (row2.getCell(1) != null)
					closedData.setConstituentAssembly(row2.getCell(1).getStringCellValue());
				if (row2.getCell(2) != null)
					closedData.setNatcoType(row2.getCell(2).getStringCellValue());
				if (row2.getCell(3) != null) {
					if (row2.getCell(3).getCellType().equals(CellType.STRING))
						closedData.setReference(row2.getCell(3).getStringCellValue());
					else if (row2.getCell(3).getCellType().equals(CellType.NUMERIC))
						closedData.setReference(row2.getCell(3).getNumericCellValue() + "");
				}
				if (row2.getCell(4) != null)
					closedData.setDescription(row2.getCell(4).getStringCellValue());
				if (row2.getCell(5) != null)
					closedData.setComment(row2.getCell(5).getStringCellValue());
				if (row2.getCell(6) != null)
					closedData.setOldConversation(row2.getCell(6).getStringCellValue());
				if (row2.getCell(7) != null)
					closedData.setConversation(row2.getCell(7).getStringCellValue());
				if (row2.getCell(8) != null)
					closedData.setMyComment(row2.getCell(8).getStringCellValue());
				if (row2.getCell(9) != null)
					closedData.setDetailedComment(row2.getCell(9).getStringCellValue());
				if (row2.getCell(10) != null)
					closedData.setStatus(row2.getCell(10).getStringCellValue());

				this.closedData.add(closedData);
				System.out.println("j" + j);
		}
	}

	public void compareTandemData() {

		this.newData = (ArrayList<TandemData>) CollectionUtils.removeAll(this.rawDataList, this.mainDataList);
		this.recentlyClosedData = (ArrayList<TandemData>) CollectionUtils.removeAll(this.mainDataList, this.rawDataList);
		this.remainSameData = (ArrayList<TandemData>) CollectionUtils.removeAll(this.mainDataList, this.recentlyClosedData);

	}

	public void updateMainExcel(File mainTandem) throws IOException {
		
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(mainTandem);

        Workbook wb = null;

        //Find the file extension by splitting  file name in substring and getting only extension name
        String fileExtensionName = mainTandem.getName().substring(mainTandem.getName().indexOf("."));

        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")){
        //If it is xlsx file then create object of XSSFWorkbook class
        wb = new XSSFWorkbook(inputStream);
        }

        //Check condition if the file is xls file
        else if(fileExtensionName.equals(".xls")){
            //If it is xls file then create object of XSSFWorkbook class
            wb = new HSSFWorkbook(inputStream);
        }    

        //Read excel sheet by sheet name    
        Sheet sheet = wb.getSheet("maintandem");
        
        // Styling border of cell.  
        CellStyle style = wb.createCellStyle();  
        style.setBorderBottom(BorderStyle.THIN);  
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderRight(BorderStyle.THIN);  
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderLeft(BorderStyle.THIN);  
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderTop(BorderStyle.THIN);  
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());  
        
        for (int i = 0; i < this.mainDataList.size(); i++) {
        	TandemData current = this.mainDataList.get(i);
        	Row row = sheet.getRow(i + 1);
        	
        	//update fields
        	for(int j = 0; j < 11; j++){
        		Cell cell = row.createCell(j);
        		cell.setCellStyle(style);  
        		if(j == 0)
        			cell.setCellValue(current.getMSN());
        		if(j == 1)
        			cell.setCellValue(current.getConstituentAssembly());
        		if(j == 2)
        			cell.setCellValue(current.getNatcoType());
        		if(j == 3)
        			cell.setCellValue(current.getReference());
        		if(j == 4)
        			cell.setCellValue(current.getDescription());
        		if(j == 5)
        			cell.setCellValue(current.getComment());
        		if(j == 6)
        			cell.setCellValue(current.getOldConversation());
        		if(j == 7)
        			cell.setCellValue(current.getConversation());
        		if(j == 8)
            		cell.setCellValue(current.getMyComment());
            	if(j == 9)
            		cell.setCellValue(current.getDetailedComment());
           		if(j == 10) {
           			if(this.rawDataList.contains(current)) {
           				cell.setCellValue("OPEN");
           			}else {
           				cell.setCellValue("CLOSED");
           			}
           		}
        	}
		}
        
        for (int i = 0; i < this.newData.size(); i++) { //add new data
        	TandemData newData = this.newData.get(i);
        	Row row = sheet.createRow(this.mainDataList.size()+1+i);
        	//update fields
        	for(int j = 0; j < 11; j++){
        		Cell cell = row.createCell(j);
        		cell.setCellStyle(style);  
        		if(j == 0)
        			cell.setCellValue(newData.getMSN());
        		if(j == 1)
        			cell.setCellValue(newData.getConstituentAssembly());
        		if(j == 2)
        			cell.setCellValue(newData.getNatcoType());
        		if(j == 3)
        			cell.setCellValue(newData.getReference());
        		if(j == 4)
        			cell.setCellValue(newData.getDescription());
        		if(j == 5)
        			cell.setCellValue(newData.getComment());
        		if(j == 7)
        			cell.setCellValue(newData.getConversation());
           		if(j == 10) {
           			cell.setCellValue("NEW");
           		}
        	}
        }
        
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        
        //Read excel sheet by sheet name    
        Sheet sheet2 = wb.getSheet("closed");
        
        for (int i = 0; i < this.recentlyClosedData.size(); i++) { 
        	TandemData closedData = this.recentlyClosedData.get(i);
        	Row row = sheet2.createRow(sheet2.getLastRowNum() + 1);
        	//update fields
        	for(int j = 0; j < 11; j++){
        		Cell cell = row.createCell(j);
        		cell.setCellStyle(style);  
        		if(j == 0)
        			cell.setCellValue(closedData.getMSN());
        		if(j == 1)
        			cell.setCellValue(closedData.getConstituentAssembly());
        		if(j == 2)
        			cell.setCellValue(closedData.getNatcoType());
        		if(j == 3)
        			cell.setCellValue(closedData.getReference());
        		if(j == 4)
        			cell.setCellValue(closedData.getDescription());
        		if(j == 5)
        			cell.setCellValue(closedData.getComment());
        		if(j == 6)
        			cell.setCellValue(closedData.getOldConversation());
        		if(j == 7)
        			cell.setCellValue(closedData.getConversation());
        		if(j == 8)
            		cell.setCellValue(closedData.getMyComment());
            	if(j == 9)
            		cell.setCellValue(closedData.getDetailedComment());
           		if(j == 10) {
           			cell.setCellValue("CLOSED" + format.format(Calendar.getInstance().getTime()));
           		}
        	}
        }
        

        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(mainTandem);
        wb.write(outputStream);
        outputStream.close();
	}

	public ArrayList<TandemData> getNewData() {
		return newData;
	}

	public ArrayList<TandemData> getrecentlyClosedData() {
		return recentlyClosedData;
	}

	public ArrayList<TandemData> getRawDataList() {
		return rawDataList;
	}

	public ArrayList<TandemData> getMainDataList() {
		return mainDataList;
	}

	public ArrayList<TandemData> getClosedData() {
		return closedData;
	}

	
	
}
