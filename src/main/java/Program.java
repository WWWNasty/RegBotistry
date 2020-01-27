import com.codeborne.selenide.SelenideElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.*;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        User user = getUser();

        registerUser(user);

        writeResult(user);

    }

    private static void writeResult(User user) {
        String result = String.format(" логин: %s\n пароль: %s\n E-mail: %s", user.preparedUserName, user.password, user.emailValue);
        writeResultToFile(result);
    }

    private static void registerUser(User user) throws InterruptedException {
        open("https://line6.com/account/create.html");
        //и тут слип не нужен
        Thread.sleep(1000);
        $("#l_email").setValue(user.emailValue);
        $("#l_user").setValue(user.preparedUserName);
        $("#l_first").setValue(user.firstName);
        $("#l_last").setValue(user.lastName);
        $("#l_pass").sendKeys(user.password);
        //Лучше не жди, а проверь что появилась надпись Password ok!
        Thread.sleep(1000);
        $("#l_pass_repeat").setValue(user.password);
        //Тоже самое
        Thread.sleep(1000);

        $("form input[type = 'submit']").click();
        $("#user_data_complete").shouldBe();
    }

    private static User getUser() {
        final int emailElementIndex = 1;
        final int userNameLengthLimit = 16;
        final int userPasswordLimit = 20;
        final int usernameElementIndex = 0;

        open("https://randomuser.me");

        final SelenideElement email = $$("ul#values_list > li").get(emailElementIndex);
        final SelenideElement username = $$("ul#values_list > li").get(usernameElementIndex);

        final String[] usernameValue = username.getAttribute("data-value").split(" ", 2); // "Denis Kuliev" -> [ "Denis", "Kuliev ]
        final String generatedUserName = usernameValue[1] + new Random().nextInt(1000);

        User user = new User();

        user.preparedUserName = limitString(generatedUserName,userNameLengthLimit);
        user.emailValue = email.getAttribute("data-value");
        user.firstName = usernameValue[0];
        user.lastName = usernameValue[1];
        user.password = limitString(UUID.randomUUID().toString(),userPasswordLimit);

        return user;
    }

    //string int => string
    private static  String limitString (String s, int max){
        if(s.length() <= max){
            return s;
        }
        return s.substring(0, max);
    }

    private static void writeResultToFile(String result) {
        try (FileWriter writer = new FileWriter("result.txt", false)) {
            writer.write(result);

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}
