package signup

class ActivityGroup {
    String name = 'zhu';

    static hasMany = [activities : ActivityItem]

    static constraints = {
        activities(nullable: true)
    }
}
