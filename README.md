# ConTree

>  `contree` 发音与`country`,意为：乡间美，乡间树多。

![maven中央仓库](https://img.shields.io/maven-central/v/org.openingo.kits/contree.svg)
![LTS版本](https://img.shields.io/nexus/s/org.openingo.kits/contree-parent?label=LTS%E7%89%88%E6%9C%AC&server=https%3A%2F%2Foss.sonatype.org&style=plastic)
### 特性

- 树节点添加、修改；
- 树节点级联删除、含有子节点的校验删除（有子节点时不可删除）；
- 树数据获取，指定rootNodeId，模糊nodeName，指定获取数据模式fetchType：仅第一级子节点或全部；
- 变动通知，便于其他业务处理其他事务。

### [接口文档](apis.md)

### 使用方式

- 添加依赖

```xml
<dependency>
    <groupId>org.openingo.kits</groupId>
    <artifactId>contree</artifactId>
    <version>${contree.version}</version>
</dependency>
```

- 在启动类上加入`@EnableTree`

```java
/**
 * App
 *
 * @author Qicz
 */
@EnableExtension
@EnableTree
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplicationX.run(App.class, args);
    }
}
```

- 监听变动`implements IConTreeObserver`即可

```java
/**
 * ConTreeObserverComponent
 *
 * @author Qicz
 */
@Component
@Slf4j
public class ConTreeObserverComponent implements IConTreeObserver {

    /**
     * 删除处理响应
     * <note>
     * 1. 这处理与树删除在同一个事务中；
     * 2. 业务端收到onTreeNodeDelete消息后，处理完成业务逻辑；
     * 3. 以上都无异常，则事务正常提交。
     * </note>
     *
     * @param treeCode 树编码
     * @param nodes    删除的node信息
     */
    @Override
    public void onTreeNodeDelete(String treeCode, List<ConTreeNode> nodes) {
        log.info("😃 treeCode = {}, nodes = {}", treeCode, nodes);
    }
}
```

