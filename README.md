# objectos :: way :: secure shell

ssh and scp for Java. A thin wrapper around the JSch library.

## Can Haz Code

### ssh

TBD

### scp

```java
// scp myfile.txt localhost:/tmp
File file = new File("myfile.txt");
Scp res = WaySSH.scp()
    .file(file)
    .toHost("localhost")
    .at("/tmp")
    .send();

boolean success = res.success();

if (!success) {
  List<Exception> exs = res.getExceptions();
  for (Exception ex : exs) {
    ex.printStackTrace(); // for the exception savvy
  }  
}
```

## Maven

way-secshell is at Maven central.

```xml
<dependency>
    <groupId>br.com.objectos</groupId>
    <artifactId>way-secshell</artifactId>
    <version>x.y.z</version>
</dependency>
```

## Versioning

We are working towards SemVer.

Till we reach version 2.0.0, things may change quite a bit...

# License

Copyright 2011 objectos, fábrica de software LTDA

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, 
software distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions 
and limitations under the License.