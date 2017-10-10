package signup

import org.springframework.dao.DataIntegrityViolationException

class ActivityGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        /*redirect(action: "list", params: params)*/
        [activityGroupInstanceList: ActivityGroup.list(params), activityGroupInstanceTotal: ActivityGroup.count()]
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [activityGroupInstanceList: ActivityGroup.list(params), activityGroupInstanceTotal: ActivityGroup.count()]
    }

    def create() {
        [activityGroupInstance: new ActivityGroup(params)]
    }

    def save() {
        def activityGroupInstance = new ActivityGroup(params)
        if (!activityGroupInstance.save(flush: true)) {
            render(view: "create", model: [activityGroupInstance: activityGroupInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), activityGroupInstance.id])
        redirect(action: "show", id: activityGroupInstance.id)
    }

    def show() {
        def activityGroupInstance = ActivityGroup.get(params.id)
        if (!activityGroupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), params.id])
            redirect(action: "list")
            return
        }

        [activityGroupInstance: activityGroupInstance]
    }

    def edit() {
        def activityGroupInstance = ActivityGroup.get(params.id)
        if (!activityGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), params.id])
            redirect(action: "list")
            return
        }

        [activityGroupInstance: activityGroupInstance]
    }

    def update() {
        def activityGroupInstance = ActivityGroup.get(params.id)
        if (!activityGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (activityGroupInstance.version > version) {
                activityGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'activityGroup.label', default: 'ActivityGroup')] as Object[],
                          "Another user has updated this ActivityGroup while you were editing")
                render(view: "edit", model: [activityGroupInstance: activityGroupInstance])
                return
            }
        }

        activityGroupInstance.properties = params

        if (!activityGroupInstance.save(flush: true)) {
            render(view: "edit", model: [activityGroupInstance: activityGroupInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), activityGroupInstance.id])
        redirect(action: "show", id: activityGroupInstance.id)
    }

    def delete() {
        def activityGroupInstance = ActivityGroup.get(params.id)
        if (!activityGroupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), params.id])
            redirect(action: "list")
            return
        }

        try {
            activityGroupInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'activityGroup.label', default: 'ActivityGroup'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
