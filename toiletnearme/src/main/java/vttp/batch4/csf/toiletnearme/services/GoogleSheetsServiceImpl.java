package vttp.batch4.csf.toiletnearme.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;

import vttp.batch4.csf.toiletnearme.configs.GoogleAuthorisationConfig;

@Service
public class GoogleSheetsServiceImpl implements GoogleSheetsService {

    private static final Logger logger = Logger.getLogger(GoogleSheetsServiceImpl.class.getName());
    
    @Value("${spreadsheet.id}")
    private String spreadsheetId;

    @Autowired
    private GoogleAuthorisationConfig googleAuthorisationConfig;

    @Override
    public void getSpreadSheetValues(Integer index) throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorisationConfig.getSheetsService();

        Sheets.Spreadsheets.Values.BatchGet request = sheetsService
            .spreadsheets()
            .values()
            .batchGet(spreadsheetId);

        // ranges = sheets
        request.setRanges(getSpreadSheet(index));
        request.setMajorDimension("ROWS");
        // System.out.println(">>>range\n" + request.getRanges());
        // System.out.println(">>>request\n" + request);

        BatchGetValuesResponse batchGetValuesResponse = request.execute();
        // System.out.println(">>>batch values:\n" + batchGetValuesResponse.getValueRanges());
        // System.out.println(">>>batch values:\n" + batchGetValuesResponse);
        
        List<List<Object>> spreadSheetValues = batchGetValuesResponse
            .getValueRanges()
            .get(0)
            .getValues();
        
        List<Object> headers = spreadSheetValues.get(1);
        
        if (index.equals(2)) {
            headers = spreadSheetValues.get(2);
        }
        // System.out.println(">>>spreadsheet values:\n" + spreadSheetValues.size());
        // System.out.println(">>>headers:\n" + headers);

        for (List<Object> row : spreadSheetValues) {
            System.out.println(headers);
            System.out.println(row);
        }
    }

    private List<String> getSpreadSheet(Integer index) throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorisationConfig.getSheetsService();
        Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
        Spreadsheet spreadsheet = request.execute();

        Sheet sheet = spreadsheet.getSheets().get(index);
        System.out.println(">>> name:" + sheet.getProperties().getTitle());
        // System.out.println(">>>sheet:\n" + sheet);

        String sheetName = sheet.getProperties().getTitle();
        String initialRowCol = "A1:AC";
        int row = sheet.getProperties().getGridProperties().getRowCount();
        int col = sheet.getProperties().getGridProperties().getColumnCount();
       
        // A single range "A1:AC1174"
        // Multiple ranges "Sheet1!A1:B10,Sheet2!C1:D10"
        // Named ranges "Sheet1!NamedRange"
        // e.g "'FEMALE TOILETS'!A1:AB1129"
        return Collections.singletonList(
                "'"
                .concat(sheetName)
                .concat("'!")
                .concat(initialRowCol) 
                .concat(String.valueOf(row)));
                // .concat("C")
                // .concat(String.valueOf(col)));
    }

}