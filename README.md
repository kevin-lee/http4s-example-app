http4s-example-app
==================
http4s Example App

How to Run
----------
```bash
sbt run
```

Once you get log messages like this
```sbtshell
[run-main-0] INFO  o.h.b.c.n.NIO1SocketServerGroup - Service bound to address /0:0:0:0:0:0:0:0:8080
[run-main-0] INFO  o.h.s.b.BlazeBuilder -   _   _   _        _ _
[run-main-0] INFO  o.h.s.b.BlazeBuilder -  | |_| |_| |_ _ __| | | ___
[run-main-0] INFO  o.h.s.b.BlazeBuilder -  | ' \  _|  _| '_ \_  _(_-<
[run-main-0] INFO  o.h.s.b.BlazeBuilder -  |_||_\__|\__| .__/ |_|/__/
[run-main-0] INFO  o.h.s.b.BlazeBuilder -              |_|
[run-main-0] INFO  o.h.s.b.BlazeBuilder - http4s v0.18.9 on blaze v0.12.13 started at http://[0:0:0:0:0:0:0:0]:8080/
```

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
