package signup

import grails.orm.PagedResultList

class BadmintonSignupService {

    def listActivity(int size){
        def criteria = ActivityItem.createCriteria()
        def activities = criteria.list{

        }
    }

    def listUsers(params) {
        String activityId = params.activityId;
        Boolean showDelete = (params.admin != null);
        Long parentId = Long.valueOf(activityId)
        def criteria = JoinItem.createCriteria()
        def joins = criteria.list(max: params.max, offset: params.offset){
            activity{
                eq "id", parentId
            }
            if (!showDelete){
                eq "isDeleted", false
            }
            maxResults(params.max)
            order 'changeLog.createdDate', 'asc'
        }
        return joins
    }

    def findByGroupId(params) {
        String groupId = params.g;

        Long parentId = Long.valueOf(groupId)
        def criteria = ActivityItem.createCriteria()
        def activities = criteria.list(max: params.max, offset: params.offset){
            group{
                eq "id", parentId
            }
            maxResults(params.max)
            order('activityDate','desc')
        }
        return activities
    }
}
