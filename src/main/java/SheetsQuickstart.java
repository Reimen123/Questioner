import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.main.Answers;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

public class SheetsQuickstart {
    public static final GsonFactory GSON_FACTORY = GsonFactory.getDefaultInstance();

    private static Credential authorize() {
        InputStream in = SheetsQuickstart.class.getResourceAsStream("/credentials.json");
        try {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    GSON_FACTORY, new InputStreamReader(in)
            );
            List<String> questionsList = List.of(SheetsScopes.SPREADSHEETS);

            GoogleAuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), GSON_FACTORY, clientSecrets, questionsList)
                    .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                    .setAccessType("offline")
                    .build();

            return new AuthorizationCodeInstalledApp(authorizationCodeFlow, new LocalServerReceiver())
                    .authorize("user");

        } catch (IOException e) {
            throw new RuntimeException("Cant find the json file " + e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Cant connect to " + e);
        }
    }

    public static Sheets getSheetsService() throws GeneralSecurityException, IOException {
        Credential credential = authorize();
        String APPLICATION_NAME = "Questioner";
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), GSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        Sheets sheetsService = getSheetsService();
        String sheetID = "122gQLvmrpRnRXimRXF5wOkrgAS-oE4lnlRG4qYqqD7s";
        Spreadsheet response = sheetsService.spreadsheets().get(sheetID).execute();
        HashMap<String, Answers> questionerMap = new HashMap<>();

        List<List<Object>> questioner = sheetsService.spreadsheets().values()
                .get(sheetID, response.getSheets().get(0).getProperties().getTitle())
                .execute().getValues();

        setAnswers(questionerMap, questioner);

        System.out.println(questionerMap);
    }

    private static void setAnswers(HashMap<String, Answers> questionerMap, List<List<Object>> questioner) {
        for (int row = 1; row <= questioner.size() - 1; row++) {
            String question = questioner.get(row).get(2).toString();
            Answers answers = new Answers();
            answers.setCategory(question(questioner, row, 1));
            answers.setFirstAnswer(question(questioner, row, 3));
            answers.setSecondAnswer(question(questioner, row, 4));
            answers.setThirdAnswer(question(questioner, row, 5));
            answers.setFourthAnswer(question(questioner, row, 6));
            answers.setCorrectAnswer(question(questioner, row, 7));
            answers.setDescription(question(questioner, row, 8));
            answers.setExplanation(question(questioner, row, 9));
            questionerMap.put(question, answers);
        }
    }

    private static String question(List<List<Object>> questioner, int row, int num) {
        return questioner.get(row).get(num).toString();
    }
}

