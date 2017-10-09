# directeur
Company employees manager app developed specially for Braintri recrutation purposes

# Startup
To run the application, all you need to to is to execute this command:
```
mvn clean package && mvn spring-boot:run
```

# Getting to know the API
If you want to take a quick look at how the API works, after running the app go into
```
http://localhost:8080/swagger-ui.html#/employees-endpoint
```
and browse through all the methods- it's all described there.

# Database configuration
I've performed the configuration on a clean postgres, so you might find that useful. 
To be able to use the database as default postgres user (otherwise you can change it in application.properties), do this:

1. Login as postgres superuser
``` 
sudo su
su - postgres 
```
  
2. Enter local postgres instance
```
psql
```
  
3. Set password for postgres user
```
\password
Enter new password:
Enter it again:
```
  
4. Disconnect from postgres
```
\q
```
  
5. Go back to root and editing postgres config
```
vim /etc/postgresql/9.4/main/pg_hba.conf
```
  
6. Change line 
```
local all all peer
 ```
(the line should be the first uncommented one)
to
```
local all all md5
```
  
7. Restart database to load new configuration:
```
/etc/init.d/postgresql restart
```
Then you're good to go- the database is up and running :D
