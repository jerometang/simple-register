package signup

import java.text.DateFormat
import java.text.SimpleDateFormat

class ActivityItem {
    Date activityDate
    ActivityType type = ActivityType.Badminton;
    String memo
    ChangeLog changeLog

    static belongsTo = [group : ActivityGroup]

    static embedded = ['changeLog']

    static hasMany = [joins : JoinItem]

    static constraints = {
        joins(nullable: true)
    }

    static mapping = {
        type inex:'Type_Idx'
        activityDate index:'Type_Idex,Act_Date_Idx'
    }

    def activeJoinItems(){
        joins.findAll {!it.isDeleted}
    }
}


class ChangeLog{
    Date createdDate
    Date lastUpdatedDate
    String createdBy
    String lastUpdatedBy

    static mapping = {
        createdDate(column: 'createdDate')
        lastUpdatedDate(column: 'lastUpdatedDate')
        createdBy(column: 'createdBy')
        lastUpdatedBy(column: 'lastUpdatedBy')
        createdBy( nullable : true)
        lastUpdatedBy(nullable : true)
        version(false)
    }

    static ChangeLog createChangeLog(String createBy){
        ChangeLog changeLog = new ChangeLog();
        def currentDate = new Date()
        changeLog.createdDate = currentDate
        changeLog.lastUpdatedDate = currentDate
        changeLog.createdBy = createBy
        changeLog.lastUpdatedBy = createBy
        return changeLog
    }

    def updateChangeLog(String lastUpdateBy){
        this.lastUpdatedDate = new Date()
        this.lastUpdatedBy = lastUpdateBy
    }
}

enum ActivityType{
    Badminton("bm","Badminton")

    String code;
    String description;

    ActivityType(String code, String des){
        this.code = code;
        this.description = des;
    }

    String value(){
        return this.code;
    }

    static ActivityType findByCode(String code){
        for(ActivityType type : ActivityType.values()){
            if(type.code == code){
                return type;
            }
        }
        return null;
    }
}
