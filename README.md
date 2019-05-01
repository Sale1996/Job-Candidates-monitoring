# Job Candidates monitoring

Backend : Java SpringBoot
Frontend : Angular6

Notes:

- Before running maven install, run testInput script in MySQL DBMS
- Swagger2 UI runs on : http://localhost:8090/swagger-ui.html
- For selecting multiple skills, either at creating new job candidate or searching candidates by given list of skills, hold CRTL.
- If you want to cancel search results, you need to unselect all selected skills.
- If you got getter and setter errors, that means you dont have installed lombok into your IDE.
First update your maven project, then go to : C:\Users\{YOUR-USERNAME}\.m2\repository\org\projectlombok\lombok\ and run "lombok-1.18.6" jar file, then follow installation steps.
(To be careful : On some PCs, this crushes eclipse/sts and they wont open until you remove lombok from some configuration. If you dont want to risk it, then remove
lombok anotations (@Data, @NoArgsConstructor, @AllArgsConstructor) and add manually constructors, getters and setters for all entities and DTOs).


