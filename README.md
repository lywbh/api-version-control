# ApiVersionControl -- SpringWeb接口版本控制工具

##### 通过header中的版本字段，控制访问的接口，用于版本发布时向下兼容

## Installation
```xml
<dependency>
    <groupId>com.lyw</groupId>
    <artifactId>api-version-control</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Use
```java
@RestController
public class Controller {

    @RequestMapping("/version")
    @VersionedApi(name = "version", from = "2.0.1", to = "2.1.4")
    public String v1() {
        return "v1";
    }

    @RequestMapping("/version")
    @VersionedApi(name = "version", from = "2.1.5", to = "2.2.0")
    public String v2() {
        return "v2";
    }

}
```
##### name是header中的版本key名，必须指定。
##### 版本字符串可以是*.*.*...形式，每一段可以是多个数字，如2.54.3.156



###### 搞Spring就是逊啦