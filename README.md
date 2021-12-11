http4s-example-app
==================
[![Build Status](https://github.com/Kevin-Lee/http4s-example-app/workflows/Build-All/badge.svg)](https://github.com/Kevin-Lee/http4s-example-app/actions?workflow=Build-All)
[![Build Status](https://github.com/Kevin-Lee/http4s-example-app/workflows/Launch%20Scala%20Steward/badge.svg)](https://github.com/Kevin-Lee/http4s-example-app/actions?workflow=Launch%20Scala%20Steward)
[![Build Status](https://github.com/Kevin-Lee/http4s-example-app/workflows/PR%20Labeler/badge.svg)](https://github.com/Kevin-Lee/http4s-example-app/actions?workflow=PR%20Labeler)

How to Run
----------
```bash
sbt run
```

Once you get log messages like this
```sbtshell
[info] running io.kevinlee.http4sexampleapp.MainServer
[ioapp-compute-0] INFO  o.h.b.c.n.NIO1SocketServerGroup - Service bound to address /0:0:0:0:0:0:0:0:8080
[ioapp-compute-0] INFO  o.h.b.s.BlazeServerBuilder -
  _   _   _        _ _
 | |_| |_| |_ _ __| | | ___
 | ' \  _|  _| '_ \_  _(_-<
 |_||_\__|\__| .__/ |_|/__/
             |_|
[ioapp-compute-0] INFO  o.h.b.s.BlazeServerBuilder - http4s v0.22.8 on blaze v0.15.2 started at http://[::]:8080/
```

In development, `reStart` and `reStop` are recommended to start and stop the app.

1. Access [http://localhost:8080/hello](http://localhost:8080/hello) in your web browser. It will give the following JSON.
    ```json
    {
      "message": "Hello, World"
    }
    ```

2. Access [http://localhost:8080/hello/YOUR_NAME](http://localhost:8080/hello/YOUR_NAME) in your web browser. It will give the following JSON.
    ```json
    {
      "message": "Hello, YOUR_NAME"
    }
    ``` 

3. Access [http://localhost:8080/hello/add](http://localhost:8080/hello/add) with two numbers in the path.
e.g.) [http://localhost:8080/hello/add/2/5](http://localhost:8080/hello/add/2/5)
    
    Result:
    ```json
    {
      "result": 7
    }
    ```
    
4. Access [http://localhost:8080/html/index.html](http://localhost:8080/html/index.html) to access an example static html page. It's handled by `StaticHtmlService`.
