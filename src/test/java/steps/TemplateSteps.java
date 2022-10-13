package steps;

import com.codeborne.selenide.Selenide;
import data.DataHelper;
import io.cucumber.java.ru.*;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @И("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
    }

    @И("пользователь вводит проверочный код 'из смс' {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(DataHelper.getAuthInfo()));
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void makeTransfer(int transfer, String numberCard, int indexOfCad) {
        var transferPage = dashboardPage.choosingCardForTransfer(indexOfCad);
        transferPage.transferToCard(numberCard, transfer);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void verifyBalance(int indexOfCad, int balance) {
        assertEquals(dashboardPage.getCardBalance(indexOfCad), balance);
    }
}
