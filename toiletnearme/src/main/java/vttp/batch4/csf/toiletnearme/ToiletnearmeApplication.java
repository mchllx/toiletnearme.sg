package vttp.batch4.csf.toiletnearme;

import java.net.ServerSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.batch4.csf.toiletnearme.configs.WebSocketConfig;
import vttp.batch4.csf.toiletnearme.services.GoogleSheetsServiceImpl;
import vttp.batch4.csf.toiletnearme.services.UserService;

@SpringBootApplication
public class ToiletnearmeApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(ToiletnearmeApplication.class, args);
  }

  // @Autowired
  // UserService userSvc;
  
  @Autowired
  GoogleSheetsServiceImpl googleSheetSvc;

  // @Autowired
  // WebSocketConfig webSocketConfig;
  
  @Override
  public void run(String... args) throws Exception {
    System.out.println(">>> springboot");
    googleSheetSvc.getSpreadSheetValues(Utils.SHEET_MALE);
    googleSheetSvc.getSpreadSheetValues(Utils.SHEET_FEMALE);
    googleSheetSvc.getSpreadSheetValues(Utils.SHEET_HOTEL);

    // userSvc.selectUserByEmail("michcllelim@gmail.com");

  }

}
