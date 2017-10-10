package signup

import grails.orm.PagedResultList
import org.springframework.dao.DataIntegrityViolationException
import signup.BadmintonSignupService

class ActivityItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def BadmintonSignupService badmintonSignupService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        if (!params.sort){
            params.sort = "activityDate"
            params.order = "desc"
        }
        String groupId = params.g;
        if (!groupId || groupId.isEmpty()){
            render(view: "/invalid")
            return
        }
        PagedResultList acts = badmintonSignupService.findByGroupId(params);
        ActivityGroup group = ActivityGroup.findById(Long.valueOf(groupId));
        [activityItemInstanceList: acts, activityItemInstanceTotal: acts.getTotalCount(), group : group]
    }

    def create() {
        String groupId = params.g;
        [activityItemInstance: new ActivityItem(params), groupId: groupId]
    }

    def save() {
        String groupId = params.g;
        if (groupId?.isEmpty()){
            render(view: "/invalid")
            return
        }
        def activityItemInstance = new ActivityItem(params)
        activityItemInstance.changeLog = ChangeLog.createChangeLog((request.remoteHost != null) ? request.remoteHost : "")

        activityItemInstance.group = ActivityGroup.findById(Long.valueOf(groupId))
        if (!activityItemInstance.save(flush: true)) {
            render(view: "create", model: [activityItemInstance: activityItemInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), activityItemInstance.id])
        redirect(action: "show", id: activityItemInstance.id)
    }

    def show() {
        def activityItemInstance = ActivityItem.get(params.id)
        if (!activityItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), params.id])
            redirect(action: "list")
            return
        }

        [activityItemInstance: activityItemInstance, groupId: activityItemInstance.group.id]
    }

    def edit() {
        def activityItemInstance = ActivityItem.get(params.id)
        if (!activityItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), params.id])
            redirect(action: "list")
            return
        }

        [activityItemInstance: activityItemInstance]
    }

    def update() {
        def activityItemInstance = ActivityItem.get(params.id)
        if (!activityItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (activityItemInstance.version > version) {
                activityItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'activityItem.label', default: 'ActivityItem')] as Object[],
                          "Another user has updated this ActivityItem while you were editing")
                render(view: "edit", model: [activityItemInstance: activityItemInstance])
                return
            }
        }

        activityItemInstance.properties = params

        if (!activityItemInstance.save(flush: true)) {
            render(view: "edit", model: [activityItemInstance: activityItemInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), activityItemInstance.id])
        redirect(action: "show", id: activityItemInstance.id)
    }

    def delete() {
        def activityItemInstance = ActivityItem.get(params.id)
        if (!activityItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), params.id])
            redirect(action: "list")
            return
        }

        try {
            def groupId = activityItemInstance.group.id
            activityItemInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), params.id])
            redirect(action: "list", params: [g : groupId])
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'activityItem.label', default: 'ActivityItem'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
