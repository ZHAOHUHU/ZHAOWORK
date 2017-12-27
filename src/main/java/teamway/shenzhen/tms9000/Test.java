package teamway.shenzhen.tms9000;

import org.apache.log4j.Logger;

public class Test {
    public static Logger log = Logger.getLogger(Test.class);

    public static void main(String[] args) {

        log.warn("DailyRollingFileAppender生成的文件是不带时间戳的，必须在某个时间点后，才对原来文件加上时间戳进行重命名。 \n" +
                "比如时间戳精确到小时，格式为.yyyy-MM-dd-HH，当前时间为2011-05-05的5点，那么日志为 \n" +
                "log \n" +
                "时间变为6点之后（并且6点有日志访问），日志为 \n" +
                "log \n" +
                "log.2011-05-05-05 \n" +
                "\n" +
                "因为log4j是事件触发的，如果某段时间没有日志访问，即使时间点到了，也不会加上时间戳进行重命名。比如两天后才有日志访问，这时才产生log.2011-05-05-05这个文件。这样会造成日志统计中，统计不到这个文件。 \n" +
                "\n" +
                "解决方法是，生成日志文件的时候就已经加上时间戳。比如5点时候的日志，就已经是log.2011-05-05-05。原log4j没有实现这个功能，需要自己继承FileAppender来实现一个Appender。我看了一下源码，可以参考DailyRollingFileAppender，修改一下它的构造函数和rollOver。自己没有去实验，不知道能不能成功。 \n" +
                "\n" +
                "apache-log4j-extras是log4j的扩展包，其中TimeBasedRollingPolicy可以实现这个需求。 \n" +
                "需要的包： \n" +
                "log4j-1.2.15.jar \n" +
                "apache-log4j-extras-1.0.jar \n" +
                "注意：log4j必须是1.2.15以上，14不行 \n" +
                "\n" +
                "log4j配置文件不能使用properties，必须使用xml。配置可参考： \n" +
                "Xml代码  收藏代码\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n" +
                "<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">  \n" +
                "<log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/'>  \n" +
                "     <!-- appender -->  \n" +
                "     <!-- STDOUT -->  \n" +
                "     <appender name=\"STDOUT\" class=\"org.apache.log4j.ConsoleAppender\">  \n" +
                "          <layout class=\"org.apache.log4j.PatternLayout\">  \n" +
                "               <param name=\"ConversionPattern\" value=\"%d %p [%c] - %m%n\" />  \n" +
                "          </layout>  \n" +
                "     </appender>  \n" +
                "  \n" +
                "     <!-- FILE -->  \n" +
                "     <appender name=\"FILE\"  \n" +
                "          class=\"org.apache.log4j.rolling.RollingFileAppender\">  \n" +
                "          <rollingPolicy  \n" +
                "               class=\"org.apache.log4j.rolling.TimeBasedRollingPolicy\">  \n" +
                "               <param name=\"FileNamePattern\"  \n" +
                "                    value=\"d:/logs/file.%d{yyyy-MM-dd-HH}\" />  \n" +
                "          </rollingPolicy>  \n" +
                "          <layout class=\"org.apache.log4j.PatternLayout\">  \n" +
                "               <param name=\"ConversionPattern\"  \n" +
                "                    value=\"%m%n\" />  \n" +
                "          </layout>  \n" +
                "     </appender>  \n" +
                "      \n" +
                "     <logger name=\"view\">  \n" +
                "          <level value=\"info\" />  \n" +
                "          <appender-ref ref=\"FILE\" />  \n" +
                "     </logger>  \n" +
                "  \n" +
                "     <root>  \n" +
                "          <level value=\"info\" />  \n" +
                "          <appender-ref ref=\"STDOUT\" />  \n" +
                "     </root>  \n" +
                "</log4j:configuration>  \n" +
                "\n" +
                "注意：org.apache.log4j.rolling.RollingFileAppender是rolling包下的，而不是原来的org.apache.log4j.RollingFileAppender \n" +
                "\n" +
                "测试用例： \n" +
                "Java代码  收藏代码\n" +
                "public static void main(String[] args) {  \n" +
                "           Logger logger = Logger.getLogger(\"view\");  \n" +
                "           logger.info(\"test\");  \n" +
                "}  \n" +
                "\n" +
                "\n" +
                "TimeBasedRollingPolicy的使用可参考： \n" +
                "http://logging.apache.org/log4j/companions/extras/apidocs/org/apache/log4j/rolling/TimeBasedRollingPolicy.html\n" +
                "分享到：    \n" +
                "cocos2d场景的层次图 | redis基本命令\n" +
                "2011-05-05 17:51浏览 16480评论(4)论坛回复 / 浏览 (4 / 2669)分类:编程语言查看更多\n" +
                "相关资源推荐\n" +
                "使用log4j扩展包的RollingFileAppender生成带时间戳的日志文件 使用log4j生成动态日志文件-文件名根据时间自动生成 Log4j2中RollingFile的文件滚动更新机制 一、什么是RollingFile RollingFileAppender是Log4j2中的一种能够实现日志文件滚动更新(rollover)的 Tomcat下使用Log4j 接管 catalina.out 日志文件生成方式 Tomcat下使用Log4j 接管 catalina.out 日志文件生成方式 tomcat下用Log4j 按文件大小，生成catalina.out日志文件 log4j 按时间、大小产生新的日志文件 Log4j输出包/类的日志文件 Log4j 4种日志文件生成方式 log4j 根据日期来生成日志文件 Tomcat配置log4j生成日志文件 Java中获取Log4j日志文件，Mybatis 的ScriptRunner执行带pl/sql的 代码块脚本 log4j指定包或类打到单独的日志文件的配置 Log4j 为单独的类生成单独的日志文件 Log4j每天、每小时、每分钟定时生成日志文件 log4j配置每天生成一个日志文件 log4j配置每天生成一个日志文件 使用log4j的DailyRollingFileAppender时只有一个日志文件 Log4j 日志文件使用 使用log4j记录日志文件\n" +
                "参考知识库\n" +
                "\n" +
                "Android知识库\n" +
                "39022  关注 | 3162  收录\n" +
                "\n" +
                "React知识库\n" +
                "3952  关注 | 393  收录\n" +
                "\n" +
                "人工智能基础知识库\n" +
                "18269  关注 | 212  收录\n" +
                "\n" +
                "Java 知识库\n" +
                "38183  关注 | 3748  收录\n" +
                "评论\n" +
                "4 楼 flyzsd 2011-05-09  \n" +
                "用 SLF4J + LOGBACK\n" +
                "3 楼 flysnowxf 2011-05-06  \n" +
                "logback实现以上需求的配置： \n" +
                "Xml代码  收藏代码\n" +
                "<timestamp key=\"byHour\" datePattern=\"yyyy-MM-dd-HH\"/>   \n" +
                "<appender name=\"FILE\"  \n" +
                "        class=\"ch.qos.logback.core.FileAppender\">  \n" +
                "        <File>d:/logs/file.${byHour}</File>  \n" +
                "        <layout class=\"ch.qos.logback.classic.PatternLayout\">  \n" +
                "            <Pattern>  \n" +
                "                %msg%n  \n" +
                "            </Pattern>  \n" +
                "        </layout>  \n" +
                "    </appender>  \n" +
                "  \n" +
                "    <logger name=\"view\">  \n" +
                "        <level value=\"info\" />  \n" +
                "        <appender-ref ref=\"FILE\" />  \n" +
                "    </logger>  \n" +
                "\n" +
                "不需要使用rolling包，直接产生带时间戳的日志文件。\n" +
                "2 楼 flysnowxf 2011-05-06  \n" +
                "simplechinese 写道\n" +
                "logback飘过\n" +
                "\n" +
                "看了下logback，它把这个功能集成进去了。那哥们老折腾了，弄了slf4j，又新开一个logback。\n" +
                "1 楼 simplechinese 2011-05-05  \n" +
                "logback飘过\n" +
                "发表评论\n" +
                "  您还没有登录,请您登录后再发表评论DailyRollingFileAppender生成的文件是不带时间戳的，必须在某个时间点后，才对原来文件加上时间戳进行重命名。 \n" +
                "比如时间戳精确到小时，格式为.yyyy-MM-dd-HH，当前时间为2011-05-05的5点，那么日志为 \n" +
                "log \n" +
                "时间变为6点之后（并且6点有日志访问），日志为 \n" +
                "log \n" +
                "log.2011-05-05-05 \n" +
                "\n" +
                "因为log4j是事件触发的，如果某段时间没有日志访问，即使时间点到了，也不会加上时间戳进行重命名。比如两天后才有日志访问，这时才产生log.2011-05-05-05这个文件。这样会造成日志统计中，统计不到这个文件。 \n" +
                "\n" +
                "解决方法是，生成日志文件的时候就已经加上时间戳。比如5点时候的日志，就已经是log.2011-05-05-05。原log4j没有实现这个功能，需要自己继承FileAppender来实现一个Appender。我看了一下源码，可以参考DailyRollingFileAppender，修改一下它的构造函数和rollOver。自己没有去实验，不知道能不能成功。 \n" +
                "\n" +
                "apache-log4j-extras是log4j的扩展包，其中TimeBasedRollingPolicy可以实现这个需求。 \n" +
                "需要的包： \n" +
                "log4j-1.2.15.jar \n" +
                "apache-log4j-extras-1.0.jar \n" +
                "注意：log4j必须是1.2.15以上，14不行 \n" +
                "\n" +
                "log4j配置文件不能使用properties，必须使用xml。配置可参考： \n" +
                "Xml代码  收藏代码\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n" +
                "<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">  \n" +
                "<log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/'>  \n" +
                "     <!-- appender -->  \n" +
                "     <!-- STDOUT -->  \n" +
                "     <appender name=\"STDOUT\" class=\"org.apache.log4j.ConsoleAppender\">  \n" +
                "          <layout class=\"org.apache.log4j.PatternLayout\">  \n" +
                "               <param name=\"ConversionPattern\" value=\"%d %p [%c] - %m%n\" />  \n" +
                "          </layout>  \n" +
                "     </appender>  \n" +
                "  \n" +
                "     <!-- FILE -->  \n" +
                "     <appender name=\"FILE\"  \n" +
                "          class=\"org.apache.log4j.rolling.RollingFileAppender\">  \n" +
                "          <rollingPolicy  \n" +
                "               class=\"org.apache.log4j.rolling.TimeBasedRollingPolicy\">  \n" +
                "               <param name=\"FileNamePattern\"  \n" +
                "                    value=\"d:/logs/file.%d{yyyy-MM-dd-HH}\" />  \n" +
                "          </rollingPolicy>  \n" +
                "          <layout class=\"org.apache.log4j.PatternLayout\">  \n" +
                "               <param name=\"ConversionPattern\"  \n" +
                "                    value=\"%m%n\" />  \n" +
                "          </layout>  \n" +
                "     </appender>  \n" +
                "      \n" +
                "     <logger name=\"view\">  \n" +
                "          <level value=\"info\" />  \n" +
                "          <appender-ref ref=\"FILE\" />  \n" +
                "     </logger>  \n" +
                "  \n" +
                "     <root>  \n" +
                "          <level value=\"info\" />  \n" +
                "          <appender-ref ref=\"STDOUT\" />  \n" +
                "     </root>  \n" +
                "</log4j:configuration>  \n" +
                "\n" +
                "注意：org.apache.log4j.rolling.RollingFileAppender是rolling包下的，而不是原来的org.apache.log4j.RollingFileAppender \n" +
                "\n" +
                "测试用例： \n" +
                "Java代码  收藏代码\n" +
                "public static void main(String[] args) {  \n" +
                "           Logger logger = Logger.getLogger(\"view\");  \n" +
                "           logger.info(\"test\");  \n" +
                "}  \n" +
                "\n" +
                "\n" +
                "TimeBasedRollingPolicy的使用可参考： \n" +
                "http://logging.apache.org/log4j/companions/extras/apidocs/org/apache/log4j/rolling/TimeBasedRollingPolicy.html\n" +
                "分享到：    \n" +
                "cocos2d场景的层次图 | redis基本命令\n" +
                "2011-05-05 17:51浏览 16480评论(4)论坛回复 / 浏览 (4 / 2669)分类:编程语言查看更多\n" +
                "相关资源推荐\n" +
                "使用log4j扩展包的RollingFileAppender生成带时间戳的日志文件 使用log4j生成动态日志文件-文件名根据时间自动生成 Log4j2中RollingFile的文件滚动更新机制 一、什么是RollingFile RollingFileAppender是Log4j2中的一种能够实现日志文件滚动更新(rollover)的 Tomcat下使用Log4j 接管 catalina.out 日志文件生成方式 Tomcat下使用Log4j 接管 catalina.out 日志文件生成方式 tomcat下用Log4j 按文件大小，生成catalina.out日志文件 log4j 按时间、大小产生新的日志文件 Log4j输出包/类的日志文件 Log4j 4种日志文件生成方式 log4j 根据日期来生成日志文件 Tomcat配置log4j生成日志文件 Java中获取Log4j日志文件，Mybatis 的ScriptRunner执行带pl/sql的 代码块脚本 log4j指定包或类打到单独的日志文件的配置 Log4j 为单独的类生成单独的日志文件 Log4j每天、每小时、每分钟定时生成日志文件 log4j配置每天生成一个日志文件 log4j配置每天生成一个日志文件 使用log4j的DailyRollingFileAppender时只有一个日志文件 Log4j 日志文件使用 使用log4j记录日志文件\n" +
                "参考知识库\n" +
                "\n" +
                "Android知识库\n" +
                "39022  关注 | 3162  收录\n" +
                "\n" +
                "React知识库\n" +
                "3952  关注 | 393  收录\n" +
                "\n" +
                "人工智能基础知识库\n" +
                "18269  关注 | 212  收录\n" +
                "\n" +
                "Java 知识库\n" +
                "38183  关注 | 3748  收录\n" +
                "评论\n" +
                "4 楼 flyzsd 2011-05-09  \n" +
                "用 SLF4J + LOGBACK\n" +
                "3 楼 flysnowxf 2011-05-06  \n" +
                "logback实现以上需求的配置： \n" +
                "Xml代码  收藏代码\n" +
                "<timestamp key=\"byHour\" datePattern=\"yyyy-MM-dd-HH\"/>   \n" +
                "<appender name=\"FILE\"  \n" +
                "        class=\"ch.qos.logback.core.FileAppender\">  \n" +
                "        <File>d:/logs/file.${byHour}</File>  \n" +
                "        <layout class=\"ch.qos.logback.classic.PatternLayout\">  \n" +
                "            <Pattern>  \n" +
                "                %msg%n  \n" +
                "            </Pattern>  \n" +
                "        </layout>  \n" +
                "    </appender>  \n" +
                "  \n" +
                "    <logger name=\"view\">  \n" +
                "        <level value=\"info\" />  \n" +
                "        <appender-ref ref=\"FILE\" />  \n" +
                "    </logger>  \n" +
                "\n" +
                "不需要使用rolling包，直接产生带时间戳的日志文件。\n" +
                "2 楼 flysnowxf 2011-05-06  \n" +
                "simplechinese 写道\n" +
                "logback飘过\n" +
                "\n" +
                "看了下logback，它把这个功能集成进去了。那哥们老折腾了，弄了slf4j，又新开一个logback。\n" +
                "1 楼 simplechinese 2011-05-05  \n" +
                "logback飘过\n" +
                "发表评论\n" +
                "  您还没有登录,请您登录后再发表评论好饿啊DailyRollingFileAppender生成的文件是不带时间戳的，必须在某个时间点后，才对原来文件加上时间戳进行重命名。 \n" +
                "比如时间戳精确到小时，格式为.yyyy-MM-dd-HH，当前时间为2011-05-05的5点，那么日志为 \n" +
                "log \n" +
                "时间变为6点之后（并且6点有日志访问），日志为 \n" +
                "log \n" +
                "log.2011-05-05-05 \n" +
                "\n" +
                "因为log4j是事件触发的，如果某段时间没有日志访问，即使时间点到了，也不会加上时间戳进行重命名。比如两天后才有日志访问，这时才产生log.2011-05-05-05这个文件。这样会造成日志统计中，统计不到这个文件。 \n" +
                "\n" +
                "解决方法是，生成日志文件的时候就已经加上时间戳。比如5点时候的日志，就已经是log.2011-05-05-05。原log4j没有实现这个功能，需要自己继承FileAppender来实现一个Appender。我看了一下源码，可以参考DailyRollingFileAppender，修改一下它的构造函数和rollOver。自己没有去实验，不知道能不能成功。 \n" +
                "\n" +
                "apache-log4j-extras是log4j的扩展包，其中TimeBasedRollingPolicy可以实现这个需求。 \n" +
                "需要的包： \n" +
                "log4j-1.2.15.jar \n" +
                "apache-log4j-extras-1.0.jar \n" +
                "注意：log4j必须是1.2.15以上，14不行 \n" +
                "\n" +
                "log4j配置文件不能使用properties，必须使用xml。配置可参考： \n" +
                "Xml代码  收藏代码\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n" +
                "<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">  \n" +
                "<log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/'>  \n" +
                "     <!-- appender -->  \n" +
                "     <!-- STDOUT -->  \n" +
                "     <appender name=\"STDOUT\" class=\"org.apache.log4j.ConsoleAppender\">  \n" +
                "          <layout class=\"org.apache.log4j.PatternLayout\">  \n" +
                "               <param name=\"ConversionPattern\" value=\"%d %p [%c] - %m%n\" />  \n" +
                "          </layout>  \n" +
                "     </appender>  \n" +
                "  \n" +
                "     <!-- FILE -->  \n" +
                "     <appender name=\"FILE\"  \n" +
                "          class=\"org.apache.log4j.rolling.RollingFileAppender\">  \n" +
                "          <rollingPolicy  \n" +
                "               class=\"org.apache.log4j.rolling.TimeBasedRollingPolicy\">  \n" +
                "               <param name=\"FileNamePattern\"  \n" +
                "                    value=\"d:/logs/file.%d{yyyy-MM-dd-HH}\" />  \n" +
                "          </rollingPolicy>  \n" +
                "          <layout class=\"org.apache.log4j.PatternLayout\">  \n" +
                "               <param name=\"ConversionPattern\"  \n" +
                "                    value=\"%m%n\" />  \n" +
                "          </layout>  \n" +
                "     </appender>  \n" +
                "      \n" +
                "     <logger name=\"view\">  \n" +
                "          <level value=\"info\" />  \n" +
                "          <appender-ref ref=\"FILE\" />  \n" +
                "     </logger>  \n" +
                "  \n" +
                "     <root>  \n" +
                "          <level value=\"info\" />  \n" +
                "          <appender-ref ref=\"STDOUT\" />  \n" +
                "     </root>  \n" +
                "</log4j:configuration>  \n" +
                "\n" +
                "注意：org.apache.log4j.rolling.RollingFileAppender是rolling包下的，而不是原来的org.apache.log4j.RollingFileAppender \n" +
                "\n" +
                "测试用例： \n" +
                "Java代码  收藏代码\n" +
                "public static void main(String[] args) {  \n" +
                "           Logger logger = Logger.getLogger(\"view\");  \n" +
                "           logger.info(\"test\");  \n" +
                "}  \n" +
                "\n" +

                "\n" +
                "TimeBasedRollingPolicy的使用可参考： \n" +
                "http://logging.apache.org/log4j/companions/extras/apidocs/org/apache/log4j/rolling/TimeBasedRollingPolicy.html\n" +
                "分享到：    \n" +
                "cocos2d场景的层次图 | redis基本命令\n" +
                "2011-05-05 17:51浏览 16480评论(4)论坛回复 / 浏览 (4 / 2669)分类:编程语言查看更多\n" +
                "相关资源推荐\n" +
                "使用log4j扩展包的RollingFileAppender生成带时间戳的日志文件 使用log4j生成动态日志文件-文件名根据时间自动生成 Log4j2中RollingFile的文件滚动更新机制 一、什么是RollingFile RollingFileAppender是Log4j2中的一种能够实现日志文件滚动更新(rollover)的 Tomcat下使用Log4j 接管 catalina.out 日志文件生成方式 Tomcat下使用Log4j 接管 catalina.out 日志文件生成方式 tomcat下用Log4j 按文件大小，生成catalina.out日志文件 log4j 按时间、大小产生新的日志文件 Log4j输出包/类的日志文件 Log4j 4种日志文件生成方式 log4j 根据日期来生成日志文件 Tomcat配置log4j生成日志文件 Java中获取Log4j日志文件，Mybatis 的ScriptRunner执行带pl/sql的 代码块脚本 log4j指定包或类打到单独的日志文件的配置 Log4j 为单独的类生成单独的日志文件 Log4j每天、每小时、每分钟定时生成日志文件 log4j配置每天生成一个日志文件 log4j配置每天生成一个日志文件 使用log4j的DailyRollingFileAppender时只有一个日志文件 Log4j 日志文件使用 使用log4j记录日志文件\n" +
                "参考知识库\n" +
                "\n" +
                "Android知识库\n" +
                "39022  关注 | 3162  收录\n" +
                "\n" +
                "React知识库\n" +
                "3952  关注 | 393  收录\n" +
                "\n" +
                "人工智能基础知识库\n" +
                "18269  关注 | 212  收录\n" +
                "\n" +
                "Java 知识库\n" +
                "38183  关注 | 3748  收录\n" +
                "评论\n" +
                "4 楼 flyzsd 2011-05-09  \n" +
                "用 SLF4J + LOGBACK\n" +
                "3 楼 flysnowxf 2011-05-06  \n" +
                "logback实现以上需求的配置： \n" +
                "Xml代码  收藏代码\n" +
                "<timestamp key=\"byHour\" datePattern=\"yyyy-MM-dd-HH\"/>   \n" +
                "<appender name=\"FILE\"  \n" +
                "        class=\"ch.qos.logback.core.FileAppender\">  \n" +
                "        <File>d:/logs/file.${byHour}</File>  \n" +
                "        <layout class=\"ch.qos.logback.classic.PatternLayout\">  \n" +
                "            <Pattern>  \n" +
                "                %msg%n  \n" +
                "            </Pattern>  \n" +
                "        </layout>  \n" +
                "    </appender>  \n" +
                "  \n" +
                "    <logger name=\"view\">  \n" +
                "        <level value=\"info\" />  \n" +
                "        <appender-ref ref=\"FILE\" />  \n" +
                "    </logger>  \n" +
                "\n" +
                "不需要使用rolling包，直接产生带时间戳的日志文件。\n" +
                "2 楼 flysnowxf 2011-05-06  \n" +
                "simplechinese 写道\n" +
                "logback飘过\n" +
                "\n" +
                "看了下logback，它把这个功能集成进去了。那哥们老折腾了，弄了slf4j，又新开一个logback。\n" +
                "1 楼 simplechinese 2011-05-05  \n" +
                "logback飘过\n" +
                "发表评论\n" +
                "  您还没有登录,请您登录后再发表评论");
    }
}
