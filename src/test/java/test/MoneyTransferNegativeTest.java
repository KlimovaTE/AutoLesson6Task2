package test;

import data.DataHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferNegativeTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    public void setUpClose() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int card1Balance = dashboardPage.getCardBalance(1);
        int sumOfTransfer = 10000 - card1Balance;
        if (sumOfTransfer <= 0) {
            var transferPage = dashboardPage.choosingCardForTransfer(2);
            transferPage.transferToCard(2, sumOfTransfer);
        } else {
            var transferPage = dashboardPage.choosingCardForTransfer(1);
            transferPage.transferToCard(1, sumOfTransfer);
        }
    }

    @Test
    void shouldTransferMoneyToFirstCardOverBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        int card1BalanceBeforeTransfer = dashboardPage.getCardBalance(1);
        int card2BalanceBeforeTransfer = dashboardPage.getCardBalance(2);
        var transferPage = dashboardPage.choosingCardForTransfer(1);
        int sumOfTransfer = card2BalanceBeforeTransfer + 10;
        transferPage.transferToCard(1, sumOfTransfer);
        var card1BalanceAfterTransfer = dashboardPage.getCardBalance(1);
        var card2BalanceAfterTransfer = dashboardPage.getCardBalance(2);
        assertEquals(card1BalanceBeforeTransfer + sumOfTransfer, card1BalanceAfterTransfer);
        assertEquals(card2BalanceBeforeTransfer - sumOfTransfer, card2BalanceAfterTransfer);
    }

    @Test
    void shouldTransferMoneyToSecondCardOverBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        int card1BalanceBeforeTransfer = dashboardPage.getCardBalance(1);
        int card2BalanceBeforeTransfer = dashboardPage.getCardBalance(2);
        var transferPage = dashboardPage.choosingCardForTransfer(2);
        int sumOfTransfer = card1BalanceBeforeTransfer + 1;
        transferPage.transferToCard(2, sumOfTransfer);
        var card1BalanceAfterTransfer = dashboardPage.getCardBalance(1);
        var card2BalanceAfterTransfer = dashboardPage.getCardBalance(2);
        assertEquals(card2BalanceBeforeTransfer + sumOfTransfer, card2BalanceAfterTransfer);
        assertEquals(card1BalanceBeforeTransfer - sumOfTransfer, card1BalanceAfterTransfer);
    }
}
