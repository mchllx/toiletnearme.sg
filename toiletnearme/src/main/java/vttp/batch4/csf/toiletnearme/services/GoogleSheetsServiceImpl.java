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
import vttp.batch4.csf.toiletnearme.exceptions.InsertToiletListingException;

@Service
public class GoogleSheetsServiceImpl implements GoogleSheetsService {
    private static final Logger logger = Logger.getLogger(GoogleSheetsServiceImpl.class.getName());
    
    @Value("${spreadsheet.id}")
    private String spreadsheetId;

    @Autowired
    private GoogleAuthorisationConfig googleAuthorisationConfig;

    @Autowired
    private ToiletService toiletSvc;
    
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
            // System.out.println(">>> processing new row");

            JsonObject rowDataObj = v.asJsonObject();
            JsonArray valuesArr = rowDataObj.getJsonArray("values");

            // unordered, nested keys, objects NOT json, empty cells required for col info
            if (valuesArr != null) {
                valuesArr.stream()
                    .map(j -> j.asJsonObject())
                    // .filter(j -> (j != null && !j.isEmpty()))
                    .forEach(j -> {
                        try {
                            String stringValue = j.get("userEnteredValue")
                            .asJsonObject()
                            .get("stringValue")
                            .toString()
                            .replaceAll("\\\\n|\\\\r", "");

                            if (!j.containsKey("hyperlink"))
                            processed.add(stringValue); 
                            // System.out.println(j.get("userEnteredValue").asJsonObject().get("stringValue"));

                            String hyperlink = j.get("hyperlink").toString();
                            StringBuilder sb = new StringBuilder();
                                sb.append(stringValue);
                                sb.append(",");
                                sb.append(hyperlink);

                            // System.out.println(">>> check:" +sb.toString());
                            processed.add(sb.toString());
                            // System.out.println(j.get("hyperlink").toString());
                        } catch (NullPointerException e1) {
                            // System.out.println(">>>null value, not added");
                        } 
                    });  
            }
            //Add to list after stream ends to avoid dupes
            results.add(processed);
            // System.out.println(">>> added:\n" + results.getLast());
        }
        // System.out.println(">>>>results:"+results);
       
        // TODO: Allow incomplete cells to be inserted
        String region = "";
        switch (index) {
            case 0:
            System.out.println(">>>>adding Male GSheet records");
            for (List<String> list : results) {
                try {
                    // Not all region cells have values, "\"" double quotes are part of the string
                    String value = list.get(0).replaceAll("\"", "");
                    // System.out.println(">>>check:"+value);
                    if ("NORTH-EAST".equals(value) || 
                        "NORTH".equals(value) || 
                        "EAST".equals(value) || 
                        "CENTRAL".equals(value) || 
                        "SOUTH".equals(value) || 
                        "WEST".equals(value) || 
                        "INSTITUTIONS".equals(value)) {
                        region = value;
                        // System.out.println(">>>region:"+region);
                        toiletSvc.insertGSheetToiletMale(
                            region, list.get(1), list.get(2), list.get(3));
                    } else {
                        toiletSvc.insertGSheetToiletMale(
                            region, list.get(0), list.get(1), list.get(2));
                    }
                } catch (IndexOutOfBoundsException e3) {
                    // System.out.println("incomplete");
                } catch (InsertToiletListingException e4) {
                    System.out.println("error");
                }
            }
            break;
            case 1:
            System.out.println(">>>>adding Female GSheet records");
            for (List<String> list : results) {
                try {
                    // Not all region cells have values, "\"" double quotes are part of the string
                    String value = list.get(0).replaceAll("\"", "");
                    // System.out.println(">>>check:"+value);
                    if ("NORTH-EAST".equals(value) || 
                        "NORTH".equals(value) || 
                        "EAST".equals(value) || 
                        "CENTRAL".equals(value) || 
                        "SOUTH".equals(value) || 
                        "WEST".equals(value) || 
                        "INSTITUTIONS".equals(value)) {
                        region = value;
                        // System.out.println(">>>region:"+region);
                        toiletSvc.insertGSheetToiletFemale(
                            region, list.get(1), list.get(2), list.get(3));
                    } else {
                        toiletSvc.insertGSheetToiletFemale(
                            region, list.get(0), list.get(1), list.get(2));
                    }
                } catch (IndexOutOfBoundsException e3) {
                    // System.out.println("incomplete");
                } catch (InsertToiletListingException e4) {
                    System.out.println("error");
                }
            }
            break;
            case 2:
            System.out.println(">>>>adding Hotel GSheet records");
                for (List<String> list : results) {
                    try {
                        toiletSvc.insertGSheetToiletHotel(
                    list.get(0), list.get(1), list.get(2)
                    , list.get(3), list.get(4));

                    } catch (IndexOutOfBoundsException e3) {
                        // System.out.println("incomplete");
                    } catch (InsertToiletListingException e4) {
                        System.out.println("error");
                    };
                } 
                break; 
            default:
                System.out.println("invalid sheet");
                break;
        }
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