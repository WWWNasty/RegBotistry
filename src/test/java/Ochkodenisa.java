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
    }

}
