# AI-assisted QA

**Tool:** Claude (Anthropic). **Activity:** reviewing and improving my manual test cases and bug reports, and generating the Appium + API test scaffolding.

## 1. Prompt used
> I have 20 test cases and 5 bug reports for the Kredily HRMS Android app (xlsx attached) plus screenshots. Review them: find inconsistencies between Expected/Actual/Status, vague results, missing links between bugs and test cases. Fix the sheets. Then write 5 Appium (Java) journeys and a Postman collection with positive and negative API cases, and an AI-usage note.

## 2. AI-generated output (summary)
- Review findings on my sheets (below), edited xlsx files with a Bug ID / Evidence column and a per-module summary.
- Appium + TestNG (Java) project (`automation/`), Postman collection (`api/`).

## 3. What I changed / validated
| Item | AI finding | My validation / decision |
|---|---|---|
| TC08 | Expected "valid shift", actual "12:30 - 12:30", status was Pass | Re-checked the Home screenshot and the Clock In sheet ("After shift - OT window"). Agreed: set to **Fail**, raised **BUG-06** |
| TC20 | Expected "untruncated", actual "truncated", status Pass | Re-checked Profile screenshot. Set to **Fail**, raised **BUG-07** |
| TC04 | Step said `abc@` but test data was `...yopmailcom` | Aligned steps and data to the missing-dot case I actually ran |
| TC06 | Actual said "toggle not tapped" but status Pass | Changed to **Not Executed** until I tap the eye icon |
| TC07/13/14/19 | Actual "Behaved as expected" was vague | Reworded to concrete results; confirmed on device |
| Appium locators | AI used visible text; app package unknown | Checked in Appium Inspector, set `APP_PACKAGE` for my device |
| API paths | AI cannot know private endpoints, so it left placeholders | Captured the real endpoints via Postman proxy before running |

**Rule I followed:** AI output is a draft. Every status change was checked against a screenshot, and nothing was reported as run until I ran it.
