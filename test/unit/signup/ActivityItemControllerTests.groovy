package signup



import org.junit.*
import grails.test.mixin.*

@TestFor(ActivityItemController)
@Mock(ActivityItem)
class ActivityItemControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/activityItem/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.activityItemInstanceList.size() == 0
        assert model.activityItemInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.activityItemInstance != null
    }

    void testSave() {
        controller.save()

        assert model.activityItemInstance != null
        assert view == '/activityItem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/activityItem/show/1'
        assert controller.flash.message != null
        assert ActivityItem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/activityItem/list'


        populateValidParams(params)
        def activityItem = new ActivityItem(params)

        assert activityItem.save() != null

        params.id = activityItem.id

        def model = controller.show()

        assert model.activityItemInstance == activityItem
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/activityItem/list'


        populateValidParams(params)
        def activityItem = new ActivityItem(params)

        assert activityItem.save() != null

        params.id = activityItem.id

        def model = controller.edit()

        assert model.activityItemInstance == activityItem
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/activityItem/list'

        response.reset()


        populateValidParams(params)
        def activityItem = new ActivityItem(params)

        assert activityItem.save() != null

        // test invalid parameters in update
        params.id = activityItem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/activityItem/edit"
        assert model.activityItemInstance != null

        activityItem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/activityItem/show/$activityItem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        activityItem.clearErrors()

        populateValidParams(params)
        params.id = activityItem.id
        params.version = -1
        controller.update()

        assert view == "/activityItem/edit"
        assert model.activityItemInstance != null
        assert model.activityItemInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/activityItem/list'

        response.reset()

        populateValidParams(params)
        def activityItem = new ActivityItem(params)

        assert activityItem.save() != null
        assert ActivityItem.count() == 1

        params.id = activityItem.id

        controller.delete()

        assert ActivityItem.count() == 0
        assert ActivityItem.get(activityItem.id) == null
        assert response.redirectedUrl == '/activityItem/list'
    }
}
