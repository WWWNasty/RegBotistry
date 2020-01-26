import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class Ochkodenisa {
    @Test
    public void userCanLoginByUsername() {
        open("https://randomuser.me");
        final int emailElementIndex = 1;
        final SelenideElement email = $$("li").get(emailElementIndex);

        final String emailValue = email.getAttribute("data-value");


        System.out.println(emailValue);

        //open("https://line6.com/account/create.html");
//        $(By.name("user.name")).setValue("johny");
//        $("#.submit").click();
//        $(".loading_progress").should(disappear); // Само подождёт, пока элемент исчезнет
//        $("#username").shouldHave(text("Hello, Johny!")); // Само подождёт, пока у элемента появится нужный текст
    }

}
