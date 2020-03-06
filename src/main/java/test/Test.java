package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test {

	public static void main(String[] args) {

		Test salTest = new Test();
		salTest.login();
		salTest.order();
		salTest.selectOption();
		salTest.purchase();

	}

	// WebDriver
	private WebDriver driver;
	private WebElement webElement;

	// Properties
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "src/main/resources/driver/chromedriver.exe";

	// 크롤링 할 URL
	private String login_url;
	private String product_url;
	private String product_urlTest1; // 옵션선택 있는 주소
	private String product_urlTest2; // 옵션선택 없는 주소

	public Test() {
		super();

		// System Property SetUp
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// Driver SetUp
		ChromeOptions options = new ChromeOptions();
		options.setCapability("ignoreProjectedModeSettings", true);
		driver = new ChromeDriver();

		login_url = "https://bling-market.com/html/dh_member/login";
		product_url = "https://bling-market.com/html/dh_product/prod_view/1903";
		product_urlTest1 = "https://bling-market.com/html/dh_product/prod_view/1810";
		product_urlTest2 = "https://bling-market.com/html/dh_product/prod_view/1640";
	}

	public void login() {

		try {
			// get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다
			driver.get(login_url);
//			System.out.println(driver.getPageSource());

//			//iframe으로 구성된 곳은 해당 프레임으로 전환	시킨다. -> 이거 주석 풀면 오류 
//			driver.switchTo().frame(driver.findElement(By.id("_hjRemoteVarsFrame")));

			// iframe 내부에서 id 필드 검색
			webElement = driver.findElement(By.name("userid"));
			String bling_id = "wbhblb";
			webElement.sendKeys(bling_id);

			// iframe 내부에서 pw 필드 탐색
			webElement = driver.findElement(By.name("passwd"));
			String bling_pw = "Q1w2e3r4";
			webElement.sendKeys(bling_pw);

			// 로그인 버튼 클릭
			webElement = driver.findElement(By.className("login_btn"));
			webElement.click();

//			Thread.sleep(20000);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void order() {
		// 로그인 후 상품페이지 이동
		driver.get(product_urlTest1);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		String stock = "";

		do {
			// 재고 여부 확인
			wait.until(ExpectedConditions.presenceOfElementLocated((By.className("num"))));
			webElement = driver.findElement(By.className("num"));
			stock = webElement.getAttribute("innerText");
			System.out.println("재고여부 : " + stock);

			if (!stock.equals("품절")) {
				break;
			}
			wait.until(ExpectedConditions.presenceOfElementLocated((By.className("num"))));
			driver.navigate().refresh();

		} while (stock.equals("품절"));
	}

	public void selectOption() {

		// num이 0원이면 옵션 선택, 0원이아니면 바로구매하기 버튼 누르기
		webElement = driver.findElement(By.className("num"));
		String stock = webElement.getAttribute("innerText");

		if (stock.equals("0원(0개)")) {
			System.out.println("puchase옵션선택 있는 곳");
			Select option = new Select(driver.findElement(By.id("option1")));

			option.selectByIndex(1);

		} else {

			webElement = driver.findElement(By.cssSelector("button.plain.btn02.orderbtn"));
			webElement.click();

		}

		WebDriverWait wait = new WebDriverWait(driver, 10);
		webElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.plain.btn02.orderbtn")));
		webElement = driver.findElement(By.cssSelector("button.plain.btn02.orderbtn"));
		webElement.click();

	}

	public void purchase() {
		// 결제하기 버튼 클릭

		/// WebDriverWait(로딩시간)
		WebDriverWait wait = new WebDriverWait(driver, 10);
		webElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("writeBtn")));

		webElement = driver.findElement(By.name("writeBtn"));
		webElement.click();

	}

}
