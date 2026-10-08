# Kredily HRMS - Intern QA Engineer Assignment

| Deliverable | Location |
|---|---|
| Test cases (20) + summary | `test-cases/Kredily_Test_Cases_Clean.xlsx` |
| Bug reports (7) + evidence | `bug-reports/Kredily_Bug_Reports.xlsx` |
| Mobile automation (Appium, Java, TestNG) | `automation/` |
| API tests (Postman) | `api/` |
| AI-assisted QA | `docs/AI_ASSISTED_QA.md` |
| Final QA summary | `docs/QA_SUMMARY.md` |
| Screenshots | `screenshots/` |

## Mobile automation (Java, Appium, TestNG) - setup
1. Install: JDK 17+, Maven, Node.js, Appium 2 (`npm i -g appium`), driver (`appium driver install uiautomator2`), Android SDK platform-tools.
2. Connect the phone (USB debugging on) - check `adb devices`.
3. Put the APK in `automation/` as `kredily-mobile-v2.apk` (or pass `-Dapk.path=...`).

## Run
```bash
appium                         # terminal 1
cd automation
mvn clean test                 # terminal 2
```
- Report: `automation/target/surefire-reports/index.html` and `emailable-report.html`; failure screenshots in `automation/reports/screens/`.
- Use an already-installed app: `mvn test -Dapp.package=<pkg>` (find it with `adb shell dumpsys window | grep mCurrentFocus`).
- Credentials default to the assignment account; override with `-Dkredily.user=... -Dkredily.pass=...`.
- BUG-01 probe (fails until fixed): `mvn test -Dsurefire.suiteXmlFiles=testng-known-bug.xml`.

**Journeys:** J1 valid login, J2 invalid login (wrong password, empty field), J3 dashboard validation, J4 attendance team view, J5 directory + profile.
If a locator does not match, open Appium Inspector, copy the element's id/text and update the page classes in `src/test/java/com/kredily/pages`.

## API tests
See `api/README.md`.
