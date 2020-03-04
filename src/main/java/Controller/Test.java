package Controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Test {

	public static void main(String[] args) {

		Test salTest = new Test();
		salTest.login();
		salTest.order();

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
	private String product_urlTest;

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
		product_urlTest = "https://bling-market.com/html/dh_product/prod_view/1810";
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
		driver.get(product_url);

		String stock = "";
		
		do {
			// 재고 여부 확인
			webElement = driver.findElement(By.className("num"));
			stock = webElement.getAttribute("innerText");
			System.out.println("재고여부 : " + stock);

			if (!stock.equals("품절")) {
				break;
			}
			
			try {
				Thread.sleep(5000);
				
				driver.navigate().refresh();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (stock.equals("품절"));

		// 새로고침?

	}

}
