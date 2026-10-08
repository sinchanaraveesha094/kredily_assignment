# API testing

The API URL was not provided with the assignment, so the collection uses a placeholder host (`base_url` = `https://kredily.com`) and assumed end-user paths: `/enduser/login`, `/enduser/attendance`, `/enduser/employees`. These are **not verified**; if you capture the real endpoints (below), update `base_url` and the paths.

## Capture endpoints (10 minutes)
1. Install **Postman** and run **Postman -> Capture requests** (or `mitmproxy` / Charles).
2. Phone and laptop on the same Wi-Fi; set the phone Wi-Fi proxy to `<laptop-ip>:5555` (Postman default).
3. Install the proxy CA certificate on the phone. If the app uses certificate pinning, traffic will not decrypt - then record this as a limitation and test the APIs the web app (`kredily.com`) uses instead.
4. In the app: log in, open Attendance, open Directory. Copy the three captured requests into the collection (host -> `base_url`, path -> the `/enduser/...` paths, body field names -> as captured).

## Collection: 3 APIs, 11 requests
| API | Positive | Negative |
|---|---|---|
| Login | valid login, token saved to `{{token}}` | wrong password, empty username, malformed email, injection-style input |
| Attendance | fetch current week | no token (401/403), from > to date range (no 500) |
| Directory | list employees (count = 3) | no token, search with no match (no 500) |

Assertions: status code, response time < 3 s, JSON body, token present/absent, no 5xx on bad input.

## Run
Import both JSON files into Postman, select the **Kredily - QA** environment, then **Run collection**.
CLI + report: `npx newman run Kredily_API_Collection.postman_collection.json -e Kredily_QA.postman_environment.json -r cli,htmlextra`
