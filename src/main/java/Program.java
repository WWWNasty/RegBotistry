import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

import static com.codeborne.selenide.Selenide.*;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        open("https://randomuser.me");
        //
        final int emailElementIndex = 1;
        final SelenideElement email = $$("ul#values_list > li").get(emailElementIndex);
        final String emailValue = email.getAttribute("data-value");

        final int usernameElementIndex = 0;
        final SelenideElement username = $$("ul#values_list > li").get(usernameElementIndex);
        final String[] usernameValue = username.getAttribute("data-value").split(" ", 2); // "Denis Kuliev" -> [ "Denis", "Kuliev ]
        final String generatedUserName = usernameValue[1] + new Random().nextInt(1000);

        final int userNameLengthLimit = 16;

        final String preparedUserName = limitString(generatedUserName,userNameLengthLimit);

        final String firstName = usernameValue[0];
        final String lastName = usernameValue[1];
        String password = limitString(UUID.randomUUID().toString(),userNameLengthLimit);

        open("https://line6.com/account/create.html");
        Thread.sleep(1000);
        $("#l_email").setValue(emailValue);
        $("#l_user").setValue(preparedUserName);
        $("#l_first").setValue(firstName);
        $("#l_last").setValue(lastName);
        $("#l_pass").sendKeys(password);
        Thread.sleep(1000);
        $("#l_pass_repeat").setValue(password);
        Thread.sleep(1000);


        System.out.println(emailValue);
        System.out.println(preparedUserName);
        $("form input[type = 'submit']").click();
        $("#user_data_complete").shouldBe();

        String result = String.format(" логин: %s\n пароль: %s\n E-mail: %s", preparedUserName, password, emailValue);

        writeResultToFile(result);



    }
    //string int => string
    private static  String limitString (String s, int max){
        return s
               .codePoints()
               .limit(max)
               .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
               .toString();
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
