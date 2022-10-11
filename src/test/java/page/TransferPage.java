package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement fromCard = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public void transferToCard(int indexOfCard, int sumOfTransfer) {
        amount.setValue(String.valueOf(sumOfTransfer));
        if (indexOfCard == 1) {
            fromCard.setValue(DataHelper.getCardFullNumber(indexOfCard));
        } else {
            fromCard.setValue(DataHelper.getCardFullNumber(indexOfCard-2));
        }
        transferButton.click();
    }
}