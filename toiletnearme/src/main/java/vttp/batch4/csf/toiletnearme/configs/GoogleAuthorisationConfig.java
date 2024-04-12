package vttp.batch4.csf.toiletnearme.configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

@Configuration
public class GoogleAuthorisationConfig {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Value("${application.name}")
    private String applicationName;

    @Value("${credentials.file.path}")
    private String credentialsFilePath;

    @Value("${tokens.directory.path}")
    private String tokensDirectoryPath;

    public GoogleAuthorisationConfig() {
    }

    private Credential authorise() throws IOException, GeneralSecurityException {
        InputStream in = GoogleAuthorisationConfig.class.getResourceAsStream(credentialsFilePath);
        InputStreamReader isr = new InputStreamReader(in);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, isr);

        List<String> scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
            .Builder(
                GoogleNetHttpTransport.newTrustedTransport()
                , JSON_FACTORY, clientSecrets, scopes)
                    .setDataStoreFactory(
                    new FileDataStoreFactory(
                    new File(tokensDirectoryPath)
                ))
                .setAccessType("offline")
                .build();

        LocalServerReceiver localServerReceiver = new LocalServerReceiver
            .Builder()
            .setPort(8888)
            .build();

        return new AuthorizationCodeInstalledApp(
            flow, localServerReceiver)
            .authorize("user");  
    }

    public Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorise();

        if (credential.getRefreshToken() == null) {
            System.out.println("Refresh token expired or invalid");
        }

        return new Sheets.Builder(
            GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
            .setApplicationName(applicationName)
            .build();        
    }
    
}
