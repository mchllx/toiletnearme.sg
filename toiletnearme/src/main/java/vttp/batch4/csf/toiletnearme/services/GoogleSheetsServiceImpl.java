package vttp.batch4.csf.toiletnearme.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
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
    public void getSpreadSheetValues() throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorisationConfig.getSheetsService();

        Sheets.Spreadsheets.Values.BatchGet request = sheetsService.spreadsheets().values().batchGet(spreadsheetId);

        request.setRanges(getSpreadSheetRange());
        request.setMajorDimension("ROWS");

        BatchGetValuesResponse batchGetValuesResponse = request.execute();
        List<List<Object>> spreadSheetValues = batchGetValuesResponse
            .getValueRanges().get(0).getValues();

        List<Object> headers = spreadSheetValues.remove(0);

        for (List<Object> row : spreadSheetValues) {
            System.out.println(row.getFirst());
            System.out.println(headers.getFirst());
        }
    }

    private List<String> getSpreadSheetRange() throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorisationConfig.getSheetsService();
        Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
        Spreadsheet spreadsheet = request.execute();
        Sheet sheet = spreadsheet.getSheets().get(0);
        int row = sheet.getProperties().getGridProperties().getRowCount();
        int col = sheet.getProperties().getGridProperties().getColumnCount();
        return Collections.singletonList("R1C1:R".concat(String.valueOf(row))
                .concat("C").concat(String.valueOf(col)));
    }

}