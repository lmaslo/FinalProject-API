# Дипломный проект по автоматизации тестирования API на тестовом сайте demoqa.com. 
<a target="_blank" href="https://demoqa.com/">Веб сайт demoqa.com</a>

## :memo: Содержание:

- [Реализованные проверки](#boom-Реализованные-проверки)
- [Технологии](#classical_building-Технологии)
- [Сборка в Jenkins](#man_cook-Jenkins-job)
- [Allure отчет](#bar_chart-Allure-отчет)

## :boom: Реализованные проверки

- ✓ Авторизация с корректными данными
- ✓ Авторизация с не существующим username 
- ✓ Авторизация с некорректным паролем
- ✓ Проверка генерации токена
- ✓ Проверка соответствие схеме запроса Список книг
- ✓ Проверка данных запроса о книге

## :classical_building: Технологии
<p align="center">
<img width="6%" title="Idea" src="images/logo/Idea.svg">
<img width="6%" title="Java" src="images/logo/Java.svg">
<img width="6%" title="Allure Report" src="images/logo/Allure.svg">
<img width="6%" title="Gradle" src="images/logo/Gradle.svg">
<img width="6%" title="JUnit5" src="images/logo/Junit5.svg">
<img width="6%" title="GitHub" src="images/logo/GitHub.svg">
<img width="6%" title="Jenkins" src="images/logo/Jenkins.svg">
<img width="6%" title="REST Assured" src="images/logo/logo-transparent.png">
</p>

## :man_cook: Jenkins job
<img src="images/logo/Jenkins.svg" width="25" height="25"  alt="Jenkins"/></a>  <a target="_blank" href="https://jenkins.autotests.cloud/job/011-maslogirl-FinalProject-API/">Jenkins job</a>
<p align="center">
<a href="https://jenkins.autotests.cloud/job/011-maslogirl-FinalProject-API/"><img src="images/screen/jenkins.jpg" alt="Jenkins"/></a>
</p>


###  Локальный запуск:
```
gradle clean test
```

## :bar_chart: Allure-отчет
<img src="images/logo/Allure.svg" width="25" height="25"  alt="Allure"/></a> Отчет в <a target="_blank" href="https://jenkins.autotests.cloud/job/011-maslogirl-FinalProject-API/2/allure/">Allure report</a>
<p align="center">
<a href="https://jenkins.autotests.cloud/job/011-maslogirl-FinalProject/3/allure/#graph"><img src="images/screen/allure.jpg" alt="Jenkins"/></a>
</p>

