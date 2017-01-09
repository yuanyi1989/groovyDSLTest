/**
 * Created by 袁意 on 2017/1/6.
 */
class Rule2 implements GroovyScript<AlarmData> {
    @Override
    void run(AlarmData data) {
        if (data.deviceName == "hello" && data.fromAddress.split(":")[0] == "172.0.0.1"){
            println "hello meet a alarm1"
        }
    }
}

