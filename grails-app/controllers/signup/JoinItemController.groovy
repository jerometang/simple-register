package signup

import grails.orm.PagedResultList
import org.springframework.dao.DataIntegrityViolationException

class JoinItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def BadmintonSignupService badmintonSignupService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        if (!params.sort){
            params.sort = "changeLog.createdDate"
            params.order = "asc"
        }
        String activityId = params.activityId
        if (!activityId || activityId.isEmpty()){
            activityId = JoinItem.get(params.joinId)?.activity?.id.toString();
            params.activityId = activityId
        }
        if (null == activityId || activityId.equals("null")|| activityId.isEmpty()){
            render(view: "/invalid");
            return;
        }
        PagedResultList users = badmintonSignupService.listUsers(params)
        def activityItem = ActivityItem.findById(Long.valueOf(activityId))
        [joinItemInstanceList: users, joinItemInstanceTotal: users.getTotalCount(), currentDate : ActivityItem.get(activityId)?.activityDate, activityId : activityId, group : activityItem.group]
    }

    def create() {
        def activityId = params.activityId
        if (!activityId || activityId.isEmpty()){
            activityId = JoinItem.get(params.joinId)?.activity?.id.toString();
        }
        [joinItemInstance: new JoinItem(params), currentDate : ActivityItem.get(activityId)?.activityDate, activityId : activityId]
    }

    def save() {
        def joinItemInstance = new JoinItem(params)
        joinItemInstance.changeLog = ChangeLog.createChangeLog(getRemoteHost())
        joinItemInstance.activity =  ActivityItem.get(params.activityId);
        if (!joinItemInstance.save(flush: true)) {
            render(view: "create", model: [joinItemInstance: joinItemInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), joinItemInstance.id])
        redirect(action: "show", id: joinItemInstance.id)
    }

    private String getRemoteHost() {
        (request.remoteHost != null) ? request.remoteHost : ""
    }

    def show() {
        def joinItemInstance = JoinItem.get(params.id)
        if (!joinItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), params.id])
            redirect(action: "list")
            return
        }

        [joinItemInstance: joinItemInstance]
    }

    def edit() {
        def joinItemInstance = JoinItem.get(params.id)
        if (!joinItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), params.id])
            redirect(action: "list")
            return
        }

        [joinItemInstance: joinItemInstance]
    }

    def update() {
        def joinItemInstance = JoinItem.get(params.id)
        if (!joinItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (joinItemInstance.version > version) {
                joinItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'joinItem.label', default: 'JoinItem')] as Object[],
                          "Another user has updated this JoinItem while you were editing")
                render(view: "edit", model: [joinItemInstance: joinItemInstance])
                return
            }
        }

        joinItemInstance.properties = params
        joinItemInstance.changeLog.updateChangeLog(getRemoteHost())

        if (!joinItemInstance.save(flush: true)) {
            render(view: "edit", model: [joinItemInstance: joinItemInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), joinItemInstance.id])
        redirect(action: "show", id: joinItemInstance.id)
    }

    def delete() {
        def joinItemInstance = JoinItem.get(params.id)
        if (!joinItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), params.id])
            redirect(controller: "joinItem", action: "list")
            return
        }

        try {
            def activityId = joinItemInstance.activity.id
            joinItemInstance.isDeleted = true
            joinItemInstance.changeLog.updateChangeLog(getRemoteHost())
            joinItemInstance.save(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), params.id])
            redirect(controller: "joinItem", action: "list", params: [activityId: activityId])
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'joinItem.label', default: 'JoinItem'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

}
