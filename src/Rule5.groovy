import static Operate.*

def 告警  = [
        设备名称 : this.data.deviceName,
        来源地址 : this.data.fromAddress,
        严重程度 : this.data.alarmLevel,
        告警类型 : this.data.alarmType,
        紧急程度 : this.data.alarmUrgent
]

def global = new main(data)








global.告警 {

    设备名称 (等于) ("hello")
    来源地址 (正则匹配) ("127\\.0\\.0\\.1:.*")
    那么 {
        打印 "hello meet a alarm"
    }
}

global.告警 {
    或者 {
        设备名称 (等于) ("hello")
        设备名称 (等于) ("world")
    }
    那么 {
        打印 告警.设备名称 + " meet a alarm"

    }
}

global.告警 {
    告警类型 (不等于) (2)
    那么 {
        打印 "ignore a alarm"
    }
}

global.告警 {
    如果 {
        紧急程度(等于)(1)
        严重程度(等于)(1)
        告警类型(不等于)(2)
        或者 {
            设备名称(等于)("hello")
            设备名称(等于)("world")
        }
    }
    那么 {
        打印 "hello world ignore a alarm"
    }
    否则 {
        打印 告警
    }

}









class main{
    AlarmData data
    main(AlarmData data){
        if(data == null) throw new IllegalStateException("data can not be null")
        this.data = data
    }

    def 告警(Closure closure){
        closure.getThisObject().data = data
        closure.getThisObject().currentOperate = "and"
        closure.getThisObject().currentLevelValue = true
        closure.getThisObject().globalList = []
        closure()
    }
}

enum Operate{
    不等于{
        @Override
        doJuge(Object a, Object b) {
            return a != b
        }
    }, 等于{
        @Override
        doJuge(Object a, Object b) {
            return a == b
        }
    }, 正则匹配{
        @Override
        doJuge(Object a, Object b) {
            if(a==null || b==null) return false
            if(!(a instanceof String) || !(b instanceof  String)){
                return false
            }

            String a1 = (String)a
            String b1 = (String)b

            return a1.matches(b1)
        }
    }

    abstract doJuge(Object a, Object b)
}

def 或者(Closure closure) {
    //store local
    boolean currentLevelValue = this.currentLevelValue
    String currentOperate = this.currentOperate

    this.currentOperate = "or"
    this.currentLevelValue = false

    closure()

    //set the value
    this.currentOperate = currentOperate

    setCurrentLevelValue(currentLevelValue)

}

def 并且(Closure closure) {
    //store local
    boolean currentLevelValue = this.currentLevelValue
    String currentOperate = this.currentOperate

    this.currentOperate = "and"
    this.currentLevelValue = true

    closure()

    //set the value
    this.currentOperate = currentOperate
    setCurrentLevelValue(currentLevelValue)

}

def 严重程度(Operate operate) {
    { value ->
        def bool = operate.doJuge(this.data.alarmLevel, value)
        setCurrentLevelValue(bool)

    }
}

def 来源地址(Operate operate) {
    { value ->
        def bool = operate.doJuge(this.data.fromAddress, value)
        setCurrentLevelValue(bool)
    }
}

def 设备名称(Operate operate) {
    { value ->
        def bool = operate.doJuge(this.data.deviceName, value)
        setCurrentLevelValue(bool)
    }
}

def 紧急程度(Operate operate) {
    { value ->
        def bool = operate.doJuge(this.data.alarmUrgent, value)
        setCurrentLevelValue(bool)
    }
}

def 告警类型(Operate operate) {
    { value ->
        def bool = operate.doJuge(this.data.alarmType, value)
        setCurrentLevelValue(bool)
    }
}

def 那么 (Closure action){
    if(this.currentLevelValue){
        action()
    }
}
def 否则 (Closure action){
    if(!this.currentLevelValue){
        action()
    }
}

def 如果 (Closure action){
    action()
}


def 打印 (Object something) {
    this.globalList << something
    //println  something
}

def setCurrentLevelValue(bool) {
    if (this.currentOperate == "and") {
        this.currentLevelValue = this.currentLevelValue && bool
    } else {
        this.currentLevelValue = this.currentLevelValue || bool
    }
}


