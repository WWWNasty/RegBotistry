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
        Thread.sleep(1000);
        $("#l_email").setValue(user.emailValue);
        $("#l_user").setValue(user.preparedUserName);
        $("#l_first").setValue(user.firstName);
        $("#l_last").setValue(user.lastName);
        $("#l_pass").sendKeys(user.password);
        Thread.sleep(1000);
        $("#l_pass_repeat").setValue(user.password);
        Thread.sleep(1000);

        $("form input[type = 'submit']").click();
        $("#user_data_complete").shouldBe();
    }

    private static User getUser() {
        open("https://randomuser.me");
        User user = new User();
        final int emailElementIndex = 1;
        final SelenideElement email = $$("ul#values_list > li").get(emailElementIndex);

        user.emailValue = email.getAttribute("data-value");

        final int usernameElementIndex = 0;
        final SelenideElement username = $$("ul#values_list > li").get(usernameElementIndex);
        final String[] usernameValue = username.getAttribute("data-value").split(" ", 2); // "Denis Kuliev" -> [ "Denis", "Kuliev ]
        final String generatedUserName = usernameValue[1] + new Random().nextInt(1000);
        final int userNameLengthLimit = 16;

        user.preparedUserName = limitString(generatedUserName,userNameLengthLimit);

        user.firstName = usernameValue[0];
        user.lastName = usernameValue[1];
        user.password = limitString(UUID.randomUUID().toString(),userNameLengthLimit);
        return user;
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
