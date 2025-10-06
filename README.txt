Automation Testing – DemoQA_Form
==========================================

1. DESKRIPSI
------------------------------------------

CASE 01 – DemoQA (Katalon Studio)
Project ini merupakan pengujian otomatis terhadap halaman form di:
https://demoqa.com/automation-practice-form

Metode yang digunakan:
- Data Driven Testing (DDT) menggunakan file CSV
- Assertion per baris data (validasi success/error)
- Eksekusi melalui Test Suite untuk generate report (PDF / XML / HTML / CSV)

Total Test Case: 12 (2 valid, 10 invalid)


2. REQUIREMENT
------------------------------------------

CASE 01 – DemoQA (Katalon):
- Katalon Studio v10.3.2
- Java JRE/JDK 8+
- Plugin Basic Report (manual install via folder Plugins)
- Browser: Google Chrome / Edge / Firefox


3. CARA JALANKAN TEST
------------------------------------------

CASE 01 – DemoQA:
1. Buka Katalon Studio
2. Open Project → arahkan ke folder project ini
3. Pastikan struktur:
   - Test Case: TC_CASE01
   - Data Files: DataDemoQA_fixed.csv
   - Test Suite: TS_DemoQA_Form
4. Jalankan Test Suite:
   Klik kanan TS_DemoQA_Form > Run
5. Pilih browser (Chrome disarankan)
6. Tunggu eksekusi selesai
7. Report muncul di:
   /Reports/TS_DemoQA_Form/YYYYMMDD_HHmmss/
   File yang dihasilkan:
   - JUnit_Report.xml
   - YYYYMMDD_HHmmss_Report.html
   - YYYYMMDD_HHmmss_Report.csv
   - YYYYMMDD_HHmmss_Report.pdf


4. CATATAN
------------------------------------------

DemoQA Form:
- Field wajib dan opsional diuji (valid dan invalid)
- Assertion dengan JavaScript checkValidity() dan popup visibility
- DDT menggunakan looping CSV
- Report tersedia dalam csv, html, pdf, xml


5. STRUKTUR FOLDER FINAL
------------------------------------------

├── DemoQA_Form_Test/
│   ├── DataDemoQA_fixed.csv
│   └── DemoQA_form_test.zip
│   └── TC_CASE01.xlsx
│   ├── Report/
│       ├── 20251003_170511.csv
│       ├── 20251003_170511.html
│       ├── 20251003_170511.pdf
│       ├── JUnit_Report.xml
│
└── README.txt

------------------------------------------
