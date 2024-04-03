package vttp.batch4.csf.toiletnearme.services;


import java.io.IOException;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
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

        Sheets.Spreadsheets.Get request = sheetsService
            .spreadsheets()
            .get(spreadsheetId);

        String field = "sheets(data(rowData(values(hyperlink,userEnteredValue))))"; 
            request.setFields(field);
            request.setIncludeGridData(false);
            request.setRanges(getSpreadSheet(index));

        // System.out.println(">>>range\n" + request.getRanges());
        // System.out.println(">>>request\n" + request);

        Spreadsheet getSpreadSheetResponse = request.execute();
        // System.out.println(">>>batch values:\n" + getValuesResponse);

        String payload = getSpreadSheetResponse.toString();
        // System.out.println(">>>spreadsheet values:\n" + spreadSheetValues);

        // {"sheets": [{"data": [{"rowData": [{ }, {"values": [{"hyperlink":}, {"userEnteredValue":{"stringValue":"Region"}}, {}, {}...]
        // }]}]}]}
        // {: [{: [{: [{}, {: [{}, {}
        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject response = jr.readObject();
        JsonArray sheetsArr = response.getJsonArray("sheets");
        JsonObject sheetsObj = sheetsArr.getJsonObject(0);
        JsonArray dataArr = sheetsObj.getJsonArray("data");
        JsonObject dataObj = dataArr.getJsonObject(0);
        JsonArray rowDataArr = dataObj.getJsonArray("rowData");

        List<List<String>> results = new LinkedList<>();

        for (JsonValue v : rowDataArr) {
            // DO NOT use clear(), final actions updates references into last value
            List<String> processed = new LinkedList<>();
            System.out.println(">>> processing new row");

            JsonObject rowDataObj = v.asJsonObject();
            JsonArray valuesArr = rowDataObj.getJsonArray("values");

            // unordered, nested keys, objects NOT json
            if (valuesArr != null) {
                valuesArr.stream()
                    .map(j -> j.asJsonObject())
                    .filter(j -> ((j != null && !j.isEmpty())))
                    .forEach(j -> {
                        try {
                            String stringValue = j.get("userEnteredValue")
                            .asJsonObject()
                            .get("stringValue")
                            .toString()
                            .replaceAll("\\\\n|\\\\r", "");

                            processed.add(stringValue); 
                            System.out.println(j.get("userEnteredValue").asJsonObject().get("stringValue"));
                            
                            String hyperlink = j.get("hyperlink")
                                .toString();
                                
                            processed.add(hyperlink);
                            System.out.println(j.get("hyperlink").toString());

                            results.add(processed);
                            System.out.println(">>> added:\n" + results.getLast());

                        } catch (NullPointerException e) {
                            logger.info(">>>null value, not added");
                        } 
                    });  
            }
        }
        System.out.println(">>>results:\n" + results);
    }

    private List<String> getSpreadSheet(Integer index) throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorisationConfig.getSheetsService();
        Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
        Spreadsheet spreadsheet = request.execute();

        Sheet sheet = spreadsheet.getSheets().get(index);
        // System.out.println(">>> retrieving range:" + sheet.getProperties().getTitle());
        // System.out.println(">>>sheet:\n" + sheet);

        String sheetName = sheet.getProperties().getTitle();
        String initialRowCol = "A1:AC";
        int row = sheet.getProperties().getGridProperties().getRowCount();
        // int col = sheet.getProperties().getGridProperties().getColumnCount();
       
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

    // Unable to retrieve hyperlink values
    // @Override
    // public void getSpreadSheetValuesNoLinks(Integer index) throws IOException, GeneralSecurityException {
    //     Sheets sheetsService = googleAuthorisationConfig.getSheetsService();

    //     Sheets.Spreadsheets.Values.BatchGet request = sheetsService
    //         .spreadsheets()
    //         .values()
    //         .batchGet(spreadsheetId);

    //     // // ranges = sheets
    //     request.setMajorDimension("ROWS");
    //     request.setValueRenderOption("FORMULA");

    //     // System.out.println(">>>range\n" + request.getRanges());
    //     // System.out.println(">>>request\n" + request);

    //     BatchGetValuesResponse batchGetValuesResponse = request.execute();
    //     // System.out.println(">>>batch values:\n" + batchGetValuesResponse.getValueRanges());
    //     // System.out.println(">>>batch values:\n" + batchGetValuesResponse);

    //     List<List<Object>> spreadSheetValues = batchGetValuesResponse
    //         .getValueRanges()
    //         .get(0)
    //         .getValues();
        
    //     // When major dimensions are set to ROWS
    //     List<Object> headers = spreadSheetValues.get(0);
        
    //     if (index.equals(2)) {
    //         headers = spreadSheetValues.get(1);
    //     }

    //     // System.out.println(">>>spreadsheet values:\n" + spreadSheetValues);
    //     // System.out.println(">>>headers:\n" + headers);

    //     for (List<Object> row : spreadSheetValues) {
    //         // System.out.println(headers);
    //         if (index.equals(2)) {
    //             System.out.println(row.get(3)); 
    //         } else {
    //         System.out.println(row.get(2));
    //         }
    //     }
    // }
}