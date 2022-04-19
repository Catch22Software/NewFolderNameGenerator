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


    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yy"); // change this for
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
            while(true) {
                String response = JOptionPane.showInputDialog(JOptionPane.getRootFrame(),"Please use the numerical " +
                        "representation\n of the month, i.e. February = 2 ."
                        ,"From what Month would you like to start?",JOptionPane.QUESTION_MESSAGE);
                if (response == null || response.isBlank() || response.isEmpty()) {
                    makeFoldersOlder(Integer.parseInt(currentMonth));
                    break;
                }
                else if(response.length() == 1 && Character.isDigit(response.charAt(0))){
                    if(Integer.parseInt(response) < 13 && Integer.parseInt(response) > 0){
                        makeFoldersOlder(Integer.parseInt(response));
                        break;
                    }
                }
                else if(response.length() == 2 && ((Character.isDigit(response.charAt(0))) && (Character.isDigit(response.charAt(1))))){
                    makeFoldersOlder(Integer.parseInt(response));
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame()
                            ,"NOPE!!! Please reread the directions!!"
                            ,"ERROR!!!!"
                            , JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        private static void makeFoldersOlder(int month){
            List<LocalDate> queryMonthDates = ((LocalDate.of(LocalDate.now().getYear()
                    , Month.of(month), 1))
                    .datesUntil(LocalDate.now().plusDays(1)))
                    .toList();
            List<String> formattedMonthDatesQueried = queryMonthDates
                    .stream()
                    .map(dateTimeFormatter::format)
                    .collect(Collectors.toList());
            formattedMonthDatesQueried.removeAll(contents);
            formattedMonthDatesQueried.stream().map(date -> {
                File file = new File(userDirectory+"/"+date);
                return file.mkdir();
            }).toList();
        }
    }
