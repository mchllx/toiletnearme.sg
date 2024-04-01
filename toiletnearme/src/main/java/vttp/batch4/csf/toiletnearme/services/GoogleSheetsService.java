package vttp.batch4.csf.toiletnearme.services;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleSheetsService {
    void getSpreadSheetValues(Integer index) throws IOException, GeneralSecurityException;

}
