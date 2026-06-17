# Роадмап рефакторинга по файлам

Это упорядоченный список файлов, которые следует исправлять в соответствии с замечаниями в комментариях. Рекомендую двигаться последовательно.

Файлы, не указанные в списке, можно исправлять в любом порядке.

### Шаг 1: Конфигурация сборки

- `pom.xml`

### Шаг 2: Сущности (JPA Entities)

- `/entity/BaseEntity.java`
- `/entity/Player.java`
- `/entity/MatchEntity.java`

### Шаг 3: Слой доступа к данным (DAO)

- `/dao/Dao.java`
- `/dao/MatchDao.java`
- `/dao/hibernateImpl/HibernatePlayerDaoImpl.java`
- `/dao/hibernateImpl/HibernateMatchDaoImpl.java`

### Шаг 4: Доменные модели

- `/model/Point.java`
- `/model/Game.java`
- `/model/TieBreak.java`
- `/model/TennisSet.java`
- `/model/Match.java`

### Шаг 5: DTO

- `/dto/`

### Шаг 6: Валидаторы и Сервисный слой

- `/validator/MatchValidator.java`
- `/service/PlayerService.java`
- `/service/FinishedMatchesPersistenceService.java`
- `/service/OngoingMatchesService.java`

### Шаг 7: Утилиты, конфигурация

- `/utils/JspHelper.java`
- `/utils/TransactionManager.java`
- `/listener/AppListener.java`

### Шаг 8: Веб-слой

- `/servlet/BaseServlet.java`
- `/servlet/ErrorPage.java`
- `/servlet/NewMatchServlet.java`
- `/servlet/MatchScoreServlet.java`
- `/servlet/MatchesServlet.java`
- `/filter/ExceptionHandlerFilter.java`

### Шаг 9: Тесты и JSP

- `src/test/java/TableTennis/model/GameTest.java`
- `src/test/java/TableTennis/model/TennisSetTest.java`
- `src/test/java/TableTennis/model/MatchTest.java`
- `src/main/webapp/WEB-INF/matches.jsp`
