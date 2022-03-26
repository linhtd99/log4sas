# In case you want to modify sources and re-compile java classes:

## 1. Change the file path of deploy.jar in source/pom.xml:

```
cd source && mvn clean compile
```

The above command will use maven to compile .java into .class, which will be saved in `source/target/classes/com/example/log4shell/`

## 2. Change directory to the "target" folder (in the same folder with Dockerfile), which contains .war file:

```
cd ../target
jar -xvf log4shell-1.0-SNAPSHOT.war // To unzip/explode war file
```

Replace all compiled .class files needed in ./WEB-INF/classes/com/example/log4shell/ then,

```
jar -cvf log4shell-1.0-SNAPSHOT.war * // To re-create war file
```

Use this .war file for docker building.

```
cd ../
docker build . -t vuln-app
docker run --network host vuln-app
```

## WEB-VIEW:

/index.jsp (login)

/login-filtered.jsp (login with filtered)
