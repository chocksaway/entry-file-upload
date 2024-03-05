File processor application

```
git clone https://github.com/chocksaway/entry-file-upload.git
cd entry-file-upload
```

Make sure application.yaml:

fileValidation: true



To run:

```
$ mvn clean test
        
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.063 s -- in com.chocksaway.fileprocessor.controller.EntityFileControllerWithWiremockTest
[INFO] Running com.chocksaway.fileprocessor.entity.IpAPIResponseTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.005 s -- in com.chocksaway.fileprocessor.entity.IpAPIResponseTest
[INFO] Running com.chocksaway.fileprocessor.util.UriTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s -- in com.chocksaway.fileprocessor.util.UriTest
[INFO]
        [INFO] Results:
        [INFO]
        [INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
        [INFO]
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
        [INFO] Total time:  7.582 s
[INFO] Finished at: 2024-03-05T11:29:14Z
[INFO] ------------------------------------------------------------------------
```

