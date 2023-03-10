package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class MainPage {

    private final SelenideElement emailInput = $("#email");
    private final SelenideElement passInput = $("#password");
    private final String addedItem = "Nutrend Excelent Protein Bar 9 x 85 g";
    private SelenideElement
            logo = $(".logo__header"),
            medicineButton = $("[data-root-item=item-1032]"),
            additivesButton = $("[data-root-item=item-1089]"),
            motherButton = $("[data-root-item=item-1167]"),
            electroButton = $("[data-root-item=item-2421]"),
            cosmeticButton = $("[data-root-item=item-1399]"),
            drugstoreButton = $("[data-root-item=item-2120]"),
            foodButton = $("[data-root-item=item-1513]"),
            sportButton = $("[data-root-item=item-1583]"),
            toothButton = $("[data-root-item=item-1625]"),
            healthButton = $("[data-root-item=item-6899]"),
            eroticaButton = $("[data-root-item=item-5472]"),
            animalButton = $("[data-root-item=item-14050]"),
            searchInput = $("#js-search-form-q"),
            searchResult = $("#product-list"),
            subMenu = $$(".main-nav__category-title.js_level-2").findBy(text("Proteinov√© potraviny")),
            chosenItem = $(byText(addedItem)),
            addItem = $("#js-add-to-cart-first",2),
            basket = $(".btn.btn-primary.btn-sm.px-3.d-inline-block.fwb"),
            basketId = $("#js-cart-open"),
            saleLink = $("#js-mobile-banner-car-cart [href=/vyprodej]"),
            basketSubMenu = $("#js-mobile-banner-car-cart"),
            mainTitle = $("#main-title"),
            controlItem = $(".ml-2.mr-2.ml-md-0.mr-md-0"),
            authUser = $("[title=PŇôihl√°sit se]"),
            authButton = $$("[data-method=Regular]").last(),
            checkAuth = $(".js-validate-error-label"),
            modalWindow = $(byText("Kdy bude zboŇĺ√≠ u v√°s?")),
            modalWindowCloseButton = $("[aria-label=ZavŇô√≠t mod√°ln√≠ okno]", 1),
            checkFooter = $(".ml-lg-4.pl-lg-4.pl-1.ml-1.fwn.footer__bottom-cs-links", 2);

    @Step("Open {baseUrl}")
    public MainPage openPage() {
        open(baseUrl);
        return this;
    }

    @Step("Close a message about cookies")
    public MainPage closeCookiesMessage() {
        $(byText("Odm√≠tnout vŇ°echny cookies")).click();
        return this;
    }

    @Step("Check menu items")
    public void checkMenuItems() {
        step("Check menu item name 'L√©ky'", () ->
                medicineButton.shouldHave(text("L√©ky")));
        step("Check menu item name 'DoplŇąky stravy'", () ->
                additivesButton.shouldHave(text("DoplŇąky stravy")));
        step("Check menu item name 'Matka a d√≠tńõ'", () ->
                motherButton.shouldHave(text("Matka a d√≠tńõ")));
        step("Check menu item name 'Elektro'", () ->
                electroButton.shouldHave(text("Elektro")));
        step("Check menu item name 'Kosmetika'", () ->
                cosmeticButton.shouldHave(text("Kosmetika")));
        step("Check menu item name 'Drogerie'", () ->
                drugstoreButton.shouldHave(text("Drogerie")));
        step("Check menu item name 'Potraviny, n√°poje'", () ->
                foodButton.shouldHave(text("Potraviny, n√°poje")));
        step("Check menu item name 'Sport'", () ->
                sportButton.shouldHave(text("Sport")));
        step("Check menu item name '√östa a zuby'", () ->
                toothButton.shouldHave(text("√östa a zuby")));
        step("Check menu item name 'Zdravotnick√© potŇôeby'", () ->
                healthButton.shouldHave(text("Zdravotnick√© potŇôeby")));
        step("Check menu item name 'Erotika'", () ->
                eroticaButton.shouldHave(text("Erotika")));
        step("Check menu item name 'Veterina'", () ->
                animalButton.shouldHave(text("Veterina")));
    }

    @Step("Search for {text}")
    public MainPage search(String text) {
        searchInput.setValue(text).pressEnter();
        return this;
    }

    @Step("Check {headerText} in search results")
    public void checkInSearchResults(String headerText) {
        searchResult.shouldHave(text(headerText));
    }

    @Step("Check that the basket is empty")
    public void checkEmptyBasket() {
        step("Hover basket", () ->
                basketId.hover());
        step("Check sub-menu item text", () -> {
            basketSubMenu.shouldHave(text("Ve sv√©m koŇ°√≠ku zat√≠m nic nem√°te"));
        });
    }

    @Step("Open Sales from the basket")
    public void checkSalesInBasket() {
        step("Hover basket", () ->
                basketId.hover());
        step("Open Sales", () -> {
            saleLink.click();
            mainTitle.shouldHave(text("V√Ĺprodej"));
        });
    }

    @Step("Add an item into the basket")
    public void addItemInBasket() {
        step("Hover 'Sport' button in the menu", () ->
                sportButton.hover());
        step("Click by sub-menu item", () ->
                subMenu.click());
        step("Choose item in the list", () ->
                chosenItem.click());

        //sometimes the modal window appears and breaks the test, looks like it works not for all clients
        //that's why if construction is here, 'sleep' helps to catch the window
        sleep(5000);
        step("Check if modal window is opened", () -> {
            if (modalWindow.isDisplayed()) {
                step("Close modal window", () -> {
                    modalWindowCloseButton.click();
                    logo.hover();
                });
            }
        });

        step("Add item in the basket", () -> {
            addItem.click();
        });

        step("Go to the basket", () ->
                basket.click());
        step("Check that the basket contains the item", () ->
                controlItem.shouldHave(text(addedItem)));
    }

    @Step("Login with {username}/{password}")
    public void loginWithError(String username, String password) {
        step("Click by authorization button", () ->
                authUser.click());
        step("Enter username", () ->
                emailInput.sendKeys(username));
        step("Enter password", () ->
                passInput.sendKeys(password));
        step("Click by enter button", () ->
                authButton.click());
        step("Check text", () ->
                checkAuth.shouldHave(text("VyplŇąte pros√≠m v√°Ň° e-mail")));
    }

    @Step("Check that footer has correct name {testFooter}")
    public void checkFooter(String testFooter) {
        checkFooter.shouldHave(text(testFooter));
    }
}