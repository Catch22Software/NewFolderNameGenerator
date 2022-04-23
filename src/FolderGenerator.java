import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FolderGenerator {


    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy"); // change this for
    // different date formats for the folders
    static String userDirectory = System.getProperty("user.dir");
    static File directoryPath = new File(userDirectory);
    static List<String> contents1 = Arrays.stream(Objects.requireNonNull(directoryPath
            .list((dir, name)
                    -> new File(dir, name).isDirectory())))
            .toList();
    static List<String> contents = new ArrayList<>(contents1);

        public static void main(String[] args) {
            String currentMonth = (LocalDate.now().getMonthValue() < 10)
                ? "0"+ LocalDate.now().getMonthValue()
                : String.valueOf(LocalDate.now().getMonthValue());
            String currentYear = String.valueOf(LocalDate.now().getYear());
            while(true) {
                JOptionPane.showMessageDialog(null
                        , "Welcome to my program!\nLeave answer blank for current month/year.");
                String yearResponse;

                try{
                    do{
                        yearResponse = JOptionPane.showInputDialog(null
                                ,"Please enter the four digit year.");
                        if(yearResponse == null)
                            System.exit(0);
                    }while((!(yearResponse.isBlank()) || !(yearResponse.isEmpty()))
                            && (Integer.parseInt(yearResponse) < 1900 || Integer.parseInt(yearResponse) > LocalDate.now().getYear()));
                    String monthResponse;
                    do{
                        monthResponse = JOptionPane.showInputDialog(null,"Please use the numerical " +
                                        "representation\n of the month, i.e. February = 2 ."
                                ,"From what Month would you like to start?",JOptionPane.QUESTION_MESSAGE);
                        if(monthResponse == null)
                            System.exit(0);
                    }while ((!(monthResponse.isBlank()) || !(monthResponse.isEmpty()))
                            && (Integer.parseInt(monthResponse) < 1 || Integer.parseInt(monthResponse) > 12));
                    int month = (monthResponse.isBlank() || monthResponse.isEmpty())
                            ? Integer.parseInt(currentMonth) : Integer.parseInt(monthResponse);
                    int year = (yearResponse.isBlank() || yearResponse.isEmpty())
                            ? Integer.parseInt(currentYear) : Integer.parseInt(yearResponse);
                    int confirm = JOptionPane.showConfirmDialog(null
                            , "You chose to start with: "+Month.of(month)+"\nof the year: "+year+"?");
                    if(confirm == 0){
                        makeFoldersOlder(month,year);
                        JOptionPane.showMessageDialog(null,"It has been completed.");
                        break;
                    }
                    else if(confirm == 1){
                        JOptionPane.showMessageDialog(null,"Please try your selection again." +
                                "\n Remember to use numbers only.");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Thank you");
                        System.exit(0);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"You have to use numbers only please!");
                }
            }
        }
        private static void makeFoldersOlder(int month, int year){
            List<LocalDate> queryMonthDates = ((LocalDate.of(year
                    , Month.of(month), 1))
                    .datesUntil(LocalDate.now().plusDays(1)))
                    .toList();
            List<String> formattedMonthDatesQueried = queryMonthDates
                    .stream()
                    .map(dateTimeFormatter::format)
                    .collect(Collectors.toList());
            formattedMonthDatesQueried.removeAll(contents);
            new ArrayList<>(formattedMonthDatesQueried.stream().map(date -> {
                File file = new File(userDirectory + "/" + date);
                return file.mkdir();
            }).toList()); // this creates the folders
        }
    }
