/**
 * Created by 袁意 on 2017/1/6.
 */
class Rule3 implements GroovyScript<AlarmData> {



    @Override
    void run(AlarmData data) {

        if (data.deviceName == "hello" || data.deviceName == "world"){
            println data.deviceName+" meet a alarm1"
        }
    }
}

