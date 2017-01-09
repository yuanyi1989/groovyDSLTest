/**
 * Created by 袁意 on 2017/1/6.
 */
class Main implements GroovyScript<AlarmData> {
    @Override
    void run(AlarmData data) {
        println data.fromAddress;
    }
}

