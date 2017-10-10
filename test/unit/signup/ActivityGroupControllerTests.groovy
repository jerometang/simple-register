package signup



import org.junit.*
import grails.test.mixin.*

@TestFor(ActivityGroupController)
@Mock(ActivityGroup)
class ActivityGroupControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/activityGroup/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.activityGroupInstanceList.size() == 0
        assert model.activityGroupInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.activityGroupInstance != null
    }

    void testSave() {
        controller.save()

        assert model.activityGroupInstance != null
        assert view == '/activityGroup/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/activityGroup/show/1'
        assert controller.flash.message != null
        assert ActivityGroup.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/activityGroup/list'


        populateValidParams(params)
        def activityGroup = new ActivityGroup(params)

        assert activityGroup.save() != null

        params.id = activityGroup.id

        def model = controller.show()

        assert model.activityGroupInstance == activityGroup
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/activityGroup/list'


        populateValidParams(params)
        def activityGroup = new ActivityGroup(params)

        assert activityGroup.save() != null

        params.id = activityGroup.id

        def model = controller.edit()

        assert model.activityGroupInstance == activityGroup
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/activityGroup/list'

        response.reset()


        populateValidParams(params)
        def activityGroup = new ActivityGroup(params)

        assert activityGroup.save() != null

        // test invalid parameters in update
        params.id = activityGroup.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/activityGroup/edit"
        assert model.activityGroupInstance != null

        activityGroup.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/activityGroup/show/$activityGroup.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        activityGroup.clearErrors()

        populateValidParams(params)
        params.id = activityGroup.id
        params.version = -1
        controller.update()

        assert view == "/activityGroup/edit"
        assert model.activityGroupInstance != null
        assert model.activityGroupInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/activityGroup/list'

        response.reset()

        populateValidParams(params)
        def activityGroup = new ActivityGroup(params)

        assert activityGroup.save() != null
        assert ActivityGroup.count() == 1

        params.id = activityGroup.id

        controller.delete()

        assert ActivityGroup.count() == 0
        assert ActivityGroup.get(activityGroup.id) == null
        assert response.redirectedUrl == '/activityGroup/list'
    }
}
