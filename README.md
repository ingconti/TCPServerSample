# TCPServerSample
trivial server TCP sample

USAGE:

 to test against console:
/usr/bin/nc 127.0.0.1 1234

and type in console: server will receive.

it will NOT block socket (for now..) when timeout.


to test against file, pls use file "cmd.JSON" in root level and type in console:

cat cmd.json |/usr/bin/nc 127.0.0.1 1234

nb: sample is very simple by design. After killing nc or shell, it exits writing: "Server done!"
