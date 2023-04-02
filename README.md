## Back-end for Cloud file storage

## Общая информация

Проект является составной частью [этого проекта](https://github.com/sezergemtsov/Cloud_file_storrage)  
с дополнительными пояснениями реализации.

Проект создан на основе Spring framework как REST api и соединен с готовой частью Front на оновании [этих требований](https://github.com/netology-code/jd-homeworks/blob/master/diploma/CloudServiceSpecification.yaml)

## Храние данных

Хранение данных реализовано на основе JPA репозитория, PostgreSQL, liquibase.  
Инициализация бд производится с помощью миграций, дополнительных настроек для запуска внутри контейнера не требуется.

По умолчанию в [application.properties](https://github.com/sezergemtsov/Cloud_file_storage_backend/blob/master/src/main/resources/application.properties#L3-L5)  установлено подключение к бд из Docker контейнера,  
поэтому для тестового запуска отдельно от основного проекта требуется переконфигурировать подключение к бд.

**Внимание:** максимальный размер передаваемых файлов ограничен 12MB, значение можно изменить в [application.properties](https://github.com/sezergemtsov/Cloud_file_storage_backend/blob/master/src/main/resources/application.properties#L13-L14)

## Авторизация

Аутентификация и авторизация в приложении настроены с помощью базовых механизмов имеющихся в Spring-Security.  
Так как в требовании фронта было наличие токена авторизации и его обработка, выбран механизм генерации JWT токена  
с применением библиотек "com.nimbusds.jose".  
Приложение генерирует ассиметричный ключ, и на его основе выдает уникальный JWT токен время жизни которого настроено на 1 час.
При потере токена или истечение срока жизни требуется повторная авторизация.
Настройки безопасности сведены в отдельный класс [SecurityConfig](https://github.com/sezergemtsov/Cloud_file_storage_backend/blob/master/src/main/java/ru/netology/diplomafinal/configs/SecurityConfig.java)  
Токен авторизации генерируется в [TokenService](https://github.com/sezergemtsov/Cloud_file_storage_backend/blob/master/src/main/java/ru/netology/diplomafinal/services/TokenService.java)  

Методы контроллера защищены требованием аутентификации и требуемой ролью пользователя.

В базе пользователей для теста приложения добавлен единственный пользователь со всеми правами доступа:  
username: admin  
password: admin

## CORS

В приложении дополнительно настроен CORS, поэтому доступ к контроллеру открыт только с порта "http://localhost:8081"  
Это следует учитывать при переконфигурации портов на хосте, во избежании проблем работы.
Добавить адреса запроса можно в [SecurityConfig](https://github.com/sezergemtsov/Cloud_file_storage_backend/blob/master/src/main/java/ru/netology/diplomafinal/configs/SecurityConfig.java#L111) 

## Дополнительная информация

В приложении добавлены тесты сервисов, они интеграционные, поэтому при пересборке **обязательно** наличие подключения к бд!  
