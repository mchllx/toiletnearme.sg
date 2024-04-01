package vttp.batch4.csf.toiletnearme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.batch4.csf.toiletnearme.services.GoogleSheetsServiceImpl;

@SpringBootApplication
public class ToiletnearmeApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(ToiletnearmeApplication.class, args);
  }

  @Autowired
  GoogleSheetsServiceImpl googleSheetSvc;
  
  @Override
  public void run(String... args) throws Exception {
    System.out.println(">>> springboot");
    googleSheetSvc.getSpreadSheetValues();

  }

}
