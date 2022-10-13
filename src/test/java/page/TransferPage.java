package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement fromCard = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public void transferToCard(String cardNumber, int sumOfTransfer) {
        amount.setValue(String.valueOf(sumOfTransfer));
        fromCard.setValue(cardNumber);
        transferButton.click();
    }
}