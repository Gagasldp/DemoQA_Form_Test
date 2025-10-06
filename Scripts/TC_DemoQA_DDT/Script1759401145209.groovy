import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.model.FailureHandling


// Load CSV dari Data Files
def data = findTestData("Data Files/DataDemoQA")

for (int row = 1; row <= data.getRowNumbers(); row++) {
    // ambil semua field dari CSV
    String firstName = data.getValue("FirstName", row)
    String lastName  = data.getValue("LastName", row)
    String email     = data.getValue("Email", row)
    String mobile    = data.getValue("Mobile", row)
    String gender    = data.getValue("Gender", row)
    String dob       = data.getValue("DOB", row)   // format: dd/MM/yyyy
    String subject   = data.getValue("Subject", row)
    String hobbies   = data.getValue("Hobbies", row)
    String picture   = data.getValue("Picture", row)
    String address   = data.getValue("Address", row)
    String state     = data.getValue("State", row)
    String city      = data.getValue("City", row)
	String expected      = data.getValue("Expected", row)

    WebUI.comment("▶ Iterasi ${row}: ${firstName} ${lastName} | ${email} | ${mobile}")

    // buka form
    WebUI.openBrowser('')
    WebUI.navigateToUrl('https://demoqa.com/automation-practice-form')
    WebUI.maximizeWindow()

    // isi nama
    WebUI.setText(findTestObject('Page_DEMOQA/input_Name_firstName'), firstName)
    WebUI.setText(findTestObject('Page_DEMOQA/input_Name_lastName'), lastName)

    // email
    WebUI.setText(findTestObject('Page_DEMOQA/input_Email_userEmail'), email)

	if (gender != null && gender.trim().length() > 0) {
		WebUI.comment("Klik gender: ${gender}")
		TestObject genderOption = new TestObject()
		genderOption.addProperty("xpath", ConditionType.EQUALS, "//label[normalize-space(text())='${gender}']")
		WebUI.click(genderOption)
	} else {
		WebUI.comment("⚠ Gender kosong → tidak dipilih")
	}
	
    // mobile
    WebUI.setText(findTestObject('Page_DEMOQA/input_(10 Digits)_userNumber'), "0"+ mobile)

    // DOB pakai dynamic TestObject
    if (dob && dob.trim() != "") {
        def parts = dob.split("/") // format CSV: dd/MM/yyyy
        def day = parts[0]
        def month = parts[1].toInteger() - 1 // react-datepicker: Jan=0
        def year = parts[2]

        WebUI.click(findTestObject('Page_DEMOQA/input_Date of Birth_dateOfBirthInput'))

        TestObject yearSelect = new TestObject()
        yearSelect.addProperty("xpath", ConditionType.EQUALS,
            "//select[contains(@class,'react-datepicker__year-select')]")

        TestObject monthSelect = new TestObject()
        monthSelect.addProperty("xpath", ConditionType.EQUALS,
            "//select[contains(@class,'react-datepicker__month-select')]")

        WebUI.selectOptionByValue(yearSelect, year, true)
        WebUI.selectOptionByIndex(monthSelect, month)

        TestObject dayObject = new TestObject()
        dayObject.addProperty("xpath", ConditionType.EQUALS,
            "//div[contains(@class,'react-datepicker__day') and not(contains(@class,'outside-month')) and text()='${day}']")
        WebUI.click(dayObject)
    }

    // Subject
    if (subject && subject.trim() != "") {
        WebUI.setText(findTestObject('Page_DEMOQA/input_Subjects_subjectsInput'), subject)
        WebUI.sendKeys(findTestObject('Page_DEMOQA/input_Subjects_subjectsInput'), Keys.chord(Keys.ENTER))
    }

    // Hobbies
    if (hobbies && hobbies.trim() != "") {
        TestObject hobbyOption = new TestObject()
        hobbyOption.addProperty("xpath", ConditionType.EQUALS, "//label[text()='${hobbies}']")
        WebUI.click(hobbyOption)
    }

    // Upload picture
    // Upload Picture (ambil path dari CSV)
	if (picture && picture.trim() != "") {
    WebUI.comment("📂 Upload picture: ${picture}")
    WebUI.uploadFile(findTestObject('Page_DEMOQA/input_Select picture_uploadPicture'), picture)
	}

    // Address
    WebUI.setText(findTestObject('Page_DEMOQA/textarea_Current Address_currentAddress'), address)

    // State
    if (state && state.trim() != "") {
        WebUI.click(findTestObject('Page_DEMOQA/div_Select State_css-1gtu0rj-indicatorContainer'))
        TestObject stateOption = new TestObject()
        stateOption.addProperty("xpath", ConditionType.EQUALS,
            "//div[contains(@id,'react-select-3-option') and text()='${state}']")
        WebUI.click(stateOption)
    }

    // City
    if (city && city.trim() != "") {
        WebUI.click(findTestObject('Page_DEMOQA/svg_Select City_css-19bqh2r'))
        TestObject cityOption = new TestObject()
        cityOption.addProperty("xpath", ConditionType.EQUALS,
            "//div[contains(@id,'react-select-4-option') and text()='${city}']")
        WebUI.click(cityOption)
    }

    // Submit
    TestObject submitBtn = new TestObject()
	submitBtn.addProperty("xpath", ConditionType.EQUALS, "//button[@id='submit']")
	WebUI.click(submitBtn)

	if (expected == "required" && (gender == null || gender.trim() == "")) {
		// cek apakah ada radio button terpilih
		def isGenderSelected = WebUI.executeJavaScript(
			"return document.querySelectorAll('input[name=\"gender\"]:checked').length > 0;", [])
	
		WebUI.comment("Gender selected? ${isGenderSelected}")
		WebUI.verifyEqual(isGenderSelected, false, FailureHandling.CONTINUE_ON_FAILURE)
	
	} else if (expected == "valid") {
		// cek popup submit muncul
		TestObject popup = new TestObject()
		popup.addProperty("xpath", ConditionType.EQUALS, "//div[@id='example-modal-sizes-title-lg']")
		WebUI.verifyElementVisible(popup, FailureHandling.CONTINUE_ON_FAILURE)
	} else if (expected == "required") {
		def isValid = WebUI.executeJavaScript(
        "return document.getElementById('lastName').checkValidity();", [])
		assert isValid == false
	} else if (expected == "email_invalid") {
		def isValid = WebUI.executeJavaScript(
		"return document.getElementById('userEmail').checkValidity();", [])
		assert isValid == false

	} else if (expected == "mobile_invalid") {
		def isValid = WebUI.executeJavaScript(
        "return document.getElementById('userNumber').checkValidity();", [])
		assert isValid == false

	}

    WebUI.closeBrowser()
}