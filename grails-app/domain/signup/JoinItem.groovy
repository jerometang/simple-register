package signup

class JoinItem {
    String userName
    ChangeLog changeLog
    boolean isDeleted = false

    static embedded = ['changeLog']

    static belongsTo = [activity : ActivityItem]

    static constraints = {
    }
}
