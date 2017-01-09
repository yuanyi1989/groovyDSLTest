import java.util.Date;

/**
 * Created by 袁意 on 2017/1/7.
 */
public class AlarmData {
    private long alarmeID;          // 告警ID
    private String deviceName;      // 告警来源设备名
    private String fromAddress;     // 告警来源地址:Port    
    private short alarmType;          // 告警类型 1:事件告警，2:故障告警；3:告警恢复
    private short alarmUrgent;      // 告警紧急程度：1:一般 2:紧急 3:非常紧急
    private short alarmLevel;       // 告警严重程度 1:一般 2:重要 3:非常重要    
    private int alarmCode;          // 告警业务标识，表示是什么问题的告警，业务分配
    private String alarmDesc;       // 告警描述
    private String alarmSuggestion; // 告警修复建议
    private Date alarmTime;         // 告警时间 


    public long getAlarmeID() {
        return alarmeID;
    }

    public void setAlarmeID(long alarmeID) {
        this.alarmeID = alarmeID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public short getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(short alarmType) {
        this.alarmType = alarmType;
    }

    public short getAlarmUrgent() {
        return alarmUrgent;
    }

    public void setAlarmUrgent(short alarmUrgent) {
        this.alarmUrgent = alarmUrgent;
    }

    public short getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(short alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public int getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(int alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmDesc() {
        return alarmDesc;
    }

    public void setAlarmDesc(String alarmDesc) {
        this.alarmDesc = alarmDesc;
    }

    public String getAlarmSuggestion() {
        return alarmSuggestion;
    }

    public void setAlarmSuggestion(String alarmSuggestion) {
        this.alarmSuggestion = alarmSuggestion;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }
}

