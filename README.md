# Cursor current Task:

```agsl
1. Створити spring boot проект “Shops”. Створити модель магазину, в класі мають
бути такі поля :айді, місто та вулиця де знаходиться магазин, назва магазину і
кількість співробітників, та поле чи є у магазина сайт. Створити контролер в
якому будутьприсутні такі операції :
1)створити новий магазин,
2)видалити магазин ( по айдішці),
3)отримати список всіх магазинів,
4)отримати магазин по айдішці
5)змінти поля магазину( по айдішці) – можна міняти всі поля крім айдішки.
Перевірити функціонал через постман, ідею чи будь яку іншу тулзовину.
Впевнетись що CRUD операції працюють. Замість бази данних використати будь
яку колекцію, бажано всі операції зробити в окремому сервісі який буде
присутній в контролері.

2.Опційно конкретизувати статус коди, наприклад після створення магазину
повертати не 200 код а 201 Created. Якщо запитується шоп якого немає
повернути 404, і додати осмислене повідомленя про рест - помилку.
```


## For project need install jdk 17.0.1, and gradle 7.6, docker and docker-compose:
1. [Install jdk use jvms](https://github.com/ystyle/jvms)
2. [Install gradle](https://gradle.org/install/)
3. [Install docker](https://docs.docker.com/engine/install/)
4. [Install docker-compose](https://docs.docker.com/compose/install/)


## For run project need run command:

```bash
# Run docker-compose
docker-compose up -d
# do gradle build
gradle build
# do gradle bootRun
gradle bootRun # go to http://localhost:5000 for check app shop your can use the api-shop.http or other tool for test api
# do run test
gradle test # important for run integration test need docker
```

