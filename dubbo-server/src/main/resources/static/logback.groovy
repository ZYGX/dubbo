import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import static ch.qos.logback.core.spi.FilterReply.ACCEPT
import static ch.qos.logback.core.spi.FilterReply.DENY
def LOG_HOME = "../logs"
appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy/MM/dd-HH:mm:ss} %-5level [%thread] %class{5}:%line>>%msg%n"
    }
}
appender("INFO_FILE", RollingFileAppender) {
//    encoding = "UTF-8"
    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_HOME}/dubbo-info.%d{yyyy-MM-dd}.%i.log"
        maxFileSize = "20MB"
        totalSizeCap = "10GB"
        maxHistory = 30
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy/MM/dd-HH:mm:ss} %-5level [%thread] %class{5}:%line - %msg%n"
    }
    filter(LevelFilter) {
        level = INFO
        onMatch = ACCEPT
        onMismatch = DENY
    }
}
appender("ERROR_FILE", RollingFileAppender) {
//    encoding = "UTF-8"
    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_HOME}/dubbo-error.%d{yyyy-MM-dd}.%i.log"
        maxFileSize = "20MB"
        totalSizeCap = "10GB"
        maxHistory = 30
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy/MM/dd-HH:mm:ss} %-5level [%thread] %class{5}:%line - %msg%n"
    }
    filter(LevelFilter) {
        level = ERROR
        onMatch = ACCEPT
        onMismatch = DENY
    }
}
root(INFO, ["CONSOLE"])